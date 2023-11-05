package Main.Language.Types;

import java.util.List;

public interface Lambda {
    public Type apply(List<Type> parameters);
}
