/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.codeInsight.daemon.impl.quickfix;

import com.intellij.codeInsight.intention.FileModifier;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.java.analysis.JavaAnalysisBundle;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class QualifyThisOrSuperArgumentFix implements IntentionAction {
  protected static final Logger LOG = Logger.getInstance(QualifyThisOrSuperArgumentFix.class);
  protected final PsiExpression myExpression;
  protected final PsiClass myPsiClass;
  private @IntentionName String myText;


  public QualifyThisOrSuperArgumentFix(@NotNull PsiExpression expression, @NotNull PsiClass psiClass) {
    myExpression = expression;
    myPsiClass = psiClass;
  }

  /**
   * Must override in subclasses, as we store myExpression in the field, so default strategy won't work
   */
  @Override
  abstract public @Nullable FileModifier getFileModifierForPreview(@NotNull PsiFile target);

  @Override
  public boolean startInWriteAction() {
    return true;
  }

  @NotNull
  @Override
  public String getText() {
    return myText;
  }

  protected abstract String getQualifierText();
  protected abstract PsiExpression getQualifier(PsiManager manager);
  
  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
    if (!myExpression.isValid()) return false;
    if (!myPsiClass.isValid()) return false;
    myText = JavaAnalysisBundle.message("intention.name.qualify.expression", getQualifierText(), myPsiClass.getQualifiedName());
    return true;
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return JavaAnalysisBundle.message("qualify.0", getQualifierText());
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
    myExpression.replace(getQualifier(PsiManager.getInstance(project)));
  }
}
