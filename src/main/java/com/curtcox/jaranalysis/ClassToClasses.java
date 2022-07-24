package com.curtcox.jaranalysis;

import java.util.*;

final class ClassToClasses {

    Map<Class, Set<Class>> map = new HashMap<>();

    Set<Class> keys() { return map.keySet(); }

    Set<Class> get(Class c) { return map.get(c); }

    void put(Class c, Set<Class> classes) { map.put(c,classes); }

    void add(Class key, Class value) {
        Set<Class> set = map.containsKey(key) ? map.get(key) : new HashSet<>();
        set.add(value);
        map.put(key,set);
    }

    boolean containsKey(Class c) {
        return map.containsKey(c);
    }

}
