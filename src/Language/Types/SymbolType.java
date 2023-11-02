package Language.Types;

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
        return "SymbolType{" +
                "name='" + name + '\'' +
                ", " + super.toString() +
                '}';
    }
}
