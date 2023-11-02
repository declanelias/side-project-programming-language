package Language.Evaluator;

import Language.Environment;
import Language.Types.*;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    Environment env;

    public Evaluator(Environment env) {
        this.env = env;
    }

    public Type evaluate(Type ast) {
        if (!(ast instanceof ListType astList)) {
            return checkForSymbol(ast);
        }
        Type first = astList.get(0);
        if (!(first instanceof SymbolType symbol)) {
            return first;
        } else {
            return evaluateSymbol(symbol, astList);
        }
    }

    private Type evaluateSymbol(SymbolType symbol, ListType listType) {
        switch (symbol.getName()) {
            case "def" ->   {return declareVariable(listType);}
            case "if" ->    {return evaluateIf(listType);}
            case "fn" ->    {return declareFunction(listType);}
            case "do" ->    {return evaluateDo(listType);}
            default ->      {return evaluateFunction(symbol, listType);}
        }
    }

    private FunctionType declareFunction(ListType listType) {
        return null;
    }

    private Type evaluateDo(ListType listType) {
        return null;
    }

    private Type evaluateFunction(SymbolType symbol, ListType listType) {
        Type envSymbol = env.get(symbol);
        if (envSymbol instanceof FunctionType func) {
            List<Type> list = new ArrayList<>();
            for (int i = 1; i < listType.size(); i++) {
                Type type = checkForSymbol(listType.get(i));
                list.add(evaluate(type));
            }

            return func.apply(list);
        } else {
            return envSymbol;
        }
    }

    private Type evaluateIf(ListType listType) {
        if (listType.size() != 4) {
            return new ErrorType("wrong");
        }

        Type firstArg = evaluate(listType.get(1));
        if (firstArg instanceof BooleanType condition) {
            if (condition.isTrue()) {
                return evaluate(listType.get(2));
            } else {
                return evaluate(listType.get(3));
            }
        } else {
            // TODO
            return new ErrorType("wrong");
        }
    }

    private Type declareVariable(ListType listType) {
        if (listType.size() != 3) {
            // TODO
            return new ErrorType("wrong!");
        }
        Type type = evaluate(listType.get(2));
        SymbolType varName = (SymbolType) listType.get(1);
        env.set(varName, type);
        return type;
    }

    private Type checkForSymbol(Type type) {
        if (type instanceof SymbolType symbol) {
            return env.get(symbol);
        }
        return type;
    }


}
