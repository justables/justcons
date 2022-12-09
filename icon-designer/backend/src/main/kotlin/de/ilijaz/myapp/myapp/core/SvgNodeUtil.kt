package de.ilijaz.myapp.myapp.core

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.util.stream.IntStream

class SvgNodeUtil {
    companion object {
        fun nodeListToNodeList(nodeList: NodeList): List<Element> =
            IntStream.range(0, nodeList.length).mapToObj(nodeList::item).toList().filterIsInstance<Element>()

        fun getTextNodesOfNodeList(nodeList: NodeList): List<Node> =
            IntStream.range(0, nodeList.length).mapToObj(nodeList::item).toList().filter { it !is Element }
    }
}