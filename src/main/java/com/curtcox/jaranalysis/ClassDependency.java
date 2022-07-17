package com.curtcox.jaranalysis;

import java.io.*;
import java.util.*;

final class ClassDependency {

    final Class dependent;
    final Class dependency;

    private ClassDependency(Class dependent, Class dependency) {
        this.dependent = dependent;
        this.dependency = dependency;
    }

    static ClassDependency from(String line,String jar) {
        return new ClassDependency(findDependent(line,jar),findDependency(line,findJar(line)));
    }

    private static Class findDependency(String line,String jar) {
        String[] parts = line.split("->");
        return Class.forName(scrub(parts[1]).split(" ")[0],jar);
    }

    private static String findJar(String line) {
        String[] parts = line.split("->");
        String withParens = scrub(parts[1]).split(" ")[1];
        return withParens.substring(1,withParens.length()-1);
    }

    private static Class findDependent(String line,String jar) {
        String[] parts = line.split("->");
        Class c = Class.forName(scrub(parts[0]),jar);
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

    static List<ClassDependency> read(File input) throws IOException {
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


}
