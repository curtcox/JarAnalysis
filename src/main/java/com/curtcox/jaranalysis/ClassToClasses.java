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

    @SafeVarargs
    static Set<Class> findAllInDirection(Class key, ClassToClasses... directions) {
        Set<Class> all = new HashSet<>();
        Set<Class> toDo = new HashSet<>();
        Set<Class> done = new HashSet<>();
        toDo.add(key);
        while (!toDo.isEmpty()) {
            Class next = take(toDo);
            all.add(next);
            done.add(next);
            for (ClassToClasses direction : directions) {
                if (direction.containsKey(next)) {
                    for (Class dep : direction.get(next)) {
                        if (!done.contains(dep)) {
                            toDo.add(dep);
                        }
                    }
                }
            }
        }
        all.remove(key);
        return all;
    }

    private boolean containsKey(Class c) {
        return map.containsKey(c);
    }

    static Class take(Set<Class> toDo) {
        Iterator<Class> it = toDo.iterator();
        Class next = it.next();
        it.remove();
        return next;
    }

}
