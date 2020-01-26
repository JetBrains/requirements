package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.Logical
import ru.meanmail.Or

interface MarkerOr : PsiElement {

    val markerAndList: List<MarkerAnd>

    fun logical(): Logical {
        val ands = mutableListOf<Logical>()
        for (marker in markerAndList) {
            ands.add(marker.logical())
        }
        return Or(*ands.toTypedArray())
    }
}
