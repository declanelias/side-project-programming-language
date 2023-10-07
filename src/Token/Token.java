package Token;

/**
 * Class to store data for each token
 */
public class Token {

    final TokenType tokenType;
    final String lexeme; // individual part of the code
    final int lineNum;
    final Object varValue;

    public Token(TokenType tokenType, String lexeme, int lineNum, Object varValue) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.lineNum = lineNum;
        this.varValue = varValue;
    }

    @Override
    public String toString() {
        return "\n\ttokenType = " + tokenType +
                ",\n\tlexeme = " + lexeme +
                ",\n\tlineNum = " + lineNum +
                ",\n\tvarValue = " + varValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Object getVarValue() {
        return varValue;
    }

    public String getLexeme() {
        return lexeme;
    }
}
