package Parser.AST.Node;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent top node of AST. Will hold a list of ListNodes
 */
public class ProgramNode extends Node{
    private final List<ListNode> listNodes = new ArrayList<>();

    public void add(ListNode listNode) {
        listNodes.add(listNode);
    }

    public List<ListNode> getListNodes() {
        return Collections.unmodifiableList(listNodes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ProgramNode{\n\t");
        for (ListNode listNode : listNodes) {
            sb.append(listNode.toString()).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
