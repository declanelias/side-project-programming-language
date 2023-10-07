package Parser.AST.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListNode extends Node{
    private final List<Node> elements;

    public ListNode() {
        this.elements = new ArrayList<>();
    }

    public void add(Node node) {
        elements.add(node);
    }

    public List<Node> getElements() {
        return Collections.unmodifiableList(elements);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ListNode{\n\t\t");
        for (Node node : elements) {
            sb.append(node.toString());
            sb.append("\n\t\t");
        }
        sb.append("}");
        return sb.toString();
    }
}
