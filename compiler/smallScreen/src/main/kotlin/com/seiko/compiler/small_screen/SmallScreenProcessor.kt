package com.seiko.compiler.small_screen

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid

class SmallScreenProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return SmallScreenProcessor(environment)
  }
}

class SmallScreenProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

  private companion object {
    const val SMALL_SCREEN_NAME = "com.seiko.tv.anime.ui.composer.screener.SmallScreen"
  }

  private val coderGenerator = environment.codeGenerator
  private val logger = environment.logger

  override fun process(resolver: Resolver): List<KSAnnotated> {
    logger.info("finding smallScreen...")
    resolver.getSymbolsWithAnnotation(SMALL_SCREEN_NAME)
      .asSequence()
      .filterIsInstance<KSFunctionDeclaration>()
      .forEach { it.accept(BuilderVisitor(), Unit) }
    return emptyList()
  }

  inner class BuilderVisitor : KSVisitorVoid() {
    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
      logger.info("find func ${function.simpleName.asString()}")

      val packageName = function.packageName.asString()
      val className = function.simpleName.asString() + "Module"

      coderGenerator.createNewFile(
        dependencies = Dependencies(aggregating = true, function.containingFile!!),
        packageName = packageName,
        fileName = className
      ).use { output ->

        val str = """
        |package $packageName
        |
        |import androidx.compose.foundation.layout.BoxScope
        |import androidx.compose.runtime.Composable
        |import com.seiko.tv.anime.ui.composer.screener.SmallScreenWrap
        |import dagger.Module
        |import dagger.Provides
        |import dagger.hilt.InstallIn
        |import dagger.hilt.android.components.ActivityComponent
        |import dagger.multibindings.IntoSet
        |
        |@InstallIn(ActivityComponent::class)
        |@Module
        |object ${function.simpleName.asString()}Module {
        |  @Provides
        |  @IntoSet
        |  fun provide${function.simpleName.asString()}(): SmallScreenWrap = object : SmallScreenWrap {
        |    @Composable
        |    override fun BoxScope.Show() = ${function.simpleName.asString()}()
        |  }
        |}
        |
        """.trimMargin()

        output.write(str.toByteArray())
      }
    }
  }
}
