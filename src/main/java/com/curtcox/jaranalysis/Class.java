package com.curtcox.jaranalysis;

import java.util.*;

final class Class implements Comparable<Class> {

    final String fullName;

    final String jar;

    private static Map<String,Class> classes = new HashMap<>();

    private Class(String fullName, String jar) {
        this.fullName = fullName;
        this.jar = jar;
    }

    static Class forName(String name, String jar) {
        if (classes.containsKey(name)) {
            Class existing = classes.get(name);
            if (existing.jar.equals(jar)) {
                return existing;
            }
            throw new IllegalArgumentException(name + " already exists in different jar.");
        }
        Class c = new Class(name,jar);
        classes.put(name,c);
        return c;
    }

    String shortName() {
        String[] parts = fullName.split("\\.");
        return parts[parts.length-1];
    }

    String packageName() { return fullName.substring(0,fullName.lastIndexOf(".")); }

    boolean implementsDependency(Class dependency) {
        return shortName().equals(dependency.shortName() + "Impl");
    }

    public boolean indicatesUsageOf(Uses uses) {
        return fullName.contains(uses.text);
    }

    @Override
    public String toString() { return fullName + " in " + jar; }

    @Override public boolean equals(Object o) {
        Class c = (Class) o;
        return fullName.equals(c.fullName);
    }

    @Override public int hashCode() {
        return fullName.hashCode();
    }

    @Override
    public int compareTo(Class c) {
        return fullName.compareTo(c.fullName);
    }
}
