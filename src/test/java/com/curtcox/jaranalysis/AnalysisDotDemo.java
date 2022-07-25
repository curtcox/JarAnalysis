package com.curtcox.jaranalysis;

import java.io.*;

final class AnalysisDotDemo {
    static final File input = new File("dots/JarAnalysis-0.0.1-SNAPSHOT.jar.dot");

    public static void main(String[] args) throws Exception {
        dump(analyze(input),System.out);
    }

    static void dump(Analysis analysis, PrintStream out) {
        Class c = Class.find(Analysis.class.getName());
        for (ClassDependency dep : analysis.dependencyTree(c)) {
            if (!dep.dependency.packageName().startsWith("java")) {
                out.println(quoted(dep.dependent) + "->" + quoted(dep.dependency));
            }
        }
    }

    static String quoted(Class c) {
        return "\"" + c.fullName + "\"";
    }

    static Analysis analyze(File input) throws Exception {
        Analysis analysis = new Analysis();
        analysis.scan(ClassDependency.read(input));
        return analysis;
    }

}
