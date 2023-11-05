package Main.Language.Reader;


import Main.Language.Types.*;
import Main.Language.Token.TokenType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class takes in raw text and converts the text into a list of tokens containing
 * information about each individual part.
 *
 * Should not need to be modified
 */
public class Reader {
    private final String code;
    private final String splitRegex;
    private int openParens = 0;
    private int closedParens = 0;

    public Reader(String code) {
        this.code = code;
        this.splitRegex = ScannerRegex.getSplitRegex();
    }

    public ListType createAst() throws ErrorType {
        // TODO take care of newline
        List<String> codeString = List.of(code.split(splitRegex));
        Iterator<String> iter = codeString.iterator();

        ListType ast = _createAst(iter);
        System.out.println(ast);
        return ast;
    }

    private ListType _createAst(Iterator<String> iter) throws ErrorType {
        List<Type> typeList = new ArrayList<>();
        while(iter.hasNext()) {
            String token = iter.next();
            TokenType tokenType = ScannerRegex.match(token);
            if (token.isEmpty()) {
                continue;
            }
            switch (tokenType) {
                case OPEN_PAREN -> {
                    openParens++;
                    typeList.add(_createAst(iter));
                }
                case CLOSE_PAREN -> {
                    closedParens++;
                    if (closedParens > openParens) {
                        throw new ErrorType("unexpected parenthesis");
                    }
                    return new ListType(typeList);
                }
                case NUMBER -> typeList.add(new NumberType(Double.parseDouble(token)));
                case STRING -> typeList.add(new StringType(token));
                case SYMBOL -> typeList.add(new SymbolType(token));
                case BOOLEAN -> typeList.add(new BooleanType(token));
                default -> throw new ErrorType("Invalid token " + tokenType.toString());
            }
        }

        if (openParens > closedParens) {
            throw new ErrorType("unclosed parenthesis");
        }

        return new ListType(typeList);
    }
}
