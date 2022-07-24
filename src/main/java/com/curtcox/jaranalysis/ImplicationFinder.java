package com.curtcox.jaranalysis;

import java.util.*;

final class ImplicationFinder {

    @SafeVarargs
    static Set<Class> findAllClassesInDirection(Class key, ClassToClasses... directions) {
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

    private static Class take(Set<Class> toDo) {
        Iterator<Class> it = toDo.iterator();
        Class next = it.next();
        it.remove();
        return next;
    }

}
