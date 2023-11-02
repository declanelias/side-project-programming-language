package Language.TextEditor;

import javax.swing.*;


public class TextEditor {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Code Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);


        JTextArea codeInput = new JTextArea();
        JPanel panel = new JPanel();
        panel.add(codeInput);
        frame.setVisible(true);
    }
}
