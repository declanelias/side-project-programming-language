package Main.Language.Types;

public class StringType extends Type {

    private final String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public StringType append(StringType s) {
        return new StringType(value + s);
    }
}
