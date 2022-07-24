package com.curtcox.jaranalysis;

import javax.swing.*;
import java.awt.*;
import java.io.*;

final class AnalysisTableDemo {
    static final File input = new File("dots/JarAnalysis-0.0.1-SNAPSHOT.jar.dot");
    static void addNameTypes() {
        ClassNameType.add("StartsWith/Class");
    }
    static void addUses() {
        Uses.add(
                "Net/java.net",
                "SQL/java.sql",
                "File/java.io.File",
                "FileWrite/java.io.FileWrite",
                "Reflection/java.lang.reflect",
                "Dynamic/java.lang.invoke"
        );
    }

    public static void main(String[] args) throws Exception {
        addNameTypes();
        addUses();
        Analysis analysis = analyze(input);
        EventQueue.invokeLater(() -> show(analysis));
    }

    static void show(Analysis analysis) {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setVisible(true);
        JTable table = new JTable(new ClassDescriptionTableModel(analysis.classes.values()));
        frame.add(new JScrollPane(table));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static Analysis analyze(File input) throws Exception {
        Analysis analysis = new Analysis();
        analysis.scan(ClassDependency.read(input));
        return analysis;
    }

}
