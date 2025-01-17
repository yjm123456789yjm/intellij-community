// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.intellij.codeInsight.hints.settings

import com.intellij.lang.Language
import com.intellij.openapi.extensions.BaseExtensionPointName
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.project.Project


/**
 * Provides settings models to fill Preferences | Editor | Inlay Hints.
 * You don't need to use it if you are using InlayHintsProvider or InlayParameterHintsProvider
 */
interface InlaySettingsProvider {
  /**
   * Returns list of hint provider models to be shown in Preferences | Editor | Inlay Hints
   * Languages are expected to be only from [getSupportedLanguages]
   */
  fun createModels(project: Project, language: Language): List<InlayProviderSettingsModel>

  /**
   *  Returns list of supported languages. Every language must have a model in [createModels].
   */
  fun getSupportedLanguages(project: Project): Collection<Language>

  @JvmDefault
  fun getDependencies(): Collection<BaseExtensionPointName<*>> {
    return emptyList()
  }

  object EP {
    val EXTENSION_POINT_NAME: ExtensionPointName<InlaySettingsProvider> =
      ExtensionPointName.create("com.intellij.config.inlaySettingsProvider")

    fun getExtensions(): Array<out InlaySettingsProvider> {
      return EXTENSION_POINT_NAME.extensions
    }
  }
}