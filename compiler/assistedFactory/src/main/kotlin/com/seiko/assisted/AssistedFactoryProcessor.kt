package com.seiko.assisted

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class AssistedFactoryProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return AssistedFactoryProcessor(environment)
  }
}

class AssistedFactoryProcessor(environment: SymbolProcessorEnvironment) : SymbolProcessor {

  private val coderGenerator = environment.codeGenerator

  override fun process(resolver: Resolver): List<KSAnnotated> {
    resolver.getSymbolsWithAnnotation("dagger.assisted.AssistedFactory")
      .filterIsInstance<KSClassDeclaration>()
      .forEach { it.accept(BuilderVisitor(), Unit) }
    return emptyList()
  }

  inner class BuilderVisitor : KSVisitorVoid() {

    @OptIn(KotlinPoetKspPreview::class)
    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
      super.visitClassDeclaration(classDeclaration, data)

      // com.seiko.anime.DetailViewModel -> DetailViewModel
      val viewModelName = classDeclaration.qualifiedName!!.asString()
        .run { substring(0, lastIndexOf('.')) }
        .run { substring(lastIndexOf('.') + 1) }

      val packageName = classDeclaration.packageName.asString()
      val fileName = "${viewModelName}FactoryModule"

      val assistedFactory = classDeclaration.toClassName()

      FileSpec.builder(packageName, fileName)
        .addType(
          TypeSpec.interfaceBuilder(fileName)
            .addAnnotation(
              AnnotationSpec.builder(hiltInstallIn)
                .addMember("%T::class", hiltSingletonComponent)
                .build()
            )
            .addAnnotation(hiltModule)
            .addFunction(
              FunSpec.builder("bind${viewModelName}Factory")
                .addParameter("factory", assistedFactory)
                .returns(Any::class)
                .addModifiers(KModifier.ABSTRACT)
                .addAnnotation(hiltBinds)
                .addAnnotation(hiltIntoMap)
                .addAnnotation(assistedFactoryQualifier)
                .addAnnotation(
                  AnnotationSpec.builder(assistedFactoryKey)
                    .addMember("%T::class", assistedFactory)
                    .build()
                )
                .build()
            )
            .build()
        )
        .build()
        .writeTo(coderGenerator, Dependencies(true, classDeclaration.containingFile!!))
    }
  }
}

private val hiltInstallIn = ClassName("dagger.hilt", "InstallIn")
private val hiltSingletonComponent = ClassName("dagger.hilt.components", "SingletonComponent")
private val hiltBinds = ClassName("dagger", "Binds")
private val hiltModule = ClassName("dagger", "Module")
private val hiltIntoMap = ClassName("dagger.multibindings", "IntoMap")

private val assistedFactoryQualifier = ClassName("com.seiko.tv.anime.di.scope", "AssistedFactoryQualifier")
private val assistedFactoryKey = ClassName("com.seiko.tv.anime.di.scope", "AssistedFactoryKey")
