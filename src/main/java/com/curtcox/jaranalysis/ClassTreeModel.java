package com.curtcox.jaranalysis;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.*;
import java.util.stream.*;

final class ClassTreeModel implements TreeModel {

    final Class root;

    ClassTreeModel(Class root) {
        this.root = root;
    }

    @Override public Object    getRoot()              { return root; }
    @Override public int getChildCount(Object parent) { return children(parent).size(); }
    @Override public boolean    isLeaf(Object node)   { return children(node).isEmpty(); }

    @Override public Object     getChild(Object parent, int index)    { return children(parent).get(index); }
    @Override public int getIndexOfChild(Object parent, Object child) { return children(parent).indexOf(child); }

    @Override public void valueForPathChanged(TreePath path, Object newValue) {}
    @Override public void addTreeModelListener(TreeModelListener l) {}
    @Override public void removeTreeModelListener(TreeModelListener l) {}

    private static List<Class> children(Object o) {
        Class c = (Class) o;
        return c.directDependencies.stream()
                .filter(x->!x.packageName().startsWith("java"))
                .collect(Collectors.toList());
    }
}
