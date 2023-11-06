package Main.Language;

import Main.Language.Types.LanguageError;
import Main.Language.Types.ListType;
import Main.Language.Types.SymbolType;
import Main.Language.Types.Type;

import java.util.HashMap;
import java.util.Map;

public final class Environment {
    private final Environment outer;
    private final Map<String, Type> symbolMap = new HashMap<>();

    public Environment() {
        this.outer = null;
    }
    public Environment(Environment outer) {
        this.outer = outer;
    }

    public Environment(Environment outer, ListType params, ListType args) {
        this.outer = outer;

        bindParamsToArgs(params, args);

    }

    private void bindParamsToArgs(ListType params, ListType args) {
//        if (params.size() != args.size()) {
//            System.err.println("wrong number of params");
//            throw new LanguageError("Wrong number of parameters");
//        }

        for (int i = 0; i < params.size(); i++) {
            set(((SymbolType) params.get(i)).getName(), args.get(i));
        }
    }

    public void set(String varName, Type var) {
        symbolMap.put(varName, var);
    }

    public Environment find(SymbolType symbol) {
        if (symbolMap.containsKey(symbol.getName())) {
            return this;
        } else if (outer != null) {
            return outer.find(symbol);
        } else {
            return null;
        }
    }

    public Type get(SymbolType symbol) throws LanguageError {
        Environment environment = find(symbol);
        if (environment == null) {
            // TODO change this to error
            throw new LanguageError(symbol + " not found");
        } else {
            return environment.symbolMap.get(symbol.getName());
        }
    }


    public void set(SymbolType symbol, Type value) {
        symbolMap.put(symbol.getName(), value);
    }

    @Override
    public String toString() {
        return symbolMap.toString();
    }
}
