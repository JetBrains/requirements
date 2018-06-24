package ru.meanmail.structure

import com.intellij.ide.projectView.PresentationData
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.PsiTreeUtil
import ru.meanmail.psi.*

class RequirementsStructureViewElement(private val element: NavigatablePsiElement) : StructureViewTreeElement, SortableTreeElement {
    
    override fun getValue(): Any {
        return element
    }
    
    override fun navigate(requestFocus: Boolean) {
        element.navigate(requestFocus)
    }
    
    override fun canNavigate(): Boolean {
        return element.canNavigate()
    }
    
    override fun canNavigateToSource(): Boolean {
        return element.canNavigateToSource()
    }
    
    override fun getAlphaSortKey(): String {
        return element.name ?: ""
    }
    
    override fun getPresentation(): ItemPresentation {
        return element.presentation ?: object : PresentationData() {
            override fun getPresentableText(): String? {
                return element.name
            }
        }
    }
    
    override fun getChildren(): Array<TreeElement> {
        if (element is RequirementsFile) {
            return PsiTreeUtil.getChildrenOfAnyType(element, RequirementsPackageStmt::class.java,
                    RequirementsEditableRequirementStmt::class.java,
                    RequirementsRequirementStmt::class.java,
                    RequirementsUrlStmt::class.java)
                    .map { RequirementsStructureViewElement(it as NavigatablePsiElement) }
                    .toTypedArray()
        }
        return emptyArray()
    }
}
