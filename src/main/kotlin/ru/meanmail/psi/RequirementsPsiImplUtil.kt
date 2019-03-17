package ru.meanmail.psi

import com.intellij.lang.ASTNode
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.jetbrains.python.packaging.PyPackageManager
import com.jetbrains.python.sdk.PythonSdkType

object RequirementsPsiImplUtil {
    @JvmStatic
    private fun getNode(element: PsiElement, types: List<IElementType>): ASTNode? {
        var keyNode = element.node
        for (type in types) {
            keyNode = keyNode.findChildByType(type) ?: return null
        }
        
        return keyNode
    }
    
    @JvmStatic
    private fun getValue(element: PsiElement, types: List<IElementType>): String? {
        return getNode(element, types)?.text
    }
    
    @JvmStatic
    fun getPackageName(element: RequirementsPackageStmt): String? {
        return getValue(element, listOf(RequirementsTypes.SIMPLE_PACKAGE_STMT,
                RequirementsTypes.PACKAGE))
    }
    
    @JvmStatic
    fun getVersionNode(element: RequirementsPackageStmt): ASTNode? {
        return getNode(element, listOf(RequirementsTypes.SIMPLE_PACKAGE_STMT,
                RequirementsTypes.VERSION))
    }
    
    @JvmStatic
    fun getVersion(element: RequirementsPackageStmt): String? {
        return getVersionNode(element)?.text
    }
    
    @JvmStatic
    fun getExtraPackage(element: RequirementsPackageStmt): String? {
        return getValue(element, listOf(RequirementsTypes.EXTRA,
                RequirementsTypes.PACKAGE))
    }
    
    @JvmStatic
    fun getFilename(element: RequirementsRequirementStmt): String? {
        return getValue(element, listOf(RequirementsTypes.FILENAME))
    }
    
    @JvmStatic
    fun getURL(element: RequirementsEditableRequirementStmt): String? {
        return (getValue(element, listOf(RequirementsTypes.URL_STMT,
                RequirementsTypes.PATH_STMT,
                RequirementsTypes.SCHEMA)) ?: "") +
                getValue(element, listOf(RequirementsTypes.URL_STMT,
                        RequirementsTypes.PATH_STMT,
                        RequirementsTypes.PATH))
    }
    
    @JvmStatic
    fun getBranch(element: RequirementsEditableRequirementStmt): String? {
        return getValue(element, listOf(RequirementsTypes.URL_STMT,
                RequirementsTypes.PATH_STMT,
                RequirementsTypes.BRANCH))
    }
    
    @JvmStatic
    fun getEgg(element: RequirementsEditableRequirementStmt): String? {
        return getValue(element, listOf(RequirementsTypes.URL_STMT,
                RequirementsTypes.PATH_STMT,
                RequirementsTypes.EGG))
    }
    
    @JvmStatic
    fun getURL(element: RequirementsUrlStmt): String? {
        return (getValue(element, listOf(RequirementsTypes.PATH_STMT,
                RequirementsTypes.SCHEMA)) ?: "") +
                getValue(element, listOf(RequirementsTypes.URL_STMT,
                        RequirementsTypes.PATH_STMT,
                        RequirementsTypes.PATH))
    }
    
    @JvmStatic
    fun getBranch(element: RequirementsUrlStmt): String? {
        return getValue(element, listOf(RequirementsTypes.PATH_STMT,
                RequirementsTypes.BRANCH))
    }
    
    @JvmStatic
    fun getEgg(element: RequirementsUrlStmt): String? {
        return getValue(element, listOf(RequirementsTypes.PATH_STMT,
                RequirementsTypes.EGG))
    }
    
    @JvmStatic
    fun getName(element: RequirementsEditableRequirementStmt): String? {
        return getValue(element, listOf(RequirementsTypes.URL_STMT,
                RequirementsTypes.SIMPLE_PACKAGE_STMT,
                RequirementsTypes.PACKAGE))
                ?: getValue(element, listOf(RequirementsTypes.URL_STMT,
                        RequirementsTypes.PATH_STMT))
    }
    
    @JvmStatic
    fun setName(element: RequirementsEditableRequirementStmt, newName: String): PsiElement {
        val urlStmt = element.node.findChildByType(RequirementsTypes.URL_STMT)
        val keyNode = urlStmt
                ?.findChildByType(RequirementsTypes.SIMPLE_PACKAGE_STMT)
                ?.findChildByType(RequirementsTypes.PACKAGE)
                ?: urlStmt?.findChildByType(RequirementsTypes.PATH_STMT)
                ?: return element
        val packageStmt = RequirementsElementFactory.createPackage(element.project, newName)
        val newKeyNode = packageStmt.firstChild.node
        element.node.replaceChild(keyNode, newKeyNode)
        return element
    }
    
    @JvmStatic
    fun getNameIdentifier(element: RequirementsEditableRequirementStmt): PsiElement? {
        val urlStmt = element.node.findChildByType(RequirementsTypes.URL_STMT)
        val keyNode = urlStmt?.findChildByType(RequirementsTypes.SIMPLE_PACKAGE_STMT)
                ?.findChildByType(RequirementsTypes.PACKAGE)
                ?: urlStmt?.findChildByType(RequirementsTypes.PATH_STMT)
        return keyNode?.psi
    }
    
    @JvmStatic
    fun getName(element: RequirementsUrlStmt): String? {
        return getValue(element, listOf(RequirementsTypes.SIMPLE_PACKAGE_STMT,
                RequirementsTypes.PACKAGE))
                ?: getValue(element, listOf(RequirementsTypes.PATH_STMT))
    }
    
    @JvmStatic
    fun setName(element: RequirementsUrlStmt, newName: String): PsiElement {
        val keyNode = element.node?.findChildByType(RequirementsTypes.SIMPLE_PACKAGE_STMT)
                ?.findChildByType(RequirementsTypes.PACKAGE)
                ?: element.node?.findChildByType(RequirementsTypes.PATH_STMT)
                ?: return element
        val packageStmt = RequirementsElementFactory.createPackage(element.project, newName)
        val newKeyNode = packageStmt.firstChild.node
        element.node.replaceChild(keyNode, newKeyNode)
        return element
    }
    
    @JvmStatic
    fun getNameIdentifier(element: RequirementsUrlStmt): PsiElement? {
        val keyNode = element.node?.findChildByType(RequirementsTypes.SIMPLE_PACKAGE_STMT)
                ?.findChildByType(RequirementsTypes.PACKAGE)
                ?: element.node?.findChildByType(RequirementsTypes.PATH_STMT)
        return keyNode?.psi
    }
    
    @JvmStatic
    fun getName(element: RequirementsPackageStmt): String? {
        return getPackageName(element)
    }
    
    @JvmStatic
    fun setName(element: RequirementsPackageStmt, newName: String): PsiElement {
        val keyNode = element.node.findChildByType(RequirementsTypes.SIMPLE_PACKAGE_STMT)
                ?.findChildByType(RequirementsTypes.PACKAGE) ?: return element
        val simplePackageStmt = RequirementsElementFactory.createSimplePackage(element.project, newName)
        val newKeyNode = simplePackageStmt.firstChild.node
        element.node.replaceChild(keyNode, newKeyNode)
        return element
    }
    
    @JvmStatic
    fun getNameIdentifier(element: RequirementsPackageStmt): PsiElement? {
        val keyNode = element.node.findChildByType(RequirementsTypes.SIMPLE_PACKAGE_STMT)
                ?.findChildByType(RequirementsTypes.PACKAGE)
        return keyNode?.psi
    }
    
    @JvmStatic
    fun getName(element: RequirementsRequirementStmt): String? {
        return getValue(element, listOf(RequirementsTypes.FILENAME))
    }
    
    @JvmStatic
    fun setName(element: RequirementsRequirementStmt, newName: String): PsiElement {
        val keyNode = element.node.findChildByType(RequirementsTypes.FILENAME) ?: return element
        val requirementsStmt = RequirementsElementFactory.createRequirements(element.project, newName)
        val newKeyNode = requirementsStmt.firstChild.node
        element.node.replaceChild(keyNode, newKeyNode)
        return element
    }
    
    @JvmStatic
    fun getNameIdentifier(element: RequirementsRequirementStmt): PsiElement? {
        val keyNode = element.node.findChildByType(RequirementsTypes.FILENAME)
        return keyNode?.psi
    }
    
    @JvmStatic
    fun getPackageManager(project: Project): PyPackageManager? {
        val projectRootManager = ProjectRootManager.getInstance(project)
        val projectSdk = projectRootManager.projectSdk ?: return null
        if (projectSdk.sdkType is PythonSdkType) {
            return PyPackageManager.getInstance(projectSdk)
        }
        return null
    }
}
