package com.curtcox.jaranalysis;

import javax.swing.*;
import java.awt.*;
import java.io.*;

final class AnalysisTreeDemo {
    static final File input = new File("dots/JarAnalysis-0.0.1-SNAPSHOT.jar.dot");

    public static void main(String[] args) throws Exception {
        analyze(input);
        EventQueue.invokeLater(() -> show());
    }

    static void show() {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setVisible(true);
        JTree tree = new JTree(new ClassTreeModel(Class.find(Analysis.class.getName())));
        frame.add(new JScrollPane(tree));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    static Analysis analyze(File input) throws Exception {
        Analysis analysis = new Analysis();
        analysis.scan(ClassDependency.read(input));
        return analysis;
    }

}
