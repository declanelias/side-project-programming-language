package Language;

import Language.Evaluator.Evaluator;
import Language.Reader.Reader;
import Language.Types.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    static Environment env;

    public static void main(String[] args) {
        env = new Environment(null);

        FunctionType add = new FunctionType(
                (o) -> (o.stream()
                        .map(c -> (NumberType) c)
                        .reduce(NumberType::add)
                        .orElse(new NumberType(0))
                )
        );

        FunctionType subtract = new FunctionType(
                (o) -> (o.stream()
                        .map(c -> (NumberType) c)
                        .reduce(NumberType::subtract)
                        .orElse(new NumberType(0))
                )
        );


        env.set("+", add);
        env.set("-", subtract);
        startREPL();
    }

    private static void run(String code) {
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();
        Evaluator evaluator = new Evaluator(env);
        Type type = evaluator.evaluate(ast);
        System.out.println(type);
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