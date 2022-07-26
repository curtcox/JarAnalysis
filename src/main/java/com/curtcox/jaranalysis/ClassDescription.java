package com.curtcox.jaranalysis;

import java.util.*;
import java.util.stream.*;

import static java.util.Arrays.*;

public final class ClassDescription {
    final Class c;
    final ClassNameType root;
    private final Set<ClassNameType> types = new HashSet<>();

    final Set<Class> allClassDependencies = new HashSet<>();
    final Set<Uses> uses = new HashSet<>();

    int partition;

    ClassDescription(Class fullName, ClassNameType root) {
        this.c = fullName;
        this.root = root;
        if (root!=null) {
            types.add(root);
        }
    }

    Set<Jar> allJarDependencies() {
        return allClassDependencies.stream().map(x -> x.jar).collect(Collectors.toSet());
    }

    void markAsUsedBy(ClassNameType type) {
        types.add(type);
    }
    static String csvHeader() { return csv(header()); }

    public static String[] header() {
        List<Object> all = new ArrayList<>();
        all.addAll(asList("jar","root", "partition"));
        all.addAll(asList(ClassNameType.values()));
        all.addAll(asList("fullName","packageName","shortName"));
        all.addAll(asList(Uses.values()));
        return all.stream().map(x->x.toString()).collect(Collectors.toList()).toArray(new String[0]);
    }

    public Object[] values() {
        List<Object> all = new ArrayList<>();
        all.addAll(asList(jar(),root(), partition));
        all.addAll(values(ClassNameType.values(),types));
        all.addAll(asList(c.fullName,c.packageName(),c.shortName()));
        all.addAll(values(Uses.values(),uses));
        return all.toArray(new Object[0]);
    }

    String toCSV() {
        if (types.isEmpty()) {
            System.out.println(c + " has no types.");
        }
        return csv(jar(),root(),partition,csv(ClassNameType.values(),types),c.fullName,c.packageName(),c.shortName(),csv(Uses.values(),uses));
    }

    Jar jar() { return c.jar; }
    String root() { return root == null ? "" : root.name; }

    @Override public String toString() { return c + " " + root + " " + types + " " + uses; }

    static String csv(Object... values) { return CSV.from(values); }

    static String csv(Object[] values,Set<?> set) { return CSV.from(values, set); }

    static List<Boolean> values(Object[] values,Set<?> set) {
        return stream(values).map(x -> set.contains(x)).collect(Collectors.toList());
    }

}
