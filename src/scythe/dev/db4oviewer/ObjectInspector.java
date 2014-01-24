package scythe.dev.db4oviewer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.border.BevelBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.ReflectField;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED 
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ObjectInspector extends JFrame implements ActionListener, MouseListener {
	
	Db4oViewer viewer;
	private JSplitPane jSplitPane1;
	private JPanel jPanel1;
	private JButton refreshButton;
	private JButton editApply;
	private JTextArea fieldEdit;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JButton deleteButton;
	private JButton saveButton;
	private JScrollPane jScrollPane1;
	Object object;

	private JTree jTree1;
	
	public static TransferableTreeNode transferNode;
	public FieldWrapperObjectTree currEditField;
	
	public ObjectInspector(Db4oViewer viewer, Object object)
	{
		this.viewer = viewer;
		this.object = object;
		
		this.setPreferredSize(new Dimension(600, 800));
		BorderLayout thisLayout = new BorderLayout();
		getContentPane().setLayout(thisLayout);
		this.setLocation(400, 150);

		{
			jSplitPane1 = new JSplitPane();
			getContentPane().add(jSplitPane1, BorderLayout.NORTH);
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			{
				jPanel1 = new JPanel();
				jSplitPane1.add(jPanel1, JSplitPane.BOTTOM);
				BorderLayout jPanel1Layout = new BorderLayout();
				jPanel1.setLayout(jPanel1Layout);
				jPanel1.setPreferredSize(new java.awt.Dimension(590, 191));
				{
					jPanel2 = new JPanel();
					FlowLayout jPanel2Layout = new FlowLayout();
					jPanel1.add(jPanel2, BorderLayout.SOUTH);
					jPanel2.setPreferredSize(new java.awt.Dimension(592, 33));
					jPanel2.setLayout(jPanel2Layout);
					{
						saveButton = new JButton();
						jPanel2.add(saveButton);
						saveButton.setText("Save");
						saveButton.setActionCommand("save");
						saveButton.addActionListener(this);
					}
					{
						deleteButton = new JButton();
						jPanel2.add(deleteButton);
						deleteButton.setText("Delete");
						deleteButton.setActionCommand("delete");
						deleteButton.addActionListener(this);
					}
					{
						refreshButton = new JButton();
						jPanel2.add(refreshButton);
						refreshButton.setText("Refresh");
						refreshButton.setActionCommand("refresh");
						refreshButton.addActionListener(this);
					}
				}
				{
					jPanel3 = new JPanel();
					jPanel1.add(jPanel3, BorderLayout.NORTH);
					FlowLayout jPanel3Layout = new FlowLayout();
					jPanel3.setLayout(jPanel3Layout);
					jPanel3.setPreferredSize(new java.awt.Dimension(592, 114));
					{
						fieldEdit = new JTextArea();
						jPanel3.add(fieldEdit);
						fieldEdit.setPreferredSize(new java.awt.Dimension(324, 92));
						fieldEdit.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					}
					{
						editApply = new JButton();
						jPanel3.add(editApply);
						editApply.setText("Apply");
						editApply.setActionCommand("apply");
						editApply.addActionListener(this);
					}
				}
			}
			{
				jScrollPane1 = new JScrollPane();
				jSplitPane1.add(jScrollPane1, JSplitPane.TOP);
				{
					jTree1 = new AutoScrollingJTree(new DefaultMutableTreeNode());
					jScrollPane1.setViewportView(jTree1);
					jTree1.setDragEnabled(true);
					
					TreeDragSource ds = new TreeDragSource(jTree1, DnDConstants.ACTION_COPY_OR_MOVE);
					TreeDropTarget dt = new TreeDropTarget(jTree1);						
				}
			}
		}

		if (object != null)
		{
			this.setTitle(object.toString());
		}
		
		System.out.println(object);
		
		buildTree();
		jTree1.addMouseListener(this);
		
		this.pack();
		this.setVisible(true);
	}
	
	public void buildTree()
	{
		DefaultMutableTreeNode top = null;
		try {
			top = fillTree(new DefaultMutableTreeNode(object), object);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jTree1.setModel(new JTree(top).getModel());
		
		jTree1.invalidate();
	}
	
	
	public DefaultMutableTreeNode fillTree(DefaultMutableTreeNode branch, Object currObject) throws IllegalArgumentException, SecurityException, IllegalAccessException
	{
		viewer.container.activate(currObject, 3);
		
		Vector<Field> fields = allFieldsOfClass(new Vector<Field>(), currObject.getClass());
		
		Object[] fieldsArray = (Object[])fields.toArray();
		Comparator<Object> comp = new Comparator<Object>()
		{
			public int compare(Object o1, Object o2) {
				if (o1 == null)
					return -1;
				else if (o2 == null)
					return 1;
				else if (o1 instanceof Field && o2 instanceof Field)
					return ((Field)o1).getName().compareTo(((Field)o2).getName());
				else
					return o1.toString().compareTo(o2.toString());
			}
		};
		Arrays.sort(fieldsArray, comp);
		
		for (Object obj: fieldsArray)
		{
			if (obj instanceof Field)
			{
				Field field = (Field)obj;
				
				System.out.println("field: "+field);
				
				if (!Modifier.isTransient(field.getModifiers()) && !Modifier.isStatic(field.getModifiers()))
				{
					field.setAccessible(true);
					//Field currField = findField(field.getName(), object.getClass());
					
					DefaultMutableTreeNode currFieldNode = new DefaultMutableTreeNode(new FieldWrapperObjectTree(field, currObject));
					branch.add(currFieldNode);
				}
			}
		}
		
		return branch;
		
	}
	
	public static Object getFieldValue(Field field, Object onObject)
	{
		Object val = null;
		if (field != null)
		{
			field.setAccessible(true);

			try {
				val = field.get(onObject);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return val;
	}

	public Vector<Field> allFieldsOfClass(Vector<Field> result, Class onClass)
	{
		Field[] fields = onClass.getDeclaredFields();
		for (Field field: fields)
		{
			result.add(field);
		}

		Class superClass = onClass.getSuperclass();
		while (superClass != null)
		{
			result = allFieldsOfClass(result, superClass);
			superClass = superClass.getSuperclass(); // go one up
		}

		return result;
	}
	

	public void mouseClicked(MouseEvent e) {
		
		if (e.getSource() == jTree1)
		{
			int[] selected = jTree1.getSelectionRows();
			if (selected.length > 0)
			{
				//DefaultMutableTreeNode clicked = (DefaultMutableTreeNode)jTree1.getModel().getChild(jTree1.getModel().getRoot(), selected[0]-1);
				DefaultMutableTreeNode clicked = (DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
				
				if (clicked != null && clicked.getUserObject() instanceof FieldWrapperObjectTree)
				{
					FieldWrapperObjectTree wrapper = ((FieldWrapperObjectTree)clicked.getUserObject());
					Object ref = getFieldValue(wrapper.getTheField(), wrapper.getOnObject());
					
					if (e.getClickCount() >= 2)
					{
						if (ref != null) // ref.getClass().getName().startsWith("scythe.dev")
						{
							try {
								fillTree(clicked, ref);
							} catch (IllegalArgumentException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SecurityException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IllegalAccessException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							jTree1.invalidate();
							jTree1.repaint();
						}
					}
					else if (e.getClickCount() == 1)
					{
						if (clicked.getUserObject() instanceof FieldWrapperObjectTree)
						{
							FieldWrapperObjectTree fieldWrapper = (FieldWrapperObjectTree)clicked.getUserObject();
							this.currEditField = fieldWrapper;
							
							fieldEdit.setText(""+getFieldValue(fieldWrapper.getTheField(), fieldWrapper.getOnObject()));
						}
						else
						{
							this.currEditField = null;
							fieldEdit.setText("");
						}
					}
					
					
				}
			}
		}
		
	}

	
	
	
	// ################################################
	// #############        EVENTS        #############
	// ################################################
	
	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("delete"))
		{
			viewer.container.delete(object);
		}
		else if (e.getActionCommand().equals("save"))
		{
			viewer.container.store(object);
		}
		else if (e.getActionCommand().equals("refresh"))
		{
			viewer.container.activate(object, 3);
			buildTree();
		}
		else if (e.getActionCommand().equals("apply"))
		{
			if (this.currEditField != null)
			{
				this.currEditField.getTheField().setAccessible(true);
				try 
				{
					Class fieldType = this.currEditField.getTheField().getType();
					Object fieldVal = getFieldValue(this.currEditField.getTheField(), this.currEditField.getOnObject());
					
					String textVal = fieldEdit.getText();
					Object newVal = null;
					
					if (fieldVal instanceof String)
					{
						newVal = textVal;
					}
					else if (fieldVal instanceof Boolean)
					{
						newVal = Boolean.valueOf(textVal);
					}
					else if (fieldVal instanceof Integer)
					{
						newVal = Integer.valueOf(textVal);
					}
					else if (fieldVal instanceof Float)
					{
						newVal = Float.valueOf(textVal);
					}
					else if (fieldVal instanceof Double)
					{
						newVal = Double.valueOf(textVal);
					}
					else
					{
						throw new IllegalArgumentException();
					}
					
					this.currEditField.getTheField().set(this.currEditField.getOnObject(), newVal);
				} 
				catch (IllegalArgumentException e1) 
				{
					JOptionPane.showMessageDialog(this, "Value '"+fieldEdit.getText()+"' could not be converted to type "+this.currEditField.getTheField().getType().getName());
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	  
		        jTree1.invalidate();
		        jTree1.repaint();
			}
		}

	}	

}
// ################################################
// #############     INNER CLASSES    #############
// ################################################

  class AutoScrollingJTree extends JTree implements Autoscroll 
  {
	    private int margin = 12;

	    public AutoScrollingJTree(DefaultMutableTreeNode rootNode) {
	      super(rootNode);
	    }

	    public void autoscroll(Point p) {
	      int realrow = getRowForLocation(p.x, p.y);
	      Rectangle outer = getBounds();
	      realrow = (p.y + outer.y <= margin ? realrow < 1 ? 0 : realrow - 1
	          : realrow < getRowCount() - 1 ? realrow + 1 : realrow);
	      scrollRowToVisible(realrow);
	    }

	    public Insets getAutoscrollInsets() {
	      Rectangle outer = getBounds();
	      Rectangle inner = getParent().getBounds();
	      return new Insets(inner.y - outer.y + margin, inner.x - outer.x
	          + margin, outer.height - inner.height - inner.y + outer.y
	          + margin, outer.width - inner.width - inner.x + outer.x
	          + margin);
	    }

	    // Use this method if you want to see the boundaries of the
	    // autoscroll active region
	    /*
	    public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      Rectangle outer = getBounds();
	      Rectangle inner = getParent().getBounds();
	      g.setColor(Color.red);
	      g.drawRect(-outer.x + 12, -outer.y + 12, inner.width - 24,
	          inner.height - 24);
	    }
	    */
	}

	//TreeDragSource.java
	//A drag source wrapper for a JTree. This class can be used to make
	//a rearrangeable DnD tree with the TransferableTreeNode class as the
	//transfer data type.

	class TreeDragSource implements DragSourceListener, DragGestureListener {

	  DragSource source;

	  DragGestureRecognizer recognizer;

	  TransferableTreeNode transferable;

	  DefaultMutableTreeNode oldNode;

	  JTree sourceTree;

	  public TreeDragSource(JTree tree, int actions) {
	    sourceTree = tree;
	    source = new DragSource();
	    recognizer = source.createDefaultDragGestureRecognizer(sourceTree,
	        actions, this);
	  }

	  /*
	   * Drag Gesture Handler
	   */
	  public void dragGestureRecognized(DragGestureEvent dge) {
	    TreePath path = sourceTree.getSelectionPath();
	    if (path == null) {
	      // We can't move an empty selection
	      return;
	    }
	    oldNode = (DefaultMutableTreeNode) path.getLastPathComponent();
	    transferable = new TransferableTreeNode(path);
	    ObjectInspector.transferNode = transferable;
	    try
	    {
	    source.startDrag(dge, DragSource.DefaultMoveDrop, transferable, this);
	    }
	    catch (Exception e)
	    {}
	  }

	  /*
	   * Drag Event Handlers
	   */
	  public void dragEnter(DragSourceDragEvent dsde) {
	  }

	  public void dragExit(DragSourceEvent dse) {
	  }

	  public void dragOver(DragSourceDragEvent dsde) {
	  }

	  public void dropActionChanged(DragSourceDragEvent dsde) {
	    System.out.println("Action: " + dsde.getDropAction());
	    System.out.println("Target Action: " + dsde.getTargetActions());
	    System.out.println("User Action: " + dsde.getUserAction());
	  }

	  public void dragDropEnd(DragSourceDropEvent dsde) {
	    /*
	     * to support move or copy, we have to check which occurred:
	     */
	    System.out.println("Drop Action: " + dsde.getDropAction());
	    if (dsde.getDropSuccess()
	        && (dsde.getDropAction() == DnDConstants.ACTION_MOVE)) {
	      
	    	//((DefaultTreeModel) sourceTree.getModel()).removeNodeFromParent(oldNode);
	    }

	    /*
	     * to support move only... if (dsde.getDropSuccess()) {
	     * ((DefaultTreeModel)sourceTree.getModel()).removeNodeFromParent(oldNode); }
	     */
	  }
	}

	//TreeDropTarget.java
	//A quick DropTarget that's looking for drops from draggable JTrees.
	//

	class TreeDropTarget implements DropTargetListener {

	  DropTarget target;

	  JTree targetTree;

	  public TreeDropTarget(JTree tree) {
	    targetTree = tree;
	    target = new DropTarget(targetTree, this);
	  }

	  /*
	   * Drop Event Handlers
	   */
	  private TreeNode getNodeForEvent(DropTargetDragEvent dtde) {
	    Point p = dtde.getLocation();
	    DropTargetContext dtc = dtde.getDropTargetContext();
	    JTree tree = (JTree) dtc.getComponent();
	    TreePath path = tree.getClosestPathForLocation(p.x, p.y);
	    return (TreeNode) path.getLastPathComponent();
	  }

	  public void dragEnter(DropTargetDragEvent dtde) {
	    TreeNode node = getNodeForEvent(dtde);
	    if (node.isLeaf()) {
	      dtde.rejectDrag();
	    } else {
	      // start by supporting move operations
	      //dtde.acceptDrag(DnDConstants.ACTION_MOVE);
	      dtde.acceptDrag(dtde.getDropAction());
	    }
	  }

	  public void dragOver(DropTargetDragEvent dtde) {
	    TreeNode node = getNodeForEvent(dtde);
	    /*
	    if (node.isLeaf()) {
	      dtde.rejectDrag();
	    } else {
	      // start by supporting move operations
	      //dtde.acceptDrag(DnDConstants.ACTION_MOVE);
	      dtde.acceptDrag(dtde.getDropAction());
	    }*/
	    dtde.acceptDrag(dtde.getDropAction());
	  }

	  public void dragExit(DropTargetEvent dte) {
	  }

	  public void dropActionChanged(DropTargetDragEvent dtde) {
	  }

	  public void drop(DropTargetDropEvent dtde) {
	    Point pt = dtde.getLocation();
	    DropTargetContext dtc = dtde.getDropTargetContext();
	    JTree tree = (JTree) dtc.getComponent();
	    TreePath parentpath = tree.getClosestPathForLocation(pt.x, pt.y);
	    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) parentpath.getLastPathComponent();

	    try {
	      //Transferable tr = dtde.getTransferable(); // !!! this should work, but doesn't. after one click on the draggable node, this call here will not return the TransferableTreeNode that is created in dragGestureRecognized()
	      Transferable tr = ObjectInspector.transferNode; // therefore, we use this hack, which just stores the current transferable in a static variable
	      
	      DataFlavor[] flavors = tr.getTransferDataFlavors();
	      for (int i = 0; i < flavors.length; i++) {
	        if (tr.isDataFlavorSupported(flavors[i])) {
	          dtde.acceptDrop(dtde.getDropAction());
	          TreePath p = (TreePath) tr.getTransferData(flavors[i]);
	          DefaultMutableTreeNode node = (DefaultMutableTreeNode) p.getLastPathComponent();
	          DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
	          
	          Object sourceObject = node.getUserObject(); 
	          
	          FieldWrapperObjectTree targetField = ((FieldWrapperObjectTree)parent.getUserObject());
	          
	          if (sourceObject != null)
	          {
	        	  if (sourceObject instanceof FieldWrapperObjectTree)
	        	  {
	        		  FieldWrapperObjectTree sourceField = (FieldWrapperObjectTree)sourceObject;

		        	  if (sourceField.getTheField().getType().equals(targetField.getTheField().getType()))
		        	  {
		        		  targetField.getTheField().set(targetField.getOnObject(), sourceField.getTheField().get(sourceField.getOnObject()));
		        	  }
	        	  }
	        	  else if (sourceObject.getClass().equals(targetField.getTheField().getType()))
	        	  {
	        		  targetField.getTheField().set(targetField.getOnObject(), sourceObject);
	        	  }
	        	  
		          tree.invalidate();
		          tree.repaint();
	          }
	          
	          dtde.dropComplete(true);
	          return;
	        }
	      }
	      dtde.rejectDrop();
	    } catch (Exception e) {
	      e.printStackTrace();
	      //dtde.rejectDrop();
	    }
	  }
	}	
	
	
	class TransferableTreeNode implements Transferable, Serializable {

		  public static DataFlavor TREE_PATH_FLAVOR = new DataFlavor(TreePath.class,
		      "Tree Path");

		  DataFlavor flavors[] = { TREE_PATH_FLAVOR };

		  TreePath path;

		  public TransferableTreeNode(TreePath tp) {
		    path = tp;
		  }

		  public synchronized DataFlavor[] getTransferDataFlavors() {
		    return flavors;
		  }

		  public boolean isDataFlavorSupported(DataFlavor flavor) {
		    return (flavor.getRepresentationClass() == TreePath.class);
		  }

		  public synchronized Object getTransferData(DataFlavor flavor)
		      throws UnsupportedFlavorException, IOException {
		    if (isDataFlavorSupported(flavor)) {
		      return (Object) path;
		    } else {
		      throw new UnsupportedFlavorException(flavor);
		    }
		  }
		}		
	