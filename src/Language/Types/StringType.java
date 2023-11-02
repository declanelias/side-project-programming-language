package Language.Types;

public class StringType extends Type {

    private final String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
