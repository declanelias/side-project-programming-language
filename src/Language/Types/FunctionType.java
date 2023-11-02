package Language.Types;

import Language.Environment;

import java.util.List;
import java.util.function.Function;

public class FunctionType extends Type{

    private Type ast;
    private Environment environment;
    private ListType params;
    private Function<List<Type>, Type> function;

    public FunctionType(Function<List<Type>, Type> function){
        this.function = function;
    }


    public Type apply(List<Type> t) {
        return function.apply(t);
    }

    public FunctionType(Type ast, Environment environment, ListType params) {
        this.ast = ast;
        this.environment = environment;
        this.params = params;
    }

}
