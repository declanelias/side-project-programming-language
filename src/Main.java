import Parser.AST.ASTConstructor;
import Parser.AST.Node.ProgramNode;
import Scanner.*;
import Token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        startREPL();
    }

    private static void run(String code) {
        Scanner scanner = new Scanner(code);
        List<Token> tokens = scanner.getTokens();

        ASTConstructor astConstructor = new ASTConstructor(tokens);
        ProgramNode pn = astConstructor.getAst();
        System.out.println(pn);
    }


    public static void startREPL() {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (;;) {
            System.out.print("> ");
            try {
                String line = reader.readLine();
                if (line == null) break;
                run(line);
            } catch (IOException e) {
                System.out.println("Error " + e);
            }
        }
    }

}