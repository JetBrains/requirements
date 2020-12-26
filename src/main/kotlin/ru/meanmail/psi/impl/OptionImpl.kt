package ru.meanmail.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import ru.meanmail.psi.*

class OptionImpl(node: ASTNode) : ASTWrapperPsiElement(node), Option {
    fun accept(visitor: Visitor) {
        visitor.visitOption(this)
    }

    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is Visitor) accept(visitor) else super.accept(visitor)
    }

    override val constraintReq: ConstraintReq?
        get() = findChildByClass(ConstraintReq::class.java)

    override val editableReq: EditableReq?
        get() = findChildByClass(EditableReq::class.java)

    override val extraIndexUrlReq: ExtraIndexUrlReq?
        get() = findChildByClass(ExtraIndexUrlReq::class.java)

    override val findLinksReq: FindLinksReq?
        get() = findChildByClass(FindLinksReq::class.java)

    override val indexUrlReq: IndexUrlReq?
        get() = findChildByClass(IndexUrlReq::class.java)

    override val noBinaryReq: NoBinaryReq?
        get() = findChildByClass(NoBinaryReq::class.java)

    override val noIndexReq: NoIndexReq?
        get() = findChildByClass(NoIndexReq::class.java)

    override val onlyBinaryReq: OnlyBinaryReq?
        get() = findChildByClass(OnlyBinaryReq::class.java)

    override val preReq: PreReq?
        get() = findChildByClass(PreReq::class.java)

    override val preferBinaryReq: PreferBinaryReq?
        get() = findChildByClass(PreferBinaryReq::class.java)

    override val referReq: ReferReq?
        get() = findChildByClass(ReferReq::class.java)

    override val requireHashesReq: RequireHashesReq?
        get() = findChildByClass(RequireHashesReq::class.java)

    override val trustedHostReq: TrustedHostReq?
        get() = findChildByClass(TrustedHostReq::class.java)

    override val useFeatureReq: UseFeatureReq?
        get() = findChildByClass(UseFeatureReq::class.java)
}
