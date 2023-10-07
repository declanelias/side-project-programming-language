package Token;

/**
 * Enum for types of tokens
 */
public enum TokenType {
    // single character tokens
    OPEN_PAREN, CLOSE_PAREN, SEMICOLON,

    // keywords
    SET,

    // types
    SYMBOL, NUMBER, STRING, BOOLEAN,

    // Error
    ERROR
}
