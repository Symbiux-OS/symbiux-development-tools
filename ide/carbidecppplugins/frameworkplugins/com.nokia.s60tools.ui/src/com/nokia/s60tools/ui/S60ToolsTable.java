/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description:
*
*/
 
 
package com.nokia.s60tools.ui;

import java.security.InvalidParameterException;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.nokia.s60tools.ui.internal.Messages;

/**
 * Because <code>org.eclipse.swt.widgets.Table</code> class cannot be subclassed, it is wrapped 
 * inside this class. This class implements the all the public methods 
 * for the wrapped class, and delegates them further.
 * 
 * This class adds the following functionalities to the original
 * <code>Table</code> class:<br>
 * - <code>S60ToolsTable</code> can be hosted by a <code>TableViewer</code>
 *   which can be set after the instantion of this class. Therefore it is possible
 *   to get reference to hosting viewer, if such is set, and query for the
 *   properties of the viewer. For example, set properties of the current
 *   sorter instance. <code>S60ToolsTable</code> is designed to be used
 *   with <code>S60ToolsViewerSorter</code> class, and instances of this
 *   class can be created with <code>S60ToolsTableFactory.create</code>
 *   method that used information stored in <code>S60ToolsTableColumnData</code>
 *   instances.
 * @see org.eclipse.swt.widgets.Table
 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
 * @see com.nokia.s60tools.ui.S60ToolsTableFactory#create
 * @see com.nokia.s60tools.ui.S60ToolsTableColumnData
 */
public class S60ToolsTable{

	/**
	 * Table viewer instance that owns
	 * this table.
	 */
	private TableViewer hostingViewer = null;
	
	/**
	 * Wrapped Table instance.
	 */
	private Table tableInstance = null;

	public static final String TXT_ITEMS_IN_COLUMN_HEADER = " " + Messages.getString("S60ToolsTable.Items_Str"); //$NON-NLS-1$ //$NON-NLS-2$
	private final String columnDataHeaderName;
	private final int showItemCountInColumn;
	private static final int COLUMN_DATA_COUNT_NOT_SET = -1;
	
	/**
	 * Constructor.
	 * @param parent Parent composite.
	 * @param style Style bits used for wrapped Table instance.
	 */
	public S60ToolsTable(Composite parent, int style) {
		tableInstance = new Table(parent, style);
		columnDataHeaderName = null;
		showItemCountInColumn = COLUMN_DATA_COUNT_NOT_SET;
	}

	/**
	 * Constructor.
	 * @param parent Parent composite.
	 * @param style Style bits used for wrapped Table instance.
	 * @param columnDataHeaderName Column name to be updated when 
	 * 			refreshing refreshHostingViewer(int columnDateHeaderCount) 
	 * @param showItemCountInColumn Column index used for showing item count. 
	 * 			Must reference to columnDataArr[<code>showItemCountInColumn</code>>].

	 * @param showItemCountInColumn
	 */
	
	public S60ToolsTable(Composite parent, int style, 
			String columnDataHeaderName, int showItemCountInColumn) {
		this.columnDataHeaderName = columnDataHeaderName;
		this.showItemCountInColumn = showItemCountInColumn;
		tableInstance = new Table(parent, style);
	}	

	/**
	 * @return Returns the hostingViewer.
	 */
	public TableViewer getHostingViewer() {
		return hostingViewer;
	}

	/**
	 * Sets the hosting viewer for the table instance.
	 * @param hostingViewer The hostingViewer to set.
	 */
	public void setHostingViewer(TableViewer hostingViewer) {
		if((getStyle() & SWT.CHECK) != 0){
			// Table with check support must be hosted with corresponding viewer
			if(! (hostingViewer instanceof CheckboxTableViewer)){
				throw new InvalidParameterException(Messages.getString("S60ToolsTable.TableMustBeViewerInstance_Exception")); //$NON-NLS-1$
			}
		}
		this.hostingViewer = hostingViewer;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setHeaderVisible(boolean)
	 */
	public void setHeaderVisible(boolean show) {
		tableInstance.setHeaderVisible(show);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setLayoutData(java.lang.Object)
	 */
	public void setLayoutData(Object layoutData) {
		tableInstance.setLayoutData(layoutData);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setLinesVisible(boolean)
	 */
	public void setLinesVisible(boolean show) {
		tableInstance.setLinesVisible(show);
	}

	/**
	 * @return Returns the tableInstance.
	 */
	public Table getTableInstance() {
		return tableInstance;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addControlListener(org.eclipse.swt.events.ControlListener)
	 */
	public void addControlListener(ControlListener listener) {
		tableInstance.addControlListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#addDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public void addDisposeListener(DisposeListener listener) {
		tableInstance.addDisposeListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addFocusListener(org.eclipse.swt.events.FocusListener)
	 */
	public void addFocusListener(FocusListener listener) {
		tableInstance.addFocusListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addHelpListener(org.eclipse.swt.events.HelpListener)
	 */
	public void addHelpListener(HelpListener listener) {
		tableInstance.addHelpListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addKeyListener(org.eclipse.swt.events.KeyListener)
	 */
	public void addKeyListener(KeyListener listener) {
		tableInstance.addKeyListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#addListener(int, org.eclipse.swt.widgets.Listener)
	 */
	public void addListener(int eventType, Listener listener) {
		tableInstance.addListener(eventType, listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addMouseListener(org.eclipse.swt.events.MouseListener)
	 */
	public void addMouseListener(MouseListener listener) {
		tableInstance.addMouseListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addMouseMoveListener(org.eclipse.swt.events.MouseMoveListener)
	 */
	public void addMouseMoveListener(MouseMoveListener listener) {
		tableInstance.addMouseMoveListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addMouseTrackListener(org.eclipse.swt.events.MouseTrackListener)
	 */
	public void addMouseTrackListener(MouseTrackListener listener) {
		tableInstance.addMouseTrackListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addPaintListener(org.eclipse.swt.events.PaintListener)
	 */
	public void addPaintListener(PaintListener listener) {
		tableInstance.addPaintListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#addSelectionListener(org.eclipse.swt.events.SelectionListener)
	 */
	public void addSelectionListener(SelectionListener listener) {
		tableInstance.addSelectionListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#addTraverseListener(org.eclipse.swt.events.TraverseListener)
	 */
	public void addTraverseListener(TraverseListener listener) {
		tableInstance.addTraverseListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#changed(org.eclipse.swt.widgets.Control[])
	 */
	public void changed(Control[] changed) {
		tableInstance.changed(changed);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#clear(int, int)
	 */
	public void clear(int start, int end) {
		tableInstance.clear(start, end);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#clear(int)
	 */
	public void clear(int index) {
		tableInstance.clear(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#clear(int[])
	 */
	public void clear(int[] indices) {
		tableInstance.clear(indices);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#clearAll()
	 */
	public void clearAll() {
		tableInstance.clearAll();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#computeSize(int, int, boolean)
	 */
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return tableInstance.computeSize(wHint, hHint, changed);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#computeSize(int, int)
	 */
	public Point computeSize(int wHint, int hHint) {
		return tableInstance.computeSize(wHint, hHint);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Scrollable#computeTrim(int, int, int, int)
	 */
	public Rectangle computeTrim(int x, int y, int width, int height) {
		return tableInstance.computeTrim(x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#deselect(int, int)
	 */
	public void deselect(int start, int end) {
		tableInstance.deselect(start, end);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#deselect(int)
	 */
	public void deselect(int index) {
		tableInstance.deselect(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#deselect(int[])
	 */
	public void deselect(int[] indices) {
		tableInstance.deselect(indices);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#deselectAll()
	 */
	public void deselectAll() {
		tableInstance.deselectAll();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	public void dispose() {
		tableInstance.dispose();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0) {
		return tableInstance.equals(arg0);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#forceFocus()
	 */
	public boolean forceFocus() {
		return tableInstance.forceFocus();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getAccessible()
	 */
	public Accessible getAccessible() {
		return tableInstance.getAccessible();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getBackground()
	 */
	public Color getBackground() {
		return tableInstance.getBackground();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getBorderWidth()
	 */
	public int getBorderWidth() {
		return tableInstance.getBorderWidth();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getBounds()
	 */
	public Rectangle getBounds() {
		return tableInstance.getBounds();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#getChildren()
	 */
	public Control[] getChildren() {
		return tableInstance.getChildren();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Scrollable#getClientArea()
	 */
	public Rectangle getClientArea() {
		return tableInstance.getClientArea();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getColumn(int)
	 */
	public TableColumn getColumn(int index) {
		return tableInstance.getColumn(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getColumnCount()
	 */
	public int getColumnCount() {
		return tableInstance.getColumnCount();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getColumnOrder()
	 */
	public int[] getColumnOrder() {
		return tableInstance.getColumnOrder();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getColumns()
	 */
	public TableColumn[] getColumns() {
		return tableInstance.getColumns();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#getData()
	 */
	public Object getData() {
		return tableInstance.getData();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#getData(java.lang.String)
	 */
	public Object getData(String key) {
		return tableInstance.getData(key);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#getDisplay()
	 */
	public Display getDisplay() {
		return tableInstance.getDisplay();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getEnabled()
	 */
	public boolean getEnabled() {
		return tableInstance.getEnabled();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getFont()
	 */
	public Font getFont() {
		return tableInstance.getFont();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getForeground()
	 */
	public Color getForeground() {
		return tableInstance.getForeground();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getGridLineWidth()
	 */
	public int getGridLineWidth() {
		return tableInstance.getGridLineWidth();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getHeaderHeight()
	 */
	public int getHeaderHeight() {
		return tableInstance.getHeaderHeight();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getHeaderVisible()
	 */
	public boolean getHeaderVisible() {
		return tableInstance.getHeaderVisible();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Scrollable#getHorizontalBar()
	 */
	public ScrollBar getHorizontalBar() {
		return tableInstance.getHorizontalBar();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getItem(int)
	 */
	public TableItem getItem(int index) {
		return tableInstance.getItem(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getItem(org.eclipse.swt.graphics.Point)
	 */
	public TableItem getItem(Point point) {
		return tableInstance.getItem(point);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getItemCount()
	 */
	public int getItemCount() {
		return tableInstance.getItemCount();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getItemHeight()
	 */
	public int getItemHeight() {
		return tableInstance.getItemHeight();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getItems()
	 */
	public TableItem[] getItems() {
		return tableInstance.getItems();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#getLayout()
	 */
	public Layout getLayout() {
		return tableInstance.getLayout();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getLayoutData()
	 */
	public Object getLayoutData() {
		return tableInstance.getLayoutData();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#getLayoutDeferred()
	 */
	public boolean getLayoutDeferred() {
		return tableInstance.getLayoutDeferred();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getLinesVisible()
	 */
	public boolean getLinesVisible() {
		return tableInstance.getLinesVisible();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getLocation()
	 */
	public Point getLocation() {
		return tableInstance.getLocation();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getMenu()
	 */
	public Menu getMenu() {
		return tableInstance.getMenu();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getMonitor()
	 */
	public Monitor getMonitor() {
		return tableInstance.getMonitor();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getParent()
	 */
	public Composite getParent() {
		return tableInstance.getParent();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getSelection()
	 */
	public TableItem[] getSelection() {
		return tableInstance.getSelection();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getSelectionCount()
	 */
	public int getSelectionCount() {
		return tableInstance.getSelectionCount();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getSelectionIndex()
	 */
	public int getSelectionIndex() {
		return tableInstance.getSelectionIndex();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getSelectionIndices()
	 */
	public int[] getSelectionIndices() {
		return tableInstance.getSelectionIndices();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getShell()
	 */
	public Shell getShell() {
		return tableInstance.getShell();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getSize()
	 */
	public Point getSize() {
		return tableInstance.getSize();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#getStyle()
	 */
	public int getStyle() {
		return tableInstance.getStyle();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#getTabList()
	 */
	public Control[] getTabList() {
		return tableInstance.getTabList();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getToolTipText()
	 */
	public String getToolTipText() {
		return tableInstance.getToolTipText();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#getTopIndex()
	 */
	public int getTopIndex() {
		return tableInstance.getTopIndex();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Scrollable#getVerticalBar()
	 */
	public ScrollBar getVerticalBar() {
		return tableInstance.getVerticalBar();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#getVisible()
	 */
	public boolean getVisible() {
		return tableInstance.getVisible();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return tableInstance.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#indexOf(org.eclipse.swt.widgets.TableColumn)
	 */
	public int indexOf(TableColumn column) {
		return tableInstance.indexOf(column);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#indexOf(org.eclipse.swt.widgets.TableItem)
	 */
	public int indexOf(TableItem item) {
		return tableInstance.indexOf(item);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#internal_dispose_GC(int, org.eclipse.swt.graphics.GCData)
	 */
	public void internal_dispose_GC(int hDC, GCData data) {
		tableInstance.internal_dispose_GC(hDC, data);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#internal_new_GC(org.eclipse.swt.graphics.GCData)
	 */
	public int internal_new_GC(GCData data) {
		return tableInstance.internal_new_GC(data);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#isDisposed()
	 */
	public boolean isDisposed() {
		return tableInstance.isDisposed();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#isEnabled()
	 */
	public boolean isEnabled() {
		return tableInstance.isEnabled();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#isFocusControl()
	 */
	public boolean isFocusControl() {
		return tableInstance.isFocusControl();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#isLayoutDeferred()
	 */
	public boolean isLayoutDeferred() {
		return tableInstance.isLayoutDeferred();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#isListening(int)
	 */
	public boolean isListening(int eventType) {
		return tableInstance.isListening(eventType);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#isReparentable()
	 */
	public boolean isReparentable() {
		return tableInstance.isReparentable();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#isSelected(int)
	 */
	public boolean isSelected(int index) {
		return tableInstance.isSelected(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#isVisible()
	 */
	public boolean isVisible() {
		return tableInstance.isVisible();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#layout()
	 */
	public void layout() {
		tableInstance.layout();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#layout(boolean, boolean)
	 */
	public void layout(boolean changed, boolean all) {
		tableInstance.layout(changed, all);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#layout(boolean)
	 */
	public void layout(boolean changed) {
		tableInstance.layout(changed);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#layout(org.eclipse.swt.widgets.Control[])
	 */
	public void layout(Control[] changed) {
		tableInstance.layout(changed);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#moveAbove(org.eclipse.swt.widgets.Control)
	 */
	public void moveAbove(Control control) {
		tableInstance.moveAbove(control);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#moveBelow(org.eclipse.swt.widgets.Control)
	 */
	public void moveBelow(Control control) {
		tableInstance.moveBelow(control);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#notifyListeners(int, org.eclipse.swt.widgets.Event)
	 */
	public void notifyListeners(int eventType, Event event) {
		tableInstance.notifyListeners(eventType, event);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#pack()
	 */
	public void pack() {
		tableInstance.pack();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#pack(boolean)
	 */
	public void pack(boolean changed) {
		tableInstance.pack(changed);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#redraw()
	 */
	public void redraw() {
		tableInstance.redraw();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#redraw(int, int, int, int, boolean)
	 */
	public void redraw(int x, int y, int width, int height, boolean all) {
		tableInstance.redraw(x, y, width, height, all);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#remove(int, int)
	 */
	public void remove(int start, int end) {
		tableInstance.remove(start, end);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#remove(int)
	 */
	public void remove(int index) {
		tableInstance.remove(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#remove(int[])
	 */
	public void remove(int[] indices) {
		tableInstance.remove(indices);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#removeAll()
	 */
	public void removeAll() {
		tableInstance.removeAll();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeControlListener(org.eclipse.swt.events.ControlListener)
	 */
	public void removeControlListener(ControlListener listener) {
		tableInstance.removeControlListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#removeDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public void removeDisposeListener(DisposeListener listener) {
		tableInstance.removeDisposeListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeFocusListener(org.eclipse.swt.events.FocusListener)
	 */
	public void removeFocusListener(FocusListener listener) {
		tableInstance.removeFocusListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeHelpListener(org.eclipse.swt.events.HelpListener)
	 */
	public void removeHelpListener(HelpListener listener) {
		tableInstance.removeHelpListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeKeyListener(org.eclipse.swt.events.KeyListener)
	 */
	public void removeKeyListener(KeyListener listener) {
		tableInstance.removeKeyListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#removeListener(int, org.eclipse.swt.widgets.Listener)
	 */
	public void removeListener(int eventType, Listener listener) {
		tableInstance.removeListener(eventType, listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeMouseListener(org.eclipse.swt.events.MouseListener)
	 */
	public void removeMouseListener(MouseListener listener) {
		tableInstance.removeMouseListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeMouseMoveListener(org.eclipse.swt.events.MouseMoveListener)
	 */
	public void removeMouseMoveListener(MouseMoveListener listener) {
		tableInstance.removeMouseMoveListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeMouseTrackListener(org.eclipse.swt.events.MouseTrackListener)
	 */
	public void removeMouseTrackListener(MouseTrackListener listener) {
		tableInstance.removeMouseTrackListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removePaintListener(org.eclipse.swt.events.PaintListener)
	 */
	public void removePaintListener(PaintListener listener) {
		tableInstance.removePaintListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#removeSelectionListener(org.eclipse.swt.events.SelectionListener)
	 */
	public void removeSelectionListener(SelectionListener listener) {
		tableInstance.removeSelectionListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#removeTraverseListener(org.eclipse.swt.events.TraverseListener)
	 */
	public void removeTraverseListener(TraverseListener listener) {
		tableInstance.removeTraverseListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#select(int, int)
	 */
	public void select(int start, int end) {
		tableInstance.select(start, end);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#select(int)
	 */
	public void select(int index) {
		tableInstance.select(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#select(int[])
	 */
	public void select(int[] indices) {
		tableInstance.select(indices);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#selectAll()
	 */
	public void selectAll() {
		tableInstance.selectAll();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color)
	 */
	public void setBackground(Color color) {
		tableInstance.setBackground(color);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setBounds(int, int, int, int)
	 */
	public void setBounds(int x, int y, int width, int height) {
		tableInstance.setBounds(x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setBounds(org.eclipse.swt.graphics.Rectangle)
	 */
	public void setBounds(Rectangle rect) {
		tableInstance.setBounds(rect);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setCapture(boolean)
	 */
	public void setCapture(boolean capture) {
		tableInstance.setCapture(capture);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setColumnOrder(int[])
	 */
	public void setColumnOrder(int[] order) {
		tableInstance.setColumnOrder(order);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setCursor(org.eclipse.swt.graphics.Cursor)
	 */
	public void setCursor(Cursor cursor) {
		tableInstance.setCursor(cursor);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#setData(java.lang.Object)
	 */
	public void setData(Object data) {
		tableInstance.setData(data);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#setData(java.lang.String, java.lang.Object)
	 */
	public void setData(String key, Object value) {
		tableInstance.setData(key, value);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		tableInstance.setEnabled(enabled);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#setFocus()
	 */
	public boolean setFocus() {
		return tableInstance.setFocus();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setFont(org.eclipse.swt.graphics.Font)
	 */
	public void setFont(Font font) {
		tableInstance.setFont(font);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setForeground(org.eclipse.swt.graphics.Color)
	 */
	public void setForeground(Color color) {
		tableInstance.setForeground(color);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setItemCount(int)
	 */
	public void setItemCount(int count) {
		tableInstance.setItemCount(count);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#setLayout(org.eclipse.swt.widgets.Layout)
	 */
	public void setLayout(Layout layout) {
		tableInstance.setLayout(layout);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#setLayoutDeferred(boolean)
	 */
	public void setLayoutDeferred(boolean defer) {
		tableInstance.setLayoutDeferred(defer);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setLocation(int, int)
	 */
	public void setLocation(int x, int y) {
		tableInstance.setLocation(x, y);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setLocation(org.eclipse.swt.graphics.Point)
	 */
	public void setLocation(Point location) {
		tableInstance.setLocation(location);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setMenu(org.eclipse.swt.widgets.Menu)
	 */
	public void setMenu(Menu menu) {
		tableInstance.setMenu(menu);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setParent(org.eclipse.swt.widgets.Composite)
	 */
	public boolean setParent(Composite parent) {
		return tableInstance.setParent(parent);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setRedraw(boolean)
	 */
	public void setRedraw(boolean redraw) {
		tableInstance.setRedraw(redraw);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setSelection(int, int)
	 */
	public void setSelection(int start, int end) {
		tableInstance.setSelection(start, end);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setSelection(int)
	 */
	public void setSelection(int index) {
		tableInstance.setSelection(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setSelection(int[])
	 */
	public void setSelection(int[] indices) {
		tableInstance.setSelection(indices);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setSelection(org.eclipse.swt.widgets.TableItem[])
	 */
	public void setSelection(TableItem[] items) {
		tableInstance.setSelection(items);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setSize(int, int)
	 */
	public void setSize(int width, int height) {
		tableInstance.setSize(width, height);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setSize(org.eclipse.swt.graphics.Point)
	 */
	public void setSize(Point size) {
		tableInstance.setSize(size);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#setTabList(org.eclipse.swt.widgets.Control[])
	 */
	public void setTabList(Control[] tabList) {
		tableInstance.setTabList(tabList);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setToolTipText(java.lang.String)
	 */
	public void setToolTipText(String string) {
		tableInstance.setToolTipText(string);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#setTopIndex(int)
	 */
	public void setTopIndex(int index) {
		tableInstance.setTopIndex(index);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		tableInstance.setVisible(visible);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#showColumn(org.eclipse.swt.widgets.TableColumn)
	 */
	public void showColumn(TableColumn column) {
		tableInstance.showColumn(column);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#showItem(org.eclipse.swt.widgets.TableItem)
	 */
	public void showItem(TableItem item) {
		tableInstance.showItem(item);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Table#showSelection()
	 */
	public void showSelection() {
		tableInstance.showSelection();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#toControl(int, int)
	 */
	public Point toControl(int x, int y) {
		return tableInstance.toControl(x, y);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#toControl(org.eclipse.swt.graphics.Point)
	 */
	public Point toControl(Point point) {
		return tableInstance.toControl(point);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#toDisplay(int, int)
	 */
	public Point toDisplay(int x, int y) {
		return tableInstance.toDisplay(x, y);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#toDisplay(org.eclipse.swt.graphics.Point)
	 */
	public Point toDisplay(Point point) {
		return tableInstance.toDisplay(point);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Widget#toString()
	 */
	public String toString() {
		return tableInstance.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#traverse(int)
	 */
	public boolean traverse(int traversal) {
		return tableInstance.traverse(traversal);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Control#update()
	 */
	public void update() {
		tableInstance.update();
	}
	/**
	 * Refreshing hosting TableViewer this.getHostingViewer().refresh()	 
	 */
	public void refreshHostingViewer() {
		getHostingViewer().refresh();
	}
	/**
	 * Refreshing hosting TableViewer this.getHostingViewer().refresh().
	 * Updating items count to column header set in Construction. 
	 * If <code>columnDataHeaderName</code> or <code>showItemCountInColumn</code>
	 * is not set in Construction, just refreshing hosting TableViewer.
	 * 
	 * @param columnDateHeaderCount
	 */
	public void refreshHostingViewer(int columnDateHeaderCount) {
		
		if(columnDataHeaderName!=null 
				&& this.showItemCountInColumn != COLUMN_DATA_COUNT_NOT_SET){
			TableColumn tblColumn = getColumn(this.showItemCountInColumn);				
			tblColumn.setText(columnDataHeaderName
						+ " (" + columnDateHeaderCount + TXT_ITEMS_IN_COLUMN_HEADER +")"); //$NON-NLS-1$ //$NON-NLS-2$
		}		
		getHostingViewer().refresh();
	}

}
