package Parser.AST.Node;

public final class NumberNode extends LiteralNode {
    private final double value;

    public NumberNode(double value) {

        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "value=" + value +
                '}';
    }
}
