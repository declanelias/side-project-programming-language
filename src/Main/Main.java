package Main;

import Main.Language.Environment;
import Main.Language.Language;
import Main.Language.NameSpace;
import Main.Language.Types.*;
import Main.Language.Types.LanguageError.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

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

        SwingUtilities.invokeLater(editor::createUI);
    }

    private static void startREPL() {
        NameSpace ns = new NameSpace(false);
        Environment env = ns.getEnv();
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
                } catch (LanguageError e) {
                    System.out.println(e);
                }
            } catch (IOException e) {
                    System.err.println("Error " + e);
            }
        }
    }

}
