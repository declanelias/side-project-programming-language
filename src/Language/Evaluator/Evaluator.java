package Language.Evaluator;

import Language.Environment;
import Language.Types.*;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    public Type evaluate(Type ast, Environment env) {
        if (!(ast instanceof ListType astList)) {
            return checkForSymbol(ast, env);
        }

        Type first = astList.get(0);

        if (first instanceof SymbolType symbol) {
            return evaluateSymbol(symbol, astList, env);
        } else if (first instanceof FunctionType func) {
            return evaluateFunction(func, astList.restOfList(), env);
        } else if (first instanceof NumberType || first instanceof StringType) {
            return evaluateList(astList, env);
        } else {
            return evaluate(evaluateList(astList, env), env);
        }
    }

    private Type evaluateList(ListType astList, Environment env) {
        List<Type> list = new ArrayList<>();
        for (int i = 0; i < astList.size(); i++) {
            list.add(evaluate(astList.get(i), env));
        }

        return new ListType(list);
    }

    private Type evaluateSymbol(SymbolType symbol, ListType listType, Environment env) {
        switch (symbol.getName()) {
            case "def" ->   {return declareVariable(listType, env);}
            case "if" ->    {return evaluateIf(listType.restOfList(), env);}
            case "fn" ->    {return declareFunction(listType, env);}
            case "do" ->    {return evaluateDo(listType);}
            default ->      {return getSymbolValue(symbol, listType.restOfList(), env);}
        }
    }

    private FunctionType declareFunction(ListType funcList, Environment env) {
        // TODO error if not list types

        ListType params = ((ListType)funcList.get(1));
        Type funcBody = funcList.get(2);

        return new FunctionType() {
            @Override
            public Type apply(ListType args) {
                return evaluate(funcBody, new Environment(env, params, args));
            }
        };
    }

    private Type evaluateDo(ListType listType) {
        return null;
    }

    private Type getSymbolValue(SymbolType symbol, ListType listType, Environment env) {
        Type envSymbol = env.get(symbol);
        if (envSymbol instanceof FunctionType func) {
            return evaluateFunction(func, listType, env);
        } else {
            return symbol;
        }
    }

    private Type evaluateFunction(FunctionType func, ListType args, Environment env) {
        List<Type> argsList = new ArrayList<>();
        for (int i = 0; i < args.size(); i++) {
            Type type = checkForSymbol(args.get(i), env);
            argsList.add(evaluate(type, env));
        }

        return func.apply(new ListType(argsList));
    }


    private Type evaluateIf(ListType listType, Environment env) {
        if (listType.size() != 3) {
            return new ErrorType("wrong");
        }

        Type firstArg = evaluate(listType.get(0), env);
        if (firstArg instanceof BooleanType condition) {
            if (condition.isTrue()) {
                return evaluate(listType.get(1), env);
            } else {
                return evaluate(listType.get(2), env);
            }
        } else {
            // TODO
            return new ErrorType("wrong");
        }
    }

    private Type declareVariable(ListType listType, Environment env) {
        if (listType.size() != 3) {
            // TODO
            return new ErrorType("wrong!");
        }
        Type type = evaluate(listType.get(2), env);
        SymbolType varName = (SymbolType) listType.get(1);
        env.set(varName, type);
        return type;
    }

    private Type checkForSymbol(Type type, Environment env) {
        if (type instanceof SymbolType symbol) {
            return env.get(symbol);
        }
        return type;
    }


}
