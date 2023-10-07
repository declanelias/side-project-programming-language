package Scanner;


import Token.Token;
import Token.TokenConstructor;
import Token.TokenType;

import java.util.ArrayList;
import java.util.List;


public class Scanner {
    private final List<Token> tokens = new ArrayList<>();
    private final String code;
    private final String splitRegex;

    public Scanner(String code) {
        this.code = code;
        this.splitRegex = ScannerRegex.getSplitRegex();
        createTokenList();
    }

    private void createTokenList () {
        int lineNum = 1; // current line
        String[] lines = code.split("\n");
        for (String line : lines) {
            // make sure split occurs before and after an operator and on white space
            String[] tokenStrings = line.split(splitRegex);
            for (String token : tokenStrings) {
                if (!token.isEmpty()) {
                    TokenType type = ScannerRegex.match(token);
                    tokens.add(TokenConstructor.constructToken(type, token, lineNum));
                }
            }
            lineNum++;
        }
        tokens.add(new Token(TokenType.EOF, null, lineNum, null));
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
