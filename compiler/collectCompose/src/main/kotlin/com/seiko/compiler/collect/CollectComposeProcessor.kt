package com.seiko.compiler.collect

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSVisitorVoid

class CollectComposeProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return CollectComposeProcessor(environment)
  }
}

class CollectComposeProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

  private companion object {
    const val COLLECT_COMPOSE = "com.seiko.tv.anime.ui.composer.collector.CollectCompose"
  }

  private val coderGenerator = environment.codeGenerator
  private val logger = environment.logger

  override fun process(resolver: Resolver): List<KSAnnotated> {
    logger.info("collecting compose...")
    resolver.getSymbolsWithAnnotation(COLLECT_COMPOSE)
      .asSequence()
      .filterIsInstance<KSFunctionDeclaration>()
      .forEach { it.accept(BuilderVisitor(), Unit) }
    return emptyList()
  }

  inner class BuilderVisitor : KSVisitorVoid() {
    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
      logger.info("find func ${function.simpleName.asString()}")

      val packageName = function.packageName.asString()
      val fileName = function.simpleName.asString() + "Module"

      val qualifier = function.annotations
        .find { it.shortName.asString() == "CollectCompose" }!!.arguments
        .find { it.name!!.asString() == "qualifier" }!!.value as KSType

      coderGenerator.createNewFile(
        dependencies = Dependencies(aggregating = true, function.containingFile!!),
        packageName = packageName,
        fileName = fileName
      ).use { output ->

        val str = """
        |package $packageName
        |
        |import androidx.compose.foundation.layout.BoxScope
        |import androidx.compose.runtime.Composable
        |import dagger.Module
        |import dagger.Provides
        |import dagger.hilt.InstallIn
        |import dagger.hilt.android.components.ActivityComponent
        |import dagger.multibindings.IntoSet
        |import ${qualifier.declaration.qualifiedName!!.asString()}
        |
        |@InstallIn(ActivityComponent::class)
        |@Module
        |object ${function.simpleName.asString()}Module {
        |  @Provides
        |  @IntoSet
        |  @${qualifier.declaration.simpleName.asString()}
        |  fun provide${function.simpleName.asString()}() = object : CollectComposeOwner<BoxScope> {
        |    @Composable
        |    override fun Show(scope: BoxScope) {
        |      scope.${function.simpleName.asString()}()
        |    }
        |  }
        |}
        |
        """.trimMargin()

        output.write(str.toByteArray())
      }
    }
  }
}
