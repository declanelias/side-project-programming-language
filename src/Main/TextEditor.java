package Main;

import Main.Language.Environment;
import Main.Language.Language;
import Main.Language.NameSpace;
import Main.Language.Types.LanguageError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextEditor {
    private JTextArea codeArea = new JTextArea(20, 50);
    private JTextArea outputArea = new JTextArea(10, 50);
    private JFrame frame = new JFrame("Text Editor");

    public void createUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new OpenActionListener());

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new SaveActionListener());

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);

        menuBar.add(fileMenu);

        JMenu runMenu = new JMenu("Run");

        JMenuItem runMenuItem = new JMenuItem("Run");


        runMenuItem.addActionListener(new RunActionListener());

        runMenu.add(runMenuItem);

        menuBar.add(runMenu);
        frame.setJMenuBar(menuBar);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(codeArea), BorderLayout.NORTH);
        panel.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private class OpenActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file)) ) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    codeArea.setText(sb.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (PrintWriter writer = new PrintWriter(file)) {
                    writer.print(codeArea.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private class RunActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("");
            NameSpace ns = new NameSpace(true, outputArea);
            Environment env = ns.getEnv();
            Language.setEnv(env);

            String code = codeArea.getText();

            outputArea.append("Running\n\n");

            try {
                Language.run(code);
            } catch (LanguageError ex) {
                outputArea.append(ex + "\n");
            }
            outputArea.append("\nComplete\n");
        }
    }
}