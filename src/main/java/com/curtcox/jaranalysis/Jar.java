package com.curtcox.jaranalysis;

import java.util.*;

final class Jar implements Comparable<Jar> {

    final String name;

    private static Map<String, Jar> jars = new TreeMap<>();

    private Jar(String name) {
        this.name = name;
    }

    static Jar forName(String name) {
        if (jars.containsKey(name)) {
            return jars.get(name);
        }
        Jar c = new Jar(name);
        jars.put(name,c);
        return c;
    }

    static Jar[] values() {
        return jars.values().toArray(new Jar[0]);
    }

    @Override
    public String toString() { return name; }

    @Override public boolean equals(Object o) {
        Jar c = (Jar) o;
        return name.equals(c.name);
    }

    @Override public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Jar c) {
        return name.compareTo(c.name);
    }
}
