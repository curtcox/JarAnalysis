package com.curtcox.jaranalysis.ui;

import com.curtcox.jaranalysis.Class;
import com.curtcox.jaranalysis.*;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.util.List;
import java.util.*;

final class ClassTreeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        List<Class> list = (List<Class>) value;
        Class c = list.get(list.size()-1);
        setText( dependencyCount(list,c) + " " + c.fullName);
        return this;
    }

    int dependencyCount(List<Class> list, Class c) {
        Set<Class> classes = new HashSet<>();
        for (ClassDependency dependency : ImplicationFinder.findAllDependencies(c)) {
            classes.add(dependency.dependency);
            classes.add(dependency.dependent);
        }
        classes.removeAll(list);
        return classes.size();
    }
}
