package Scanner;


import Token.Token;
import Token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class takes in raw text and converts the text into a list of tokens containing
 * information about each individual part.
 *
 * Should not need to be modified
 */
public class Scanner {
    private final List<Token> tokens = new ArrayList<>();
    private final String code;
    private final String splitRegex;

    public Scanner(String code) {
        this.code = code;
        this.splitRegex = ScannerRegex.getSplitRegex();
        createTokenList();
    }

    /**
     * Creates a list of tokens from the source code.
     * Keeps track of line number for error reporting purposes
     */
    private void createTokenList () {
        int lineNum = 1; // current line
        String[] lines = code.split("\n");
        for (String line : lines) {
            // make sure split occurs before and after an operator and on white space
            String[] tokenStrings = line.split(splitRegex);
            for (String token : tokenStrings) {
                if (!token.isEmpty()) {
                    TokenType type = ScannerRegex.match(token);
                    tokens.add(constructToken(type, token, lineNum));
                }
            }
            lineNum++;
        }
    }

    /**
     * Constructs a token given the type
     *
     * @param type TokenType object stating what type it is
     * @param lexeme raw code
     * @param lineNum line number to keep track of for error handling purposes
     * @return
     */
    private Token constructToken(TokenType type, String lexeme, int lineNum) {
        if (type.equals(TokenType.NUMBER)) {
            return new Token(type, lexeme, lineNum, Double.parseDouble(lexeme));
        } else if (type.equals(TokenType.BOOLEAN)) {
            return new Token(type, lexeme, lineNum, Boolean.parseBoolean(lexeme));
        } else if (type.equals(TokenType.STRING)) {
            return new Token(type, lexeme, lineNum, lexeme);
        } else {
            return new Token(type, lexeme, lineNum, null);
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
