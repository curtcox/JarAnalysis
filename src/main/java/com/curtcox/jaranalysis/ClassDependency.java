package com.curtcox.jaranalysis;

import java.io.*;
import java.util.*;

public final class ClassDependency {

    public final Class dependent;
    public final Class dependency;

    final boolean viaInterface;

    private ClassDependency(Class dependent, Class dependency, boolean viaInterface) {
        this.dependent = dependent;
        this.dependency = dependency;
        this.viaInterface = viaInterface;
    }

    static ClassDependency direct(Class dependent, Class dependency) {
        return new ClassDependency(dependent,dependency,false);
    }

    static ClassDependency from(String line,String jar) {
        Class dependent = findDependent(line,jar);
        Class dependency = findDependency(line,findJar(line));
        dependent.directDependencies.add(dependency);
        dependency.directDependents.add(dependent);
        return new ClassDependency(dependent,dependency,false);
    }

    private static Class findDependency(String line,String jar) {
        String[] parts = line.split("->");
        return Class.forName(scrub(parts[1]).split(" ")[0],Jar.forName(jar));
    }

    private static String findJar(String line) {
        String[] parts = line.split("->");
        String withParens = scrub(parts[1]).split(" ")[1];
        return withParens.substring(1,withParens.length()-1);
    }

    private static Class findDependent(String line,String jar) {
        String[] parts = line.split("->");
        Class c = Class.forName(scrub(parts[0]),Jar.forName(jar));
        return c;
    }

    private static String scrub(String s) {
        return s.replaceAll("\"","")
                .replaceAll(";","")
                .trim();
    }

    private static boolean isDependency(String line) {
        return line.contains("->");
    }
    private static boolean isDigraph(String line) {
        return line.startsWith("digraph");
    }

    // digraph "name" {
    private static String findDigraphName(String line) {
        return line.split("\"")[1];
    }

    public static List<ClassDependency> read(File input) throws IOException {
        List<ClassDependency> lines = new ArrayList<>();
        String jar = "?";
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            for (String line; (line = br.readLine()) != null;) {
                if (isDigraph(line)) {
                    jar = findDigraphName(line);
                }
                if (isDependency(line)) {
                    lines.add(from(line,jar));
                }
            }
        }
        return lines;
    }

    @Override public boolean equals(Object o) {
        ClassDependency that = (ClassDependency) o;
        return dependent.equals(that.dependent) && dependency.equals(that.dependency);
    }

    @Override public int hashCode() {
        return dependent.hashCode() ^ dependency.hashCode();
    }
}
