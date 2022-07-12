package com.curtcox.jaranalysis;

import java.io.*;
import java.util.*;

final class ClassDependency {

    final Class dependent;
    final Class dependency;

    final String jar;

    private ClassDependency(Class dependent, Class dependency, String jar) {
        this.dependent = dependent;
        this.dependency = dependency;
        this.jar = jar;
    }

    static ClassDependency from(String line) {
        return new ClassDependency(findDependent(line),findDependency(line),findJar(line));
    }

    private static Class findDependency(String line) {
        String[] parts = line.split("->");
        return Class.forName(scrub(parts[1]).split(" ")[0]);
    }

    private static String findJar(String line) {
        String[] parts = line.split("->");
        String withParens = scrub(parts[1]).split(" ")[1];
        return withParens.substring(1,withParens.length()-1);
    }

    private static Class findDependent(String line) {
        String[] parts = line.split("->");
        Class c = Class.forName(scrub(parts[0]));
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

    static List<ClassDependency> read(File input) throws IOException {
        List<ClassDependency> lines = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(input))) {
            for (String line; (line = br.readLine()) != null;) {
                if (isDependency(line)) {
                    lines.add(from(line));
                }
            }
        }
        return lines;
    }

}
