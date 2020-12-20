package ru.meanmail.reformat

import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings
import ru.meanmail.lang.RequirementsLanguage
import ru.meanmail.psi.Types

class RequirementsFormattingModelBuilder : FormattingModelBuilder {
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        return FormattingModelProvider
            .createFormattingModelForPsiFile(
                formattingContext.containingFile,
                Block(
                    formattingContext.node,
                    Wrap.createWrap(WrapType.ALWAYS, false),
                    Alignment.createAlignment(),
                    createSpaceBuilder(formattingContext.codeStyleSettings)
                ),
                formattingContext.codeStyleSettings
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
