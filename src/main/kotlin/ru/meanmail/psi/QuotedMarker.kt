package ru.meanmail.psi

import com.intellij.psi.PsiElement
import ru.meanmail.Logical

interface QuotedMarker : PsiElement {

    val marker: Marker

    fun logical(): Logical {
        return marker.logical()
    }

}
