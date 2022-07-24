package com.curtcox.jaranalysis;

import java.util.*;
import java.util.stream.*;

final class Analysis {

    ClassToClasses classToDependencies = new ClassToClasses(); // class -> classes it imports (direct only)
    ClassToClasses classToDependents = new ClassToClasses(); // class -> classes that import it (directly depend on it)
    ClassToClasses interfaceToImplementations = new ClassToClasses(); // class -> classes that implement it
    ClassToClasses dependents = new ClassToClasses(); // class -> classes that depend on it -- including indirectly
    Map<Uses, Set<Class>> groupDependents = new HashMap<>(); // Uses -> classes that depend on it -- including indirectly
    Map<Class, ClassDescription> classes = new HashMap<>(); // class -> class description

    void scan(List<ClassDependency> lines) {
        addDirectDependents(lines);
        recordInitialDescriptions();
        findAllDependents();
        findGroupDependents();
        updateTypes();
        updateUses();
        updatePartitions(partition());
    }

    void recordInitialDescriptions() {
        Set<ClassNameType> roots = new HashSet<>();
        for (Class c : classToDependencies.keys()) {
            ClassDescription description = initialDescription(c);
            classes.put(c, description);
            ClassNameType root = description.root;
            if (root != null) {
                roots.add(root);
            }
        }
        for (ClassNameType t : ClassNameType.values()) {
            if (!roots.contains(t)) {
                throw new IllegalArgumentException("No " + t);
            }
        }
    }

    ClassDescription initialDescription(Class c) {
        return new ClassDescription(c, ClassNameType.from(c));
    }

    void updateUses() {
        for (Class c : allClassesThatNeedSomething()) {
            classes.get(c).uses.addAll(determineUses(c));
        }
    }

    void updateTypes() {
        for (Class c : allClassesThatNeedSomething()) {
            ClassDescription description = classes.get(c);
            ClassNameType type = description.root;
            description.allClassDependencies.addAll(findAllDependencies(c));
            for (Class dep : description.allClassDependencies) {
                if (classes.containsKey(dep)) {
                    if (description.root != null) {
                        classes.get(dep).markAsUsedBy(type);
                    }
                }
            }
        }
    }

    Set<Uses> determineUses(Class c) {
        Set<Uses> all = new HashSet<>();
        for (Uses t : Uses.values()) {
            if (uses(c, t)) {
                all.add(t);
            }
        }
        return all;
    }

    boolean uses(Class c, Uses t) {
        return groupDependents.get(t).contains(c);
    }

    void findGroupDependents() {
        for (Uses t : Uses.values()) {
            groupDependents.put(t, whatDependsOn(classesThatContain(t)));
        }
    }

    Set<Class> allClassesThatNeedSomething() {
        return classes.keySet();
    }

    Set<Class> allClassesThatSomethingNeeds() {
        return classToDependents.keys();
    }

    Set<Class> whatDependsOn(Set<Class> classes) {
        Set<Class> all = new HashSet<>();
        for (Class c : classes) {
            all.addAll(dependents.get(c));
        }
        return all;
    }

    Set<Class> classesThatContain(Uses uses) {
        return allClassesThatSomethingNeeds().stream().filter((x) -> x.indicatesUsageOf(uses)).collect(Collectors.toSet());
    }

    void findAllDependents() {
        for (Class key : allClassesThatSomethingNeeds()) {
            dependents.put(key, findAllDependents(key));
        }
    }

    Set<Class> findAllDependents(Class c) {
        return ImplicationFinder.findAllClassesInDirection(c, classToDependents);
    }

    Set<Class> findAllDependencies(Class c) {
        return ImplicationFinder.findAllClassesInDirection(c, classToDependencies, interfaceToImplementations);
    }

    void addDirectDependents(List<ClassDependency> deps) {
        for (ClassDependency dep : deps) {
            Class dependent = dep.dependent;
            Class dependency = dep.dependency;
            classToDependencies.add(dependent, dependency);
            classToDependents.add(dependency, dependent);
            if (dependent.implementsDependency(dependency)) {
                interfaceToImplementations.add(dependency, dependent);
            }
        }
    }


    Partitioning partition() {
        Partitioning partitioning = new Partitioning();
        Set<Class> all = allClassesThatNeedSomething();
        for (Class c : all) {
            for (Class d : findAllDependents(c)) {
                if (all.contains(d)) {
                    partitioning.ensureInSamePartition(c,d);
                }
            }
            for (Class d : findAllDependencies(c)) {
                if (all.contains(d)) {
                    partitioning.ensureInSamePartition(c,d);
                }
            }
        }
        return partitioning;
    }

    void updatePartitions(Partitioning partitioning) {
        for (Class c : allClassesThatNeedSomething()) {
            classes.get(c).partition = partitioning.partitionNumberFor(c);
        }
    }

}
