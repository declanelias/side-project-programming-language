package Main.Language.Types;

import java.util.Objects;

public class SymbolType extends Type{

    private final String name;

    public SymbolType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolType that = (SymbolType) o;
        return Objects.equals(name, that.name);
    }
}
