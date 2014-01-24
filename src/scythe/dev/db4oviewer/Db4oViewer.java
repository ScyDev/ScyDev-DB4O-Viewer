package scythe.dev.db4oviewer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.db4o.query.Query;
import com.db4o.reflect.ReflectClass;
import com.db4o.reflect.ReflectField;
import com.db4o.reflect.core.ReflectorUtils;
import com.spaceprogram.db4o.sql.Sql4o;
import com.spaceprogram.db4o.sql.Sql4oException;
import com.spaceprogram.db4o.sql.parser.SqlParseException;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.BorderFactory;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;


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
public class Db4oViewer extends JFrame implements ActionListener, MouseListener, KeyListener  {

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	ObjectContainer container;
	Hashtable<String, ReflectClass> knownClasses;
	String sql = "";
	
	private static final String SETTINGS_FILENAME = "db4o_viewer_settings.ini";
	private String host = "";
	private String port = "";
	private String username = "";
	private String password = "";
	private String classFilter = "";
	
	
	private JSplitPane jSplitPane1;
	private JButton connectButton1;
	private JLabel jLabel3;
	private JTextField portField;
	private JLabel jLabel2;
	private JTextField hostField;
	private JLabel jLabel1;
	private JLabel jLabel4;
	private JButton querySendButton;
	private JTextArea queryEdit;
	private JPanel jPanel6;
	private JTextField filterField;
	private JLabel jLabel5;
	private JPanel jPanel5;
	private JPanel jPanel4;
	private JLabel countLabel;
	private JPanel jPanel3;
	private JTable jTable2;
	private JScrollPane jScrollPane2;
	private JScrollPane jScrollPane1;
	private JPanel jPanel1;
	private JSplitPane jSplitPane2;
	private JTextField passwordField;
	private JTextField usernameField;
	private JPanel jPanel2;
	private JTree jTree1;
	private JTable jTable1;

	public static void main(String[] args)
	{
		new Db4oViewer();
	}
	
	public Db4oViewer()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		loadSettings();
		
		{
			BorderLayout thisLayout = new BorderLayout();
			getContentPane().setLayout(thisLayout);
			this.setBounds(0, 0, 777, 488);
			this.setPreferredSize(new Dimension(1400, 800));
		{
			jSplitPane1 = new JSplitPane();
			getContentPane().add(jSplitPane1, BorderLayout.CENTER);
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setPreferredSize(new java.awt.Dimension(710, 440));
			{
				jPanel1 = new JPanel();
				BorderLayout jPanel1Layout = new BorderLayout();
				jPanel1.setLayout(jPanel1Layout);
				jSplitPane1.add(jPanel1, JSplitPane.BOTTOM);
				{
					jSplitPane2 = new JSplitPane();
					jPanel1.add(jSplitPane2, BorderLayout.CENTER);
					{
						jPanel4 = new JPanel();
						BorderLayout jPanel4Layout = new BorderLayout();
						jPanel4.setLayout(jPanel4Layout);
						jSplitPane2.add(jPanel4, JSplitPane.LEFT);
						jPanel4.setPreferredSize(new java.awt.Dimension(490, 710));
						{
							jScrollPane1 = new JScrollPane();
							jPanel4.add(jScrollPane1, BorderLayout.CENTER);
							{
								jTree1 = new JTree(new DefaultMutableTreeNode());
								jScrollPane1.setViewportView(jTree1);
								GroupLayout jTree1Layout = new GroupLayout((JComponent)jTree1);
								jTree1.setLayout(jTree1Layout);
								jTree1.setMaximumSize(new java.awt.Dimension(0, 0));
								jTree1.setMinimumSize(new Dimension(400, 100));
								jTree1.setOpaque(false);
								jTree1Layout.setVerticalGroup(jTree1Layout.createParallelGroup());
								jTree1Layout.setHorizontalGroup(jTree1Layout.createParallelGroup());
								
								jTree1.addMouseListener(this);
							}
							jScrollPane1.setMinimumSize(new Dimension(400, 100));
						}
						{
							jPanel5 = new JPanel();
							FlowLayout jPanel5Layout = new FlowLayout();
							jPanel4.add(jPanel5, BorderLayout.NORTH);
							jPanel5.setLayout(jPanel5Layout);
							{
								jLabel5 = new JLabel();
								jPanel5.add(jLabel5);
								jLabel5.setText("Filter:");
							}
							{
								filterField = new JTextField();
								jPanel5.add(filterField);
								filterField.setPreferredSize(new java.awt.Dimension(222, 21));
								filterField.addKeyListener(this);
								filterField.setText(this.classFilter);
							}
						}
					}
					{
						jPanel3 = new JPanel();
						jSplitPane2.add(jPanel3, JSplitPane.RIGHT);
						BorderLayout jPanel3Layout = new BorderLayout();
						jPanel3.setLayout(jPanel3Layout);
						jPanel3.setPreferredSize(new java.awt.Dimension(696, 398));
						{
							jScrollPane2 = new JScrollPane();
							jPanel3.add(jScrollPane2, BorderLayout.CENTER);
							jScrollPane2.setPreferredSize(new java.awt.Dimension(553, 398));
							{
								Object[][] data = {{1234}, {1234}};
								Object[] cols = {6789, 6789};
								TableModel jTable1Model = 
									new DefaultTableModel();
								jTable1 = new JTable() {
										public boolean isCellEditable(int row, int column) {
											return false;
										}};
								jScrollPane2.setViewportView(jTable1);
								//getContentPane().add(jTable1, BorderLayout.WEST);
								
								jTable1.setModel(jTable1Model);
								jTable1.setRowMargin(3);
								jTable1.addMouseListener(this);
								
								jTable1.setCellEditor(null);
								jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

								//jTable1.setDefaultEditor(columnClass, editor)
							}
						}
						{
							countLabel = new JLabel();
							jPanel3.add(countLabel, BorderLayout.SOUTH);
							countLabel.setHorizontalTextPosition(SwingConstants.CENTER);
							countLabel.setHorizontalAlignment(SwingConstants.CENTER);
							countLabel.setText("0 objects");
						}
						{
							jPanel6 = new JPanel();
							FlowLayout jPanel6Layout = new FlowLayout();
							jPanel3.add(jPanel6, BorderLayout.NORTH);
							jPanel6.setLayout(jPanel6Layout);
							jPanel6.setPreferredSize(new java.awt.Dimension(981, 80));
							{
								queryEdit = new JTextArea();
								jPanel6.add(queryEdit);
								queryEdit.setPreferredSize(new java.awt.Dimension(517, 68));
								queryEdit.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
							}
							{
								querySendButton = new JButton();
								jPanel6.add(querySendButton);
								querySendButton.setText("Go");
								querySendButton.setPreferredSize(new java.awt.Dimension(41, 21));
								querySendButton.setActionCommand("query");
								querySendButton.addActionListener(this);
							}
						}
					}
				}
			}
			{
				jPanel2 = new JPanel();
				jSplitPane1.add(jPanel2, JSplitPane.TOP);
				jPanel2.setPreferredSize(new java.awt.Dimension(767, 45));
				jPanel2.setLayout(null);
				jPanel2.setMinimumSize(new java.awt.Dimension(10, 45));
				{
					connectButton1 = new JButton();
					jPanel2.add(connectButton1);
					connectButton1.setText("Connect");
					connectButton1.setActionCommand("connect");
					connectButton1.setBounds(6, 12, 69, 21);
					connectButton1.addActionListener(this);
				}
				{
					jLabel1 = new JLabel();
					jPanel2.add(jLabel1);
					jLabel1.setText("Host:");
					jLabel1.setBounds(87, 15, 26, 14);
				}
				{
					hostField = new JTextField();
					jPanel2.add(hostField);
					hostField.setBounds(119, 12, 81, 21);
					hostField.setText(this.host);
				}
				{
					jLabel2 = new JLabel();
					jPanel2.add(jLabel2);
					jLabel2.setText("Port:");
					jLabel2.setBounds(206, 15, 24, 14);
				}
				{
					portField = new JTextField();
					jPanel2.add(portField);
					portField.setBounds(236, 12, 54, 21);
					portField.setText(this.port);
				}
				{
					jLabel3 = new JLabel();
					jPanel2.add(jLabel3);
					jLabel3.setText("Username:");
					jLabel3.setBounds(296, 15, 52, 14);
				}
				{
					usernameField = new JTextField();
					jPanel2.add(usernameField);
					usernameField.setBounds(354, 12, 140, 21);
					usernameField.setText(this.username);
				}
				{
					jLabel4 = new JLabel();
					jPanel2.add(jLabel4);
					jLabel4.setText("Password");
					jLabel4.setBounds(506, 15, 46, 14);
				}
				{
					passwordField = new JTextField();
					jPanel2.add(passwordField);
					passwordField.setBounds(558, 12, 132, 21);
					passwordField.setText(this.password);
				}
			}
		}
		}

		//pack();
		this.setLocation(200, 200);
		this.setVisible(true);
		this.setTitle("ScyDev DB4O Viewer - See and edit your objects");
		pack();
	}
	
	public void connect(String host, String port, String username, String passwd)
	{
		writeSettings();
		
		// for v7.8 // container = Db4oClientServer.openClient(Db4oClientServer.newClientConfiguration(), host, new Integer(port).intValue(), username, passwd);
		container = Db4o.openClient(host, new Integer(port).intValue(), username, passwd);
		
		if (container != null)
		{
			fillClassTree();
		}
	}
	
	public void fillClassTree()
	{
		knownClasses = new Hashtable<String, ReflectClass>();

		ReflectClass[] classes = null;
		try {
			classes = container.ext().knownClasses();
		} 
		catch (Exception e) {
			System.out.println("Damn! DB4Os 'container.ext().knownClasses();' doesn't work... finding known classes by queryinng all objects. Slow.");
			
			Query query=container.query();
			query.constrain(Object.class);
			//query.descend("ownerId").constrain("thluks"); // use ID not name!!!!!!!!!!!!!
				
			Iterator iter = (Iterator)query.execute();
			Hashtable<ReflectClass, ReflectClass> refs = new Hashtable<ReflectClass, ReflectClass>();
			
			for (int i = 0; iter.hasNext(); i++)
			{
				System.out.print(".");
				Object currObj = (Object)iter.next();
				ReflectClass refRef = ReflectorUtils.reflectClassFor(container.ext().reflector(), currObj);
				refs.put(refRef, refRef);
				
				Object[] objectArray = refs.keySet().toArray();
				classes = new ReflectClass[objectArray.length];
				for (int j = 0; j < objectArray.length; j++)
				{
					classes[j] = (ReflectClass)objectArray[j];
				}
			}
			System.out.println(".");
		}
		
		DefaultMutableTreeNode knownClassesNode = new DefaultMutableTreeNode("Classes");

		for (ReflectClass currClass: classes)
		{
			knownClasses.put(currClass.getName(), currClass);
		}
		Vector<String> knownClassNames = (Vector<String>)Db4oViewer.enumerationToVector(knownClasses.keys());		
		Collections.sort(knownClassNames);
		
		knownClassNames = filterClasses(knownClassNames);
		
		for (String currClassName: knownClassNames)
		{
			ReflectClass currClass = knownClasses.get(currClassName);
			

				System.out.println("Class: "+currClass.getName());
				DefaultMutableTreeNode currClassNode = new DefaultMutableTreeNode(new ClassWrapper(currClass));
				knownClassesNode.add(currClassNode);
				
				Object[] fields = currClass.getDeclaredFields();
				Comparator<Object> comp = new Comparator<Object>()
				{

					public int compare(Object o1, Object o2) {
						if (o1 == null)
							return -1;
						else if (o2 == null)
							return 1;
						else if (o1 instanceof ReflectField && o2 instanceof ReflectField)
							return ((ReflectField)o1).getName().compareTo(((ReflectField)o2).getName());
						else
							return o1.toString().compareTo(o2.toString());
					}
				};
				Arrays.sort(fields, comp);
				
				for (Object obj: fields)
				{
					if (obj instanceof ReflectField)
					{
						ReflectField currField = (ReflectField)obj;
						
						if (!currField.isStatic() && !currField.isTransient())
						{
							//System.out.println("---> Field: "+currField.getName());
							DefaultMutableTreeNode currFieldNode = new DefaultMutableTreeNode(currField.getName()+" ["+currField.getFieldType().getName()+"]");
							currClassNode.add(currFieldNode);
						}
					}
				}

		}
		
		jTree1.setModel(new JTree(knownClassesNode).getModel());

		//jTree1.setModel(top);
	}
	
	public void fillTable(final ReflectClass clicked)
	{
		//System.out.println("Query class: "+clicked.getName());
		int counter = 0;
		
		Object[] columns = {};
		Object[][] data = {};
		
		Thread tableClearThread = new Thread(){
			public void run()
			{
				Object[] columns = {};
				Object[][] data = {{"Loading"}, {"..."}};
				jTable1.setModel(new DefaultTableModel(data, columns)); // empty table
			}
		};
		tableClearThread.start();
		

		// find real class (breack out of db4o generic reflect class)
		Class type = null;
		try {
			type = Class.forName(clicked.getName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// headers
		Vector<ReflectField> fields = allFieldsOfClass(new Vector<ReflectField>(), clicked);
		Vector showFields = new Vector();
		showFields.add("THIS");
		for (ReflectField field: fields)
		{
			if (!field.isTransient() && !field.isStatic())
			{
				String fieldName = ((ReflectField)field).getName();
				fieldName = fieldName.substring(fieldName.lastIndexOf(":")+1);
				showFields.add(fieldName);
			}
		}
		columns = showFields.toArray();
		
		
		// data
		/*
		Query query=container.query();
		query.constrain(type);
		//query.descend("class").descend("className").constrain(clicked.getClass().getName());
		ObjectSet result = query.execute();
		*/
		
		List list = null;
	
		try {
			list = Sql4o.execute(container, this.sql);
		} catch (SqlParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Sql4oException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		Iterator result = list.iterator();

		
		/*
		List list = container.query(new Predicate() {
			public boolean match(Object obj) {
				return obj.getClass().getName().equals(clicked.getClass().getName());
			}
		});
		Iterator result = list.iterator();
		*/
		
		Vector rows = new Vector();
		while (result.hasNext())
		{
			Object currRowResult = result.next();
			//System.out.println("row: "+currRowResult);
			Vector rowCols = new Vector();
			rowCols.add(currRowResult); // add object itself in first col
			
			for (Object field: columns)
			{
				String fieldName = ((String)field);
				//System.out.println("---> col: "+fieldName);
				
				if (!fieldName.equals("THIS"))
				{
					try {
						//Field currField = currRowResult.getClass().getField(fieldName);
						Field currField = findField(fieldName, currRowResult.getClass());
						
						Object val = null;
						if (currField != null)
						{
							currField.setAccessible(true);
	
							val = currField.get(currRowResult);
						}
						
						rowCols.add(val);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} /*catch (NoSuchFieldException e) {
						rowCols.add("");
						e.printStackTrace();
					}*/
				}
			}
			
			rows.add(rowCols.toArray());
			counter++;
		}
		
		countLabel.setText(counter+" objects");
		
		data = new Object[rows.size()][columns.length];
		for (int i = 0; i < rows.size(); i++)
		{
			data[i] = (Object[])rows.elementAt(i);
		}
		
		
		jTable1.setModel(new DefaultTableModel(data, columns));
		calcColumnWidths(jTable1);
		
		//jTable1.setMinimumSize(new Dimension(5000, jTable1.getSize().height));
		
		/*
		jTable1.repaint();
		jTable1.invalidate();
		 */
	}
	
	public static Vector<ReflectField> allFieldsOfClass(Vector<ReflectField> result, ReflectClass onClass)
	{
		ReflectField[] fields = onClass.getDeclaredFields();
		for (ReflectField field: fields)
		{
			result.add(field);
		}

		ReflectClass superClass = onClass.getSuperclass();
		while (superClass != null)
		{
			result = allFieldsOfClass(result, superClass);
			superClass = superClass.getSuperclass(); // go one up
		}

		return result;
	}
	
	/**
	 * why not use: currRowResult.getClass().getField(fieldName).get(currRowResult);
	 * 
	 * BECAUSE: getField() uses this.name instead of methods name param...  which results in classname instead of fieldname, and therefore NoSuchField...
	 * 
	 * @param fieldName
	 * @return
	 */
	public Field findField(String fieldName, Class onClass)
	{
		Field foundField = null;
		
		Field[] fields = onClass.getDeclaredFields();
		for (Field field: fields)
		{
			if (field.getName().equals(fieldName))
			{
				foundField = field;
			}
		}
		
		if (foundField == null)
		{
			Class superClass = onClass.getSuperclass();
			while (superClass != null && foundField == null)
			{
				foundField = findField(fieldName, superClass);
				superClass = superClass.getSuperclass(); // go one up
			}
		}
		
		return foundField;
	}
	
	public void loadSettings()
	{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(SETTINGS_FILENAME));
		} catch (FileNotFoundException e) {
			return;
		}
		
		String currLine;
		try {
			while ((currLine = br.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(currLine, "=");
			
				try
				{
					String name = st.nextToken();
					String value = st.nextToken();

					if (name.equals("host"))
					{
						this.host = value;
					}
					else if (name.equals("port"))
					{
						this.port = value;
					}
					else if (name.equals("username"))
					{
						this.username = value;
					}
					else if (name.equals("password"))
					{
						this.password = value;
					}
					else if (name.equals("filter"))
					{
						this.classFilter = value;
					}
				}
				catch(NoSuchElementException e)
				{}
			}
			
			br.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void writeSettings()
	{
		FileWriter fw = null;
		try {
			fw = new FileWriter(SETTINGS_FILENAME);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			fw.write("host="+this.hostField.getText()+"\n");
			fw.write("port="+this.portField.getText()+"\n");
			fw.write("username="+this.usernameField.getText()+"\n");
			fw.write("password="+this.passwordField.getText()+"\n");
			fw.write("filter="+this.filterField.getText()+"\n");

			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Vector<String> filterClasses(Vector<String> classNames)
	{
		Vector<String> filtered = new Vector<String>();
		if (this.classFilter != null && !this.classFilter.equals(""))
		{
			for (String currName: classNames)
			{
				if (currName.startsWith(this.classFilter))
				{
					filtered.add(currName);
				}
			}
		}
		else
		{
			return classNames;
		}
		
		return filtered;
	}
	
	public void calcColumnWidths(JTable table)
	{
	    JTableHeader header = table.getTableHeader();

	    TableCellRenderer defaultHeaderRenderer = null;

	    if (header != null)
	        defaultHeaderRenderer = header.getDefaultRenderer();

	    TableColumnModel columns = table.getColumnModel();
	    TableModel data = table.getModel();

	    int margin = columns.getColumnMargin(); // only JDK1.3

	    int rowCount = data.getRowCount();

	    int totalWidth = 0;

	    for (int i = columns.getColumnCount() - 1; i >= 0; --i)
	    {
	        TableColumn column = columns.getColumn(i);
	            
	        int columnIndex = column.getModelIndex();
	            
	        int width = -1; 

	        TableCellRenderer h = column.getHeaderRenderer();
	          
	        if (h == null)
	            h = defaultHeaderRenderer;
	            
	        if (h != null) // Not explicitly impossible
	        {
	            Component c = h.getTableCellRendererComponent
	                   (table, column.getHeaderValue(),
	                    false, false, -1, i);
	                    
	            width = c.getPreferredSize().width;
	        }
	       
	        for (int row = rowCount - 1; row >= 0; --row)
	        {
	            TableCellRenderer r = table.getCellRenderer(row, i);
	                 
	            Component c = r.getTableCellRendererComponent
	               (table,
	                data.getValueAt(row, columnIndex),
	                false, false, row, i);
	        
	                width = Math.max(width, c.getPreferredSize().width);
	        }

	        if (width >= 0)
	            column.setPreferredWidth(width + margin); // <1.3: without margin
	        else
	            ; // ???
	            
	        totalWidth += column.getPreferredWidth();
	    }
	    

	    totalWidth += columns.getColumnCount() * columns.getColumnMargin();

	    Dimension size = table.getPreferredScrollableViewportSize();

	    size.width = totalWidth;

	    table.setPreferredScrollableViewportSize(size);
	    table.setSize(size);
	    table.setMinimumSize(size);

	   //table.sizeColumnsToFit(-1); 

	     if (header != null)
	        header.repaint(); 
	}	
	
	
	public static Vector iteratorToVector(Iterator iter)
	{
		Vector theVec = new Vector();
		
		if (iter != null)
		{
			while (iter.hasNext())
			{
				theVec.add(iter.next());
			}
		}
		
		return theVec;
	}
	
	public static Vector enumerationToVector(Enumeration enu)
	{
		Vector theVec = new Vector();
		
		if (enu != null)
		{
			while (enu.hasMoreElements())
			{
				theVec.add(enu.nextElement());
			}
		}
		
		return theVec;
	}
		
	// ############## EVENTS ####################
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("connect"))
		{
			connect(hostField.getText(), portField.getText(), usernameField.getText(), passwordField.getText());
		}
		else if (e.getActionCommand().equals("query"))
		{
			this.sql = queryEdit.getText();
			DefaultMutableTreeNode clicked = (DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
			
			if (clicked.getUserObject() instanceof ClassWrapper)
			{
				fillTable(((ClassWrapper)clicked.getUserObject()).getTheClass());
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		
			if (e.getSource() == jTree1)
			{
				int[] selected = jTree1.getSelectionRows();
				if (selected.length > 0)
				{
					//DefaultMutableTreeNode clicked = (DefaultMutableTreeNode)jTree1.getModel().getChild(jTree1.getModel().getRoot(), selected[0]-1);
					DefaultMutableTreeNode clicked = (DefaultMutableTreeNode)jTree1.getSelectionPath().getLastPathComponent();
							
					if (clicked.getUserObject() instanceof ClassWrapper)
					{
						this.sql = "SELECT * FROM "+((ClassWrapper)clicked.getUserObject()).getTheClass().getName();
						queryEdit.setText(this.sql);
						
					}

					if (e.getClickCount() >= 2)
					{
						fillTable(((ClassWrapper)clicked.getUserObject()).getTheClass());
					}
					
				}
			}
			else if (e.getSource() == jTable1)
			{
				if (e.getClickCount() >= 2)
				{
					int index = jTable1.getSelectedRow();
					Object selected = jTable1.getModel().getValueAt(index, 0);
					
					new ObjectInspector(this, selected);
				}
			}
		
	}

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
	
	public void keyTyped(KeyEvent e) 
	{
		if(e.getKeyChar() == KeyEvent.VK_ENTER)
		{ 
			if (e.getSource() == this.filterField)
			{
				this.classFilter = this.filterField.getText();
				writeSettings();
				fillClassTree();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
