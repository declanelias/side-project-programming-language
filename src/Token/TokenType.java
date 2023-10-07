package Token;

public enum TokenType {
    // single character tokens
    OPEN_PAREN, CLOSE_PAREN, SEMICOLON,

    // Boolean
    BOOLEAN,

    // keywords
    SET,

    // literals
    IDENTIFIER, NUMBER, STRING,

    // math operations
    ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULUS,

    // Error
    ERROR,

    // end of file
    EOF
}
