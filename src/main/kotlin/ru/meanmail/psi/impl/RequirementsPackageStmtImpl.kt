package ru.meanmail.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.FileContentUtil
import com.jetbrains.python.packaging.PyExecutionException
import com.jetbrains.python.packaging.PyPackage
import com.jetbrains.python.packaging.PyPackageUtil
import ru.meanmail.psi.*


class RequirementsPackageStmtImpl(node: ASTNode) : RequirementsNamedElementImpl(node), RequirementsPackageStmt {
    
    override val extra: RequirementsExtra?
        get() = findChildByClass(RequirementsExtra::class.java)
    
    override val simplePackageStmt: RequirementsSimplePackageStmt
        get() = findNotNullChildByClass(RequirementsSimplePackageStmt::class.java)
    
    override val packageName: String?
        get() = RequirementsPsiImplUtil.getPackageName(this)
    
    override val version: String?
        get() = RequirementsPsiImplUtil.getVersion(this)
    
    override val extraPackage: String?
        get() = RequirementsPsiImplUtil.getExtraPackage(this)
    
    private fun accept(visitor: RequirementsVisitor) {
        visitor.visitPackageStmt(this)
    }
    
    override fun accept(visitor: PsiElementVisitor) {
        if (visitor is RequirementsVisitor)
            accept(visitor)
        else
            super.accept(visitor)
    }
    
    override fun getName(): String? {
        return RequirementsPsiImplUtil.getName(this)
    }
    
    override fun setName(newName: String): PsiElement {
        return RequirementsPsiImplUtil.setName(this, newName)
    }
    
    override fun getNameIdentifier(): PsiElement? {
        return RequirementsPsiImplUtil.getNameIdentifier(this)
    }
    
    private val pyPackage: PyPackage?
        get() {
            val packageManager = RequirementsPsiImplUtil.getPackageManager(project) ?: return null
            val packageName = packageName ?: return null
            return PyPackageUtil.findPackage(packageManager.refreshAndGetPackages(false),
                    packageName)
        }
    
    override val isInstalled: Boolean
        get() = pyPackage?.isInstalled ?: false
    
    override fun install() {
        var title = "Installing '$packageName'"
        if (version != null) {
            title += " version '$version'..."
        }
        
        val task = object : Task.Backgroundable(
                project, title
        ) {
            override fun run(indicator: ProgressIndicator) {
                indicator.text = this.title
                indicator.isIndeterminate = true
                
                val application = ApplicationManager.getApplication()
                
                application.runReadAction {
                    try {
                        val packageManager = RequirementsPsiImplUtil.getPackageManager(project)
                        
                        if (packageManager != null) {
                            packageManager.install(text)
                        } else {
                            Notification("pip",
                                    title,
                                    "Package manager is not available",
                                    NotificationType.ERROR).notify(project)
                            return@runReadAction
                        }
                        val pyPackage = pyPackage
                        
                        if (pyPackage == null) {
                            Notification("pip",
                                    title,
                                    "Failed. Not installed",
                                    NotificationType.ERROR).notify(project)
                            return@runReadAction
                        }
                        
                        Notification("pip",
                                "${pyPackage.name} (${pyPackage.version})",
                                "Successfully installed",
                                NotificationType.INFORMATION).notify(project)
                        
                        application.invokeLater {
                            application.runWriteAction {
                                val file = this@RequirementsPackageStmtImpl.containingFile
                                FileContentUtil.reparseFiles(file.virtualFile)
                            }
                        }
                    } catch (e: PyExecutionException) {
                        Notification(e.command,
                                e.stdout,
                                e.stderr,
                                NotificationType.ERROR).notify(project)
                    }
                }
            }
        }
        
        ProgressManager.getInstance().run(task)
    }
}
