package Parser.AST.Node;

public final class NumberLiteralNode extends Node {
    private final double value;

    public NumberLiteralNode(NodeKind type, Node left, Node right, double value) {
        super(type, left, right);
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
