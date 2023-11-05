package Main.Language;

import Main.Language.Evaluator.Evaluator;
import Main.Language.Reader.Reader;
import Main.Language.Types.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Language {
    private static Environment env;

    public static void setEnv(Environment env) {
        Language.env = env;
    }

    public static String run(String code) throws ErrorType {
        Reader reader = new Reader(code);
        ListType ast = reader.createAst();
        Evaluator evaluator = new Evaluator();
        Type type = evaluator.evaluate(ast, env);
        return type.toString();
    }

}