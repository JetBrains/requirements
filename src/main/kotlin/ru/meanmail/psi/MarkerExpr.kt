package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.Expression
import ru.meanmail.False
import ru.meanmail.Logical
import ru.meanmail.True

interface MarkerExpr : PsiElement {

    val markerOp: MarkerOp?

    val markerOr: MarkerOr?

    val markerVarList: List<MarkerVar>

    fun logical(): Logical {
        val marker = markerOr

        if (marker != null) {
            return marker.logical()
        }

        val operation = markerOp?.text

        if (markerVarList.isEmpty()) {
            return False()
        }

        if (markerVarList.size == 1) {
            return True()
        }

        if (operation == null) {
            return False()
        }

        val variable = markerVarList[0].text
        var value = markerVarList[1].text
        value = value.substring(1, value.length - 1)

        return Expression(variable, operation, value)
    }
}
