package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.Logical

interface Marker : PsiElement {

    val markerOr: MarkerOr

    fun logical(): Logical {
        return markerOr.logical()
    }

}
