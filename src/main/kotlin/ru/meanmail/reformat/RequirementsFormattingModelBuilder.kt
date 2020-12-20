package ru.meanmail.reformat

import com.intellij.formatting.*
import com.intellij.psi.PsiElement
import com.intellij.psi.codeStyle.CodeStyleSettings
import ru.meanmail.lang.RequirementsLanguage
import ru.meanmail.psi.Types

class RequirementsFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
        return FormattingModelProvider
            .createFormattingModelForPsiFile(
                element.containingFile,
                Block(
                    element.node,
                    Wrap.createWrap(WrapType.ALWAYS, false),
                    Alignment.createAlignment(),
                    createSpaceBuilder(settings)
                ),
                settings
            )
    }

    private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
        return SpacingBuilder(settings, RequirementsLanguage)
            .around(Types.VERSIONSPEC)
            .none()
            .around(Types.NAME_REQ)
            .none()
            .around(Types.URL_REQ)
            .none()
            .around(Types.REFER_REQ)
            .none()
            .around(Types.EDITABLE_REQ)
            .none()
            .around(Types.PATH_REQ)
            .none()
            .after(Types.NAME)
            .none()
            .around(Types.EXTRAS_LIST)
            .none()
            .aroundInside(Types.COMMA, Types.EXTRAS_LIST)
            .none()
    }
}
