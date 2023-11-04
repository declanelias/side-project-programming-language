package Language;

import Language.Types.ErrorType;
import Language.Types.ListType;
import Language.Types.SymbolType;
import Language.Types.Type;

import java.util.HashMap;
import java.util.Map;

public final class Environment {
    private final Environment outer;
    private final Map<String, Type> symbolMap = new HashMap<>();

    public Environment(Environment outer) {
        this.outer = outer;
    }

    public Environment(Environment outer, ListType params, ListType args) {
        this.outer = outer;

        bindParamsToArgs(params, args);

    }

    private void bindParamsToArgs(ListType params, ListType args) {
        if (params.size() != args.size()) {
            // TODO edit this to error
            System.err.println("wrong number of params");
        }

        for (int i = 0; i < params.size(); i++) {
            // TODO error
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

    public Type get(SymbolType symbol) {
        Environment environment = find(symbol);
        if (environment == null) {
            // TODO change this to error
            System.err.println(symbol + "not found");
            throw new RuntimeException(symbol + "not found");
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
