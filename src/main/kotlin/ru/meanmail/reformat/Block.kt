package ru.meanmail.reformat

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.formatter.common.AbstractBlock
import ru.meanmail.psi.Types
import java.util.*

class Block constructor(
    node: ASTNode, wrap: Wrap?, alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder
) : AbstractBlock(node, wrap, alignment) {

    override fun buildChildren(): List<com.intellij.formatting.Block> {
        val blocks = ArrayList<com.intellij.formatting.Block>()
        var child: ASTNode? = myNode.firstChildNode
        while (child != null) {
            if (child.elementType !== Types.WHITE_SPACE) {
                val block = Block(
                    child,
                    Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    spacingBuilder
                )
                blocks.add(block)
            }
            child = child.treeNext
        }
        return blocks
    }

    override fun getIndent(): Indent? {
        return Indent.getNoneIndent()
    }

    override fun getSpacing(child1: com.intellij.formatting.Block?, child2: com.intellij.formatting.Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }
}
