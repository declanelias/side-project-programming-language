package Main.Language.Types;

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

    public ListType restOfList() {
        return new ListType(list.subList(1, size));
    }


    @Override
    public String toString() {
        return list.toString();
    }
}
