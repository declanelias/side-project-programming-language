package Parser.AST.Node;

public final class SetNode extends Node{

    private final SymbolNode symbolNode;
    private final LiteralNode literalNode;

    public SetNode(SymbolNode symbolNode, LiteralNode literalNode) {
        this.symbolNode = symbolNode;
        this.literalNode = literalNode;
    }

    @Override
    public String toString() {
        return "SetNode{" +
                symbolNode.toString() +
                ", " + literalNode.toString() +
                '}';
    }
}
