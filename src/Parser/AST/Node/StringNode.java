package Parser.AST.Node;

public final class StringNode extends LiteralNode {
    private final String string;

    public StringNode(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return "StringNode{" +
                "string='" + string + '\'' +
                '}';
    }
}
