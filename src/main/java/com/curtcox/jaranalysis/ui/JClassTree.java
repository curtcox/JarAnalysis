package com.curtcox.jaranalysis.ui;

import com.curtcox.jaranalysis.Class;

import javax.swing.*;

public final class JClassTree extends JTree {

    public JClassTree(Class c) {
        super(new ClassTreeModel(c));
        setCellRenderer(new ClassTreeRenderer());
    }
}
