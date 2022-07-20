package com.curtcox.jaranalysis;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

final class ClassDescription {
    final Class c;
    final ClassNameType root;
    private final Set<ClassNameType> types = new HashSet<>();

    final Set<Class> allDependencies = new HashSet<>();
    final Set<Uses> uses = new HashSet<>();

    int partition;

    ClassDescription(Class fullName, ClassNameType root) {
        this.c = fullName;
        this.root = root;
        if (root!=null) {
            types.add(root);
        }
    }

    void markAsUsedBy(ClassNameType type) {
        types.add(type);
    }
    static String csvHeader() {
        return csv("jar","root", "partition", csv(ClassNameType.values()),"fullName","packageName","shortName", csv(Uses.values()));
    }

    public String toCSV() {
        if (types.isEmpty()) {
            System.out.println(c + " has no types.");
        }
        return csv(jar(),root(),partition,csv(ClassNameType.values(),types),c,c.packageName(),c.shortName(),csv(Uses.values(),uses));
    }

    String jar() { return c.jar == null ? "" : c.jar; }
    String root() { return root == null ? "" : root.name; }

    @Override public String toString() { return c + " " + root + " " + types + " " + uses; }

    static String csv(Object... values) { return csv(values, Object::toString); }

    static String csv(Object[] values,Set<?> set) { return csv(values, (v) -> set.contains(v) ? v.toString() : ""); }

    static String csv(Object[] values, Function<Object,String> mapper) {
        return Arrays.stream(values).map(mapper).collect(Collectors.joining(","));
    }

}
