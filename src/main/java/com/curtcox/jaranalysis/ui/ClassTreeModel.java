package com.curtcox.jaranalysis.ui;

import com.curtcox.jaranalysis.Class;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.*;
import java.util.stream.*;

final class ClassTreeModel implements TreeModel {

    final List<Class> root;

    ClassTreeModel(Class root) {
        this.root = Arrays.asList(root);
    }

    @Override public Object    getRoot()              { return root; }
    @Override public int getChildCount(Object parent) { return children(parent).size(); }
    @Override public boolean    isLeaf(Object node)   { return children(node).isEmpty(); }

    @Override public Object     getChild(Object parent, int index)    { return children(parent).get(index); }
    @Override public int getIndexOfChild(Object parent, Object child) { return children(parent).indexOf(child); }

    @Override public void valueForPathChanged(TreePath path, Object newValue) {}
    @Override public void addTreeModelListener(TreeModelListener l) {}
    @Override public void removeTreeModelListener(TreeModelListener l) {}

    private static List<List<Class>> children(Object o) {
        List<Class> head = (List<Class>) o;
        Class last = head.get(head.size()-1);
        List<Class> tails = last.directDependencies.stream()
                .filter(x->!head.contains(x))
                .filter(x->!x.packageName().startsWith("java"))
                .collect(Collectors.toList());
        return combine(head,tails);
    }

    private static List<List<Class>> combine(List<Class> head,List<Class> tails) {
        return tails.stream().map(x -> append(head,x)).collect(Collectors.toList());
    }

    private static List<Class> append(List<Class> head, Class tail) {
        List<Class> all = new ArrayList<>(head);
        all.add(tail);
        return all;
    }
}
