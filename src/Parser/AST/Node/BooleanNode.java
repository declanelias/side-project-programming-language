package Parser.AST.Node;

public final class BooleanNode extends Node{
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BooleanNode{" +
                "value=" + value +
                '}';
    }
}
