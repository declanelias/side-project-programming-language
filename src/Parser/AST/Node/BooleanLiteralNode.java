package Parser.AST.Node;

public final class BooleanLiteralNode {
    private final boolean value;

    public BooleanLiteralNode(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }
}
