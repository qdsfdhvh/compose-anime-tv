package com.seiko.assisted

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.visitor.KSEmptyVisitor

class AssistedViewModelProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return AssistedViewModelProcessor(environment)
  }
}

class AssistedViewModelProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

  private val coderGenerator = environment.codeGenerator
  // private val logger = environment.logger

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val factorySymbols = resolver.getSymbolsWithAnnotation("dagger.assisted.AssistedFactory")
    val factory = factorySymbols.filterIsInstance<KSClassDeclaration>().toList()

    val activitySymbols = resolver.getSymbolsWithAnnotation("dagger.hilt.android.AndroidEntryPoint")
    activitySymbols.forEach { it.accept(HolderVisitor(), factory) }

    return emptyList()
  }

  inner class HolderVisitor : KSEmptyVisitor<List<KSClassDeclaration>, Unit>() {
    override fun defaultHandler(node: KSNode, data: List<KSClassDeclaration>) {
      if (node !is KSClassDeclaration) {
        return
      }

      val packageName = node.packageName.asString()
      val className = "${node.qualifiedName?.getShortName()}AssistedViewHolder"

      coderGenerator.createNewFile(
        Dependencies(aggregating = false),
        packageName = packageName,
        fileName = className
      ).use { output ->
        var num = 1
        val params = data.joinToString(separator = System.lineSeparator() + "|  ") {
          val name = it.qualifiedName?.asString() ?: "<ERROR>"
          "factory${num++}: $name,"
        }

        num = 1
        val mapParams = data.joinToString(separator = System.lineSeparator() + "|    ") {
          val name = it.qualifiedName?.asString() ?: "<ERROR>"
          "$name::class.java to factory${num++},"
        }

        val str = """
        |package $packageName
        |  
        |class $className @javax.inject.Inject constructor(
        |  $params
        |) {
        |  val factory = mapOf(
        |    $mapParams
        |  )
        |}
        """.trimMargin()

        output.write(str.toByteArray())
      }
    }
  }
}
