package Main.Language.Types;

/**
 * Errors for language
 */
public class LanguageError extends Throwable{

    private final String msg;

    public LanguageError(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ERROR: " + msg;
    }

    public static class LanguageParseError extends LanguageError {
        public LanguageParseError(String msg) {
            super(msg);
        }

        @Override
        public String toString() {
            return "PARSE " + super.toString();
        }
    }

    public static class LanguageFunctionError extends LanguageError{

        public LanguageFunctionError() {
            super("Wrong number of arguments passed to function");
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
