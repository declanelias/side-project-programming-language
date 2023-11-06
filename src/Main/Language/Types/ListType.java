package Main.Language.Types;

import java.util.List;
import java.util.Objects;

/**
 * Holds information about ListType in language
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListType listType = (ListType) o;
        return size == listType.size && Objects.equals(list, listType.list);
    }
}
