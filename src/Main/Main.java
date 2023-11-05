package Main;

import Main.Language.Environment;
import Main.Language.Language;
import Main.Language.Types.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    // TODO make env not static so the environment clears on every run
    private static Environment env;

    public static void main(String[] args) {
        boolean useTextEditor = true;
        if (useTextEditor) {
            runTextEditor();
        } else {
            startREPL();
        }
    }

    private static void runTextEditor() {
        TextEditor editor = new TextEditor();

        buildTextEditorEnv(editor.getOutputArea());
        Language.setEnv(env);

        buildTextEditorEnv(editor.getOutputArea());
        SwingUtilities.invokeLater(editor::createUI);
    }

    private static void startREPL() {
        buildReplEnv();
        Language.setEnv(env);
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        for (;;) {
            System.out.print("> ");
            try {
                String line = reader.readLine();
                if (line == null) break;
                try {
                    System.out.println(Language.run(line));
                } catch (ErrorType e) {
                    System.out.println(e);
                }
            } catch (IOException e) {
                    System.err.println("Error " + e);
            }
        }
    }


    private static void buildEnv() {

         env = new Environment();

//        FunctionType add = new FunctionType(
////                (o) -> (o.stream()
////                        .map(c -> (NumberType) c)
////                        .reduce(NumberType::add)
////                        .orElse(new NumberType(0))
////                )
//        );

        FunctionType add = new FunctionType() {
            @Override
            public Type apply(ListType list) {
                return ((NumberType) list.get(0)).add((NumberType) list.get(1));
            }
        };

        FunctionType append = new FunctionType() {
            @Override
            public Type apply(ListType list) {
                return ((StringType) list.get(0)).append(((StringType) list.get(1)));
            }
        };


        env.set("+", add);
        env.set("append", append);
    }

    private static void buildTextEditorEnv(JTextArea outputArea) {
        buildEnv();

        FunctionType print = new FunctionType() {
            @Override
            public Type apply(ListType list) {
                Type t = list.get(0);
                outputArea.append(t.toString() + "\n");
                return t;
            }
        };

        env.set("print", print);
    }

    private static void buildReplEnv() {
        buildEnv();

        FunctionType print = new FunctionType() {
            @Override
            public Type apply(ListType list) {
                System.out.println(list);
                return list;
            }
        };

        env.set("print", print);
    }

}
