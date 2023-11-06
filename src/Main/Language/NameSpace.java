package Main.Language;

import Main.Language.Types.*;

import javax.swing.*;

public class NameSpace {

    private final Environment env;

    public NameSpace(boolean textEditorUsed) {
        env = new Environment();
        addGeneralFunctions();
        addStringFunctions();
        addNumberFunctions();
        addPrintFunction(textEditorUsed, null);
    }

    public NameSpace(boolean textEditorUsed, JTextArea outputArea) {
        env = new Environment();
        addGeneralFunctions();
        addStringFunctions();
        addNumberFunctions();
        addPrintFunction(textEditorUsed, outputArea);
    }

    public Environment getEnv() {
        return env;
    }

    public void addPrintFunction(boolean textEditorUsed, JTextArea outputArea) {
        FunctionType print;
        if (textEditorUsed) {
            print = new FunctionType() {
                @Override
                public Type apply(ListType list) throws LanguageError {
                    checkForError(list, 1);

                    Type t = list.get(0);
                    outputArea.append(t.toString() + "\n");
                    return t;
                }
            };
        } else {
            print = new FunctionType() {
                @Override
                public Type apply(ListType list) throws LanguageError {
                    checkForError(list, 1);
                    Type t = list.get(0);
                    System.out.println(t);
                    return t;
                }
            };
        }

        env.set("print", print);
    }

    public void addGeneralFunctions() {
        FunctionType equals = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                return new BooleanType(list.get(0).equals(list.get(1)));
            }
        };

        env.set("==", equals);
    }

    private void addNumberFunctions () {

        FunctionType add = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                return ((NumberType) list.get(0)).add((NumberType) list.get(1));
            }
        };

        FunctionType subtract = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                return ((NumberType) list.get(0)).subtract((NumberType) list.get(1));
            }
        };

        FunctionType mulitply = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                return ((NumberType) list.get(0)).multiply((NumberType) list.get(1));
            }
        };

        FunctionType divide = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                return ((NumberType) list.get(0)).divide((NumberType) list.get(1));
            }
        };

        FunctionType lt = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                boolean b = ((NumberType) list.get(0)).getValue() < ((NumberType) list.get(1)).getValue();
                return new BooleanType(b);
            }
        };

        FunctionType lte = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                boolean b = ((NumberType) list.get(0)).getValue() <= ((NumberType) list.get(1)).getValue();
                return new BooleanType(b);
            }
        };

        FunctionType gt = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                boolean b = ((NumberType) list.get(0)).getValue() > ((NumberType) list.get(1)).getValue();
                return new BooleanType(b);
            }
        };

        FunctionType gte = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                checkForError(list, 2);
                boolean b = ((NumberType) list.get(0)).getValue() >= ((NumberType) list.get(1)).getValue();
                return new BooleanType(b);
            }
        };

        env.set("+", add);
        env.set("-", subtract);
        env.set("*", mulitply);
        env.set("/", divide);

        env.set("<", lt);
        env.set(">", gt);
        env.set("<=", lte);
        env.set(">=", gte);
    }

    public void addStringFunctions() {
        FunctionType append = new FunctionType() {
            @Override
            public Type apply(ListType list) throws LanguageError {
                if (list.size() > 2) {
                    throw new LanguageError.LanguageFunctionError();
                }
                return ((StringType) list.get(0)).append(((StringType) list.get(1)));
            }
        };
        env.set("append", append);

    }

    private void checkForError(ListType list, int numParams) throws LanguageError {
        if (list.size() > numParams) {
            throw new LanguageError.LanguageFunctionError();
        }
    }

}
