package ru.meanmail

import com.intellij.lang.Commenter

class RequirementsCommenter : Commenter {
    
    override fun getCommentedBlockCommentPrefix(): String? = null
    
    override fun getCommentedBlockCommentSuffix(): String? = null
    
    override fun getBlockCommentPrefix(): String? = ""
    
    override fun getBlockCommentSuffix(): String? = null
    
    override fun getLineCommentPrefix(): String? = "# "
    
}
