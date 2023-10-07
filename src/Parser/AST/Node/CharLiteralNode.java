package Parser.AST.Node;

public final class CharLiteralNode {
    private final char charValue;

    public CharLiteralNode(char charValue) {
        this.charValue = charValue;
    }

    public char getCharValue() {
        return charValue;
    }
}
