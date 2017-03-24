package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import databaseClasses.ConnectionClass;
import databaseClasses.SplitTypeHandler;
import tableModels.SplitTypeTableModel;
import userInterfaces.AddSplitType;


@SuppressWarnings("serial")
public class SplitTypePanel extends JPanel
{
	private JPanel jp1,jp2;
	private JButton btnAdd, btnEdit, btnDelete;
	private JTable jt1;
	private ConnectionClass cc1;
	private SplitTypeHandler sth1;
	private JScrollPane sp1;
	private ResultSet rs1;
	//private DefaultTableModel model1;
	private SplitTypeTableModel tm1;
	
	private String ip;
	private String dbName;
	private String dbUser;
	private String dbPass;
			
	public SplitTypePanel(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass, Boolean isSeriesDir)
	{
		this.setLayout(new BorderLayout());
		ip = ipIn;
		dbName = dbNameIn;
		dbUser = dbUserIn;
		dbPass = dbPassIn;
		
		sth1 = new SplitTypeHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
				
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		//jp2.setLayout(new GridLayout(3,1));;
		
		btnAdd = new JButton("Add");
		btnEdit = new JButton("Edit");
		btnDelete = new JButton("Delete");
		
		if(!isSeriesDir)
		{
			btnEdit.setEnabled(false);
			btnDelete.setEnabled(false);
		}
				
		jp2.add(btnAdd);
		jp2.add(btnEdit);
		jp2.add(btnDelete);
		
		/*	btnRefresh.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				
			}
		});*/
		
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				@SuppressWarnings("unused")
				AddSplitType s1 = new AddSplitType(SplitTypePanel.this,ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
			}
		});
						
		try //create a JTable and load with database values
		{
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			
			rs1 = sth1.getSplitTypes();
			tm1 = new SplitTypeTableModel(rs1);
			rs1 = sth1.getSplitTypes();
			tm1.buildModel(rs1);
			jt1 = new JTable(tm1);	
			/////////////////////////////////////////////////////////////////////////
			DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
			centreRenderer.setHorizontalAlignment(JLabel.CENTER);
			jt1.getColumnModel().getColumn(0).setCellRenderer(centreRenderer);			
			/////////////////////////////////////////////////////////////////////////
			sp1 = new JScrollPane(jt1);
			sp1.setPreferredSize(new Dimension(500,200));	
			cc1.closeConnection();			
			jp1.add(sp1);
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		btnDelete.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				int SelectedSplitId = -1;
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
				 //determine the selected rows ID
				 SelectedSplitId = (int)jt1.getModel().getValueAt(rowIndex, 0);
				 System.out.println("row selected = "+rowIndex+" ,id = "+SelectedSplitId);
				 
				 try 
					{
						 int result = JOptionPane.showConfirmDialog(null, "are you sure???",
							        "alert", JOptionPane.OK_CANCEL_OPTION);
						 System.out.println("result = "+result);
						 if(result == 0)
						 {
							//call method to remove the selected row from the database
							sth1.deleteSplit(SelectedSplitId);
							refreshTable();
							//((DefaultTableModel) jt1.getModel()).removeRow(rowIndex); 
						 
							JOptionPane.showMessageDialog(null, "Split Type with ID "+ SelectedSplitId+" successfully deleted from database.");
						 }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//remove the deleted row from the table
					
				}
				else
				{
					System.out.println("No row selected");
					System.out.println("row selected = "+rowIndex+" ,id = "+SelectedSplitId);
				}
				
				

			}
		}); //end of btnDelete actionListener
		
		btnEdit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				 if (jt1.isEditing()) //to enable recognition of edited cell
						jt1.getCellEditor().stopCellEditing();
				 
				int SelectedSplitId = -1;
				String newSplitName = "";
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
					 //determine the selected rows ID
					 SelectedSplitId = (int)jt1.getModel().getValueAt(rowIndex, 0);
					 								 
					 newSplitName = (String) jt1.getModel().getValueAt(rowIndex, 1);
					 //System.out.println("row selected = "+rowIndex+" ,id = "+SelectedSplitId+" ,entered value = "+newSplitName);
					 								
					 try 
						{
							//call method to edit the selected row in the database
							sth1.editSplitType(SelectedSplitId, newSplitName);	
							JOptionPane.showMessageDialog(null, "Split Type with ID "+ SelectedSplitId+" successfully edited and saved to database.");
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					
				}
				else
				{
					System.out.println("No row selected");
					System.out.println("row selected = "+rowIndex+" ,id = "+SelectedSplitId+" ,entered value = "+newSplitName);
				}
				
				
			}
		});//end of btnEdit actionListener
		
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2, BorderLayout.SOUTH);
	}
	
	//method to build table model from a database ResultSet object
/*	public static DefaultTableModel buildTableModel(ResultSet rs)  throws SQLException 
	{

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) 
	    {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) 
	    {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	        {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    return new DefaultTableModel(data, columnNames);
	}*/
	
	//method to refresh table contents after add operations
	public void refreshTable() 
	{
		cc1 = new ConnectionClass(ip,dbName,dbUser,dbPass); //for purpose of closing the connection
		
		try {
			rs1 = sth1.getSplitTypes(); //get new result set
		    tm1 = new SplitTypeTableModel(rs1); //create table model from result set
		    rs1 = sth1.getSplitTypes(); //get new result set
		    tm1.buildModel(rs1);
			jt1 = new JTable(tm1);	//build new table from model
			/////////////////////////////////////////////////////////////////////////
			DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
			centreRenderer.setHorizontalAlignment(JLabel.CENTER);
			jt1.getColumnModel().getColumn(0).setCellRenderer(centreRenderer);			
			/////////////////////////////////////////////////////////////////////////
			sp1.remove(jt1); //remove existing table from scroll pane
			jp1.remove(sp1); //remove existing scroll pane from panel
			sp1 = new JScrollPane(jt1); //create new scroll pane and add table to it
			sp1.setPreferredSize(new Dimension(500,200));	
			cc1.closeConnection();			
			jp1.add(sp1); 
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		//((AbstractTableModel) jt1.getModel()).fireTableDataChanged();
	}
	
	/*public void testMethod()
	{
		System.out.println("test working"+jt1.getModel().getValueAt(1, 1));
	}*/
	
	
}
