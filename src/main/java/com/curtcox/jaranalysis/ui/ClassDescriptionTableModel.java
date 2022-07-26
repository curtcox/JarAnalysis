package com.curtcox.jaranalysis.ui;

import com.curtcox.jaranalysis.*;

import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;

final class ClassDescriptionTableModel implements TableModel {

    final List<ClassDescription> descriptions;

    public ClassDescriptionTableModel(Collection<ClassDescription> values) {
        descriptions = new ArrayList<>(values);
    }

    @Override public int getRowCount()    { return descriptions.size(); }
    @Override public int getColumnCount() { return ClassDescription.header().length; }
    @Override public String getColumnName(int columnIndex) {
        return ClassDescription.header()[columnIndex];
    }

    @Override
    public java.lang.Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return descriptions.get(rowIndex).values()[columnIndex];
    }

    // stuff we don't do

    @Override public void addTableModelListener(TableModelListener l) {}
    @Override public void removeTableModelListener(TableModelListener l) {}
    @Override public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
    @Override public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException();
    }

}
