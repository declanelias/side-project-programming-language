package Main.Language.Types;

import java.util.Objects;

/**
 * Holds information about strings in language
 */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringType that = (StringType) o;
        return Objects.equals(value, that.value);
    }
}
