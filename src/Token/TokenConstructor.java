package Token;

public class TokenConstructor {

    public static Token constructToken(TokenType type, String string, int lineNum) {
        if (type.equals(TokenType.NUMBER)) {
            return new Token(type, string, lineNum, Double.parseDouble(string));
        } else if (type.equals(TokenType.BOOLEAN)) {
            return new Token(type, string, lineNum, Boolean.parseBoolean(string));
        } else if (type.equals(TokenType.STRING)) {
            return new Token(type, string, lineNum, string);
        }

        else {
            return new Token(type, string, lineNum, null);
        }
    }
}
