package Parser.AST;

import Parser.AST.Node.*;
import Token.*;

import java.util.List;
import java.util.Set;

/**
 * Constructs the Abstract Syntax Tree for the scanner
 *
 * TODO handle errors (replace all the return nulls)
 * TODO make code not gross
 * TODO get boolean to work
 */
public class ASTConstructor {
    private List<Token> tokens;
    private int currentIndex;
    private ProgramNode ast;

    public ASTConstructor(List<Token> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
        ast = parseProgram();
    }

    /**
     * @return Abstract Syntax Tree represented by ProgramNode
     */
    public ProgramNode getAst() {
        return ast;
    }

    /**
     * Check if there are more tokens
     *
     * @return true or false
     */
    private boolean hasMoreTokens() {
        return currentIndex < tokens.size();
    }

    /**
     * @return current token given the index
     */
    private Token getCurrentToken() {
        if (hasMoreTokens()) {
            return tokens.get(currentIndex);
        }
        return null;
    }

    /**
     * Advance the current index. Included to make code more readable
     */
    private void advance() {
        currentIndex++;
    }

    /**
     * @return SymbolNode if token node is a symbol
     */
    private SymbolNode parseSymbol() {
        Token token = getCurrentToken();
        advance();
        return new SymbolNode(token.getLexeme());
    }

    /**
     * @return NumberNode if current token is a number
     */
    private NumberNode parseNumber() {
        Token token = getCurrentToken();
        advance();
        return new NumberNode(Double.parseDouble(token.getLexeme()));
    }

    /**
     * returns a variable declaration node
     *
     * @return SetNode containing variable declaration
     */

    private SetNode parseSet() {
        Token setToken = getCurrentToken();
        advance();
        Token symbol = getCurrentToken();
        advance();
        Token value = getCurrentToken();
        advance();
        SymbolNode sn = new SymbolNode(symbol.getLexeme());
        LiteralNode ln;
        if (value.getTokenType() == TokenType.NUMBER) {
            ln = new NumberNode((Double) value.getVarValue());
        } else if (value.getTokenType() == TokenType.STRING) {
            ln = new StringNode((String) value.getVarValue());
        } else {
            // TODO catch error
            ln = null;
        }
        return new SetNode(sn, ln);
    }

    /**
     * @return BooleanNode if current token is boolean
     */
    private BooleanNode parseBoolean() {
        Token token = getCurrentToken();
        advance();
        return new BooleanNode(Boolean.parseBoolean(token.getLexeme().toLowerCase()));
    }

    /**
     * When an open paren is detected, parse the list and
     * return a list node. Called recursively when a nested list
     * is found
     *
     * // TODO implement boolean
     *
     * @return ListNode of the parsed list
     */
    private ListNode parseList() {
        ListNode listNode = new ListNode();
        // Consume the opening parenthesis
        advance();

        while (hasMoreTokens()) {
            Token token = getCurrentToken();
            TokenType type = token.getTokenType();

            // Check for the closing parenthesis
            if (type == TokenType.CLOSE_PAREN) {
                advance();
                break;
            }

            // Parse expressions or symbols inside the list
            if (type == TokenType.SYMBOL) {
                listNode.add(parseSymbol());
            } else if (type == TokenType.NUMBER) {
                listNode.add(parseNumber());
            } else if (type == TokenType.SET) {
                listNode.add(parseSet());
            }
            //TODO figure out why this isn't working
//            else if (token.getTokenType() == TokenType.BOOLEAN) {
//                listNode.add(new BooleanNode(true));
//            }
            else if (type == TokenType.OPEN_PAREN) {
                listNode.add(parseList());
            } else {
                // Handle error: Invalid token inside the list
                return null;
            }
        }

        return listNode;
    }

    /**
     * Starts the parsing of the Tokens. Program node is always
     * top level of the AST.
     *
     * @return ProgramNode containing the AST
     */
    public ProgramNode parseProgram() {
        ProgramNode programNode = new ProgramNode();

        while (hasMoreTokens()) {
            Token token = getCurrentToken();

            if (token.getTokenType() == TokenType.OPEN_PAREN) {
                programNode.add(parseList());
            } else {
                // Handle error: Unexpected token outside of a list
                return null;
            }
        }

        return programNode;
    }
}
