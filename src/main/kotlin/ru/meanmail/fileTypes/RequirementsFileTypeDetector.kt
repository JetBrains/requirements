package ru.meanmail.fileTypes

import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.util.io.ByteSequence
import com.intellij.openapi.vfs.VirtualFile

class RequirementsFileTypeDetector : FileTypeRegistry.FileTypeDetector {
    override fun detect(
        file: VirtualFile, firstBytes: ByteSequence, firstCharsIfText: CharSequence?
    ): FileType? {
        if (file.parent != null && file.parent.name == "requirements") {
            return RequirementsFileType
        }

        return null
    }
}
