package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.And
import ru.meanmail.Logical

interface MarkerAnd : PsiElement {

    val markerExprList: List<MarkerExpr>

    fun logical(): Logical {
        val expr = mutableListOf<Logical>()

        for (expression in markerExprList) {
            expr.add(expression.logical())
        }
        return And(*expr.toTypedArray())
    }
}
