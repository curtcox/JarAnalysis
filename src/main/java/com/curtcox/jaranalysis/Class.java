package com.curtcox.jaranalysis;

import java.util.*;

final class Class implements Comparable<Class> {

    final String fullName;

    final Jar jar;

    final Set<Class> directDependencies = new HashSet<>();

    final Set<Class> directDependents = new HashSet<>();

    private static Map<String,Class> classes = new HashMap<>();

    private Class(String fullName, Jar jar) {
        this.fullName = fullName;
        this.jar = jar;
    }

    static Class forName(String name, Jar jar) {
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

    static Class find(String name) {
        return classes.get(name);
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
        Class that = (Class) o;
        return fullName.equals(that.fullName);
    }

    @Override public int hashCode() {
        return fullName.hashCode();
    }

    @Override
    public int compareTo(Class c) {
        return fullName.compareTo(c.fullName);
    }
}
