package de.ilijaz.myapp.myapp.core

import org.w3c.dom.Element
import org.w3c.dom.NodeList

open class SvgNode(
    val root: SvgRoot,
    val element: Element,
) {
    val children: List<SvgNode>
        get() = SvgNodeUtil.nodeListToNodeList(element.childNodes).map { nodeToSvgNode(root, it) }

    fun wrapChildren(wrapElement: SvgNode, attributes: Map<String, String> = mapOf()) {
        val children = nodeListToSvgNodeList(root, element.childNodes)
        children.forEach { element.removeChild(it.element) }
        children.forEach { wrapElement.element.appendChild(it.element) }
        attributes.keys.forEach { wrapElement.element.setAttribute(it, attributes[it]) }
        element.appendChild(wrapElement.element)
    }

    companion object {
        fun createElement(root: SvgRoot, tagName: String, children: List<SvgNode> = listOf()): SvgNode {
            val element = root.document.createElement(tagName)
            children.forEach { element.appendChild(it.element) }
            return nodeToSvgNode(root, element)
        }

        @JvmStatic
        fun nodeToSvgNode(root: SvgRoot, element: Element): SvgNode {
            removeTextNodes(element)
            return SvgNode(root, element)
        }

        @JvmStatic
        protected fun removeTextNodes(element: Element) {
            val textNodes = SvgNodeUtil.getTextNodesOfNodeList(element.childNodes)
            textNodes.forEach { element.removeChild(it) }
        }

        private fun nodeListToSvgNodeList(root: SvgRoot, nodeList: NodeList): List<SvgNode> =
            SvgNodeUtil.nodeListToNodeList(nodeList).map { nodeToSvgNode(root, it) }.toList()
    }
}