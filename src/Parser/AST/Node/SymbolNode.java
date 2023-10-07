package Parser.AST.Node;

public final class SymbolNode extends Node{

    private final String name;

    public SymbolNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SymbolNode{" +
                "name='" + name + '\'' +
                '}';
    }
}
