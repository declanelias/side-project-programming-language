package Scanner;
import Token.TokenType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


// TODO figure out regex to allow for all keyboard characters to be allowed for char

public class ScannerRegex {

    private static final Map<String, TokenType> patternMap = new HashMap<>();
    private static final String splitRegex = "\\s+|(?<=[+\\-*/();])|(?=[+\\-*/();])";

    static {
        // single characters
        patternMap.put(";", TokenType.SEMICOLON);
        patternMap.put("\\(", TokenType.OPEN_PAREN);
        patternMap.put("\\)", TokenType.CLOSE_PAREN);

        // keywords
        patternMap.put("set", TokenType.SET);

        // Boolean
        patternMap.put("TRUE|FALSE", TokenType.BOOLEAN);

        // identifiers and literals
        patternMap.put("['][a-zA-Z0-9]*[']", TokenType.STRING);
        patternMap.put("^(?!DECLARE$|AS$|TRUE$|FALSE$)[A-Za-z_][A-Za-z0-9_]*$", TokenType.IDENTIFIER);
        patternMap.put("[-]?[0-9]*.?[0-9]+", TokenType.NUMBER);

        // math operations
        patternMap.put("\\+", TokenType.ADD);
        patternMap.put("-", TokenType.SUBTRACT);
        patternMap.put("\\*", TokenType.MULTIPLY);
        patternMap.put("/", TokenType.DIVIDE);
        patternMap.put("%", TokenType.MODULUS);
    }

    public static String getSplitRegex() {
        return splitRegex;
    }

    public static TokenType match(String string) {

        for (String pattern : patternMap.keySet()) {
            if (Pattern.matches(pattern, string)) {
                TokenType type = patternMap.get(pattern);
                return type;
            }
        }
        // TODO change this to return error type
        return TokenType.ERROR;
    }
}
