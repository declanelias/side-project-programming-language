package Main.Language.Reader;
import Main.Language.Token.TokenType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Keeps information about Regex needed for scanning.
 *
 * Separated from Scanner class in case I need to change
 * regex logic. If there is an error I know it is with logic
 * and not within the Scanner class.
 */
public class ScannerRegex {

    private static final Map<String, TokenType> patternMap = new HashMap<>();
    private static final String splitRegex = "\\s+|(?<=[()])|(?=[()])";

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
        patternMap.put("^(?!set$|TRUE$|FALSE$)[A-Za-z_][A-Za-z0-9_]*$", TokenType.SYMBOL);
        patternMap.put("[-]?[0-9]*.?[0-9]+", TokenType.NUMBER);

        // math operations
        patternMap.put("\\+", TokenType.SYMBOL);
        patternMap.put("-", TokenType.SYMBOL);
        patternMap.put("\\*", TokenType.SYMBOL);
        patternMap.put("/", TokenType.SYMBOL);
        patternMap.put("%", TokenType.SYMBOL);
        patternMap.put(">=", TokenType.SYMBOL);
        patternMap.put("<=", TokenType.SYMBOL);
        patternMap.put(">", TokenType.SYMBOL);
        patternMap.put("<", TokenType.SYMBOL);
        patternMap.put("==", TokenType.SYMBOL);
    }


    public static String getSplitRegex() {
        return splitRegex;
    }

    /**
     * Takes input string and matches it to a type or returns an error type
     *
     * @param string code to be matched to a token
     * @return TokenType containing matched pattern or return general error type
     *
     * TODO Impliment more specific error types
     */
    public static TokenType match(String string) {

        for (String pattern : patternMap.keySet()) {
            if (Pattern.matches(pattern, string)) {
                TokenType type = patternMap.get(pattern);
                return type;
            }
        }

        return TokenType.ERROR;
    }
}
