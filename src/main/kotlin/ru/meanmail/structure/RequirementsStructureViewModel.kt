package ru.meanmail.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.psi.PsiFile

class RequirementsStructureViewModel(psiFile: PsiFile) :
        StructureViewModelBase(psiFile, RequirementsStructureViewElement(psiFile)),
        StructureViewModel.ElementInfoProvider {

    override fun getSorters(): Array<Sorter> {
        return arrayOf(Sorter.ALPHA_SORTER)
    }


    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean {
        return false
    }

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
//        return element.value is RequirementsPackageStmt
        return false
    }
}
