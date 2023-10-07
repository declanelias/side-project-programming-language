package Parser.AST.Node;

public final class NameNode extends Node {
    private final String name;

    public NameNode(NodeKind type, Node left, Node right, String name) {
        super(type, left, right);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
