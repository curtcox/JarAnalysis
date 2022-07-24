package com.curtcox.jaranalysis;

import java.io.*;
import java.util.*;

final class AnalysisCsvDemo {
    static final File input = new File("dots/JarAnalysis-0.0.1-SNAPSHOT.jar.dot");
    static final File output = new File("dots/classes.csv");

    static void addNameTypes() {
        ClassNameType.add("StartsWith/Class");
    }
    static void addUses() {
        Uses.add(
                "RMI/java.rmi",
                "Net/java.net",
                "WebClient/org.springframework.web.client",
                "Struts/org.apache.struts",
                "Servlet/javax.servlet",
                "SQL/java.sql",
                "XSQL/javax.sql",
                "Persistence/javax.persistence",
                "File/java.io.File",
                "FileWrite/java.io.FileWrite",
                "Serializable/java.io.Serializable",
                "Spring/org.springframework",
                "Service/org.springframework.stereotype.Service",
                "Controller/org.springframework.stereotype.Controller",
                "JMS/javax.jms",
                "JNDI/javax.naming",
                "Reflection/java.lang.reflect",
                "Dynamic/java.lang.invoke"
        );
    }

    public static void main(String[] args) throws Exception {
        addNameTypes();
        addUses();
        Collection<ClassDescription> descriptions = analyze(input);
        writeClassDescriptionsReport(descriptions,new PrintStream(output));
    }

    static void writeClassDescriptionsReport(Collection<ClassDescription> descriptions, PrintStream out) {
        out.println(ClassDescription.csvHeader());
        for (ClassDescription description : descriptions) {
            out.println(description.toCSV());
        }
    }

    static Collection<ClassDescription> analyze(File input) throws Exception {
        Analysis analysis = new Analysis();
        analysis.scan(ClassDependency.read(input));
        return analysis.classes.values();
    }

}
