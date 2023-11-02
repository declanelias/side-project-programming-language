package Language.Reader;


import Language.Token.TokenType;
import Language.Types.*;

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

    public Reader(String code) {
        this.code = code;
        this.splitRegex = ScannerRegex.getSplitRegex();
    }

    public ListType createAst() {
        // TODO take care of newline
        List<String> codeString = List.of(code.split(splitRegex));
        Iterator<String> iter = codeString.iterator();

        Type ast = _createAst(iter).get(0);
        if (!(ast instanceof ListType)) {
            System.err.println("WRONG!");
            System.exit(1);
        }
        return (ListType) ast;
    }

    private ListType _createAst(Iterator<String> iter) {
        List<Type> typeList = new ArrayList<>();
        int lineNum = 0;
        while(iter.hasNext()) {
            String token = iter.next();
            TokenType tokenType = ScannerRegex.match(token);
            if (token.isEmpty()) {
                continue;
            }
            switch (tokenType) {
                case OPEN_PAREN -> typeList.add(_createAst(iter));
                case CLOSE_PAREN -> {return new ListType(typeList);}
                case NUMBER -> typeList.add(new NumberType(Double.parseDouble(token)));
                case STRING -> typeList.add(new StringType(token));
                case SYMBOL -> typeList.add(new SymbolType(token));
                case BOOLEAN -> typeList.add(new BooleanType(token));
                default -> typeList.add(new ErrorType(token));
            }
        }

        return new ListType(typeList);
    }
}
