package Main;

import Main.Language.Environment;
import Main.Language.Language;
import Main.Language.NameSpace;
import Main.Language.Types.*;
import Main.Language.Types.LanguageError.*;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        getUserChoice();
    }

    /**
     * Gets user choice and either runs text editor or starts repl
     */
    private static void getUserChoice() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Use text editor? (y/n)");
            String choice = reader.readLine();

            if (choice.equals("y")) {
                runTextEditor();
            } else if (choice.equals("n")) {
                System.out.println("-----------------------------------------");
                startREPL();
            } else {
                System.out.println("Please enter preference again");
                getUserChoice();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts text editor if user decides
     */
    private static void runTextEditor() {
        TextEditor editor = new TextEditor();

        SwingUtilities.invokeLater(editor::createUI);
    }

    /**
     * Starts REPL if user decides
     */
    private static void startREPL() {
        NameSpace ns = new NameSpace(false);
        Environment env = ns.getEnv();
        Language.setEnv(env);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            for (; ; ) {
                System.out.print("> ");
                String line = br.readLine();
                if (line.isEmpty()) {
                    continue;
                } else if (line.equals("q")) {
                    break;
                } else {
                    System.out.println(Language.run(line));
                }
            }
        } catch (IOException e) {
            System.err.println("Error " + e);
        } catch (LanguageError e) {
            System.out.println(e);
        }
    }
}
