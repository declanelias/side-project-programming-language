package Language.Types;

import java.util.Collections;
import java.util.List;

public class ListType extends Type {

    private final List<Type> list;
    private final int size;

    public ListType(List<Type> list) {
        this.list = list;
        this.size = list.size();
    }

    public Type get(int n) {
        return list.get(n);
    }

    public int size() {
        return size;
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (Type type : list) {
            sb.append(type.toString());
        }


        return "ListType{" +
                "\t" + sb +
                '}';
    }
}
