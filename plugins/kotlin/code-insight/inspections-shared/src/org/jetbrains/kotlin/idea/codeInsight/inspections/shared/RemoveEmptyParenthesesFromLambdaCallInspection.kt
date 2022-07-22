// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.kotlin.idea.codeInsight.inspections.shared

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import org.jetbrains.kotlin.idea.base.resources.KotlinBundle
import org.jetbrains.kotlin.idea.codeinsight.api.classic.inspections.AbstractApplicabilityBasedInspection
import org.jetbrains.kotlin.idea.codeinsights.impl.base.applicators.RemoveEmptyParenthesesFromLambdaCallIntentionUtils
import org.jetbrains.kotlin.psi.KtValueArgumentList

class RemoveEmptyParenthesesFromLambdaCallInspection :
    AbstractApplicabilityBasedInspection<KtValueArgumentList>(KtValueArgumentList::class.java) {
    override fun inspectionText(element: KtValueArgumentList): String =
        KotlinBundle.message("remove.unnecessary.parentheses.from.function.call.with.lambda")

    override val defaultFixText: String
        get() = KotlinBundle.message("remove.unnecessary.parentheses.from.function.call.with.lambda")

    override fun applyTo(element: KtValueArgumentList, project: Project, editor: Editor?) =
        RemoveEmptyParenthesesFromLambdaCallIntentionUtils.applyTo(element)

    override fun isApplicable(element: KtValueArgumentList): Boolean =
        RemoveEmptyParenthesesFromLambdaCallIntentionUtils.isApplicable(element)
}