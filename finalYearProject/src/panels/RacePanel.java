package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JSpinner.DateEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import databaseClasses.ConnectionClass;
import databaseClasses.DirectorHandler;
import databaseClasses.RaceHandler;
import tableModels.DateEditor;
import tableModels.DirectorTableModel;
import tableModels.RaceTableModel;
import userInterfaces.AddDirector;
import userInterfaces.AddRace;



@SuppressWarnings("serial")
public class RacePanel extends JPanel
{
	private JPanel jp1,jp2;
	private JButton btnAdd, btnEdit, btnDelete;
	private JTable jt1;
	private ConnectionClass cc1;
	private RaceHandler rh1;
	private JScrollPane sp1;
	private ResultSet rs1;
	private RaceTableModel tm1;
	
	private String ip;
	private String dbName;
	private String dbUser;
	private String dbPass;
			
	public RacePanel(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass, Boolean isSeriesDir)
	{
		this.setLayout(new BorderLayout());
		ip = ipIn;
		dbName = dbNameIn;
		dbUser = dbUserIn;
		dbPass = dbPassIn; //required for method outside constructor
		
		rh1 = new RaceHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
				
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		//jp2.setLayout(new GridLayout(3,1));;
		
		btnAdd = new JButton("Add");
		btnEdit = new JButton("Edit");
		btnDelete = new JButton("Delete");
		
		if(!isSeriesDir)
		{		
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
				AddRace d1 = new AddRace(RacePanel.this,ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass,isSeriesDir);
			}
		});
						
		try //create a JTable and load with database values
		{
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			if(isSeriesDir)
			{
				rs1 = rh1.getRaces();
			}
			else
			{
				rs1 = rh1.getRacesByDirEmail(username);
			}
			
			tm1 = new RaceTableModel(rs1);
			
			if(isSeriesDir)
			{
				rs1 = rh1.getRaces();
			}
			else
			{
				rs1 = rh1.getRacesByDirEmail(username);
			}
			
			tm1.buildModel(rs1);
			jt1 = new JTable(tm1);
			////////////////////////////////////
			TableColumn dateColumn = jt1.getColumnModel().getColumn(3);
			DateEditor de1 = new DateEditor();
			dateColumn.setCellEditor(de1);
			//////////////////////////////////////////
			DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
			centreRenderer.setHorizontalAlignment(JLabel.CENTER);
			jt1.getColumnModel().getColumn(0).setCellRenderer(centreRenderer);
			jt1.getColumnModel().getColumn(5).setCellRenderer(centreRenderer);
			////////////////////////////////////////////////////////////////////////
			jt1.getColumnModel().getColumn(0).setPreferredWidth(27);
			jt1.getColumnModel().getColumn(3).setPreferredWidth(35);			
			sp1 = new JScrollPane(jt1);
			sp1.setPreferredSize(new Dimension(1000,200));	
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
				int selectedRaceId = -1;
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
				 //determine the selected rows ID
				 selectedRaceId = (int)jt1.getModel().getValueAt(rowIndex, 0);
				 System.out.println("(racepanel)row selected = "+rowIndex+" ,id = "+selectedRaceId);
				 
				 try 
					{
						 int result = JOptionPane.showConfirmDialog(null, "are you sure???",
							        "alert", JOptionPane.OK_CANCEL_OPTION);
						 System.out.println("result = "+result);
						 if(result == 0)
						 {
							//call method to remove the selected row from the database
							rh1.deleteRace(selectedRaceId);
							//((DefaultTableModel) jt1.getModel()).removeRow(rowIndex); 
						    //jt1.getModel())).removeRow(rowIndex); 
							refreshTable(username,isSeriesDir);
						 
							JOptionPane.showMessageDialog(null, "Race with ID "+ selectedRaceId+" successfully deleted from database.");
						 }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//remove the deleted row from the table					
				}
				else
				{
					System.out.println("(racepanel)No row selected");
					System.out.println("(racepanel)row selected = "+rowIndex+" ,id = "+selectedRaceId);
				}								
			}
		}); //end of btnDelete actionListener
		
		btnEdit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				 if (jt1.isEditing()) //to enable recognition of edited cell
						jt1.getCellEditor().stopCellEditing();
				 
				int selectedRaceId = -1;
				String newName = "";
				String newLocation = "";
				Date newDate = null;
				Boolean newMultiWave = false;
				int noRecSplits = 0;
				
				
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
					 //determine the selected rows ID
					selectedRaceId = (int)jt1.getModel().getValueAt(rowIndex, 0);
					newName = (String) jt1.getModel().getValueAt(rowIndex, 1);
					newLocation = (String) jt1.getModel().getValueAt(rowIndex, 2);
					newDate = (Date) jt1.getModel().getValueAt(rowIndex, 3);
					newMultiWave = (Boolean) jt1.getModel().getValueAt(rowIndex, 4);
					noRecSplits = (int) jt1.getModel().getValueAt(rowIndex, 5);
					
					 //System.out.println("row selected = "+rowIndex+" ,id = "+SelectedSplitId+" ,entered value = "+newSplitName);
					 								
					 try 
						{
							//call method to edit the selected row in the database
							rh1.editRace(selectedRaceId,newName, newLocation,newDate,newMultiWave,noRecSplits);	
							JOptionPane.showMessageDialog(null, "Race with ID "+ selectedRaceId+" successfully edited and saved to database.");
						} catch (Exception e) {
							
							e.printStackTrace();
						}					
				}
				else
				{
					System.out.println("(racepanel)No row selected");
					System.out.println("(racepanel)row selected = "+rowIndex+" ,id = "+selectedRaceId+" ,entered value = "+newName);
				}								
			}
		});//end of btnEdit actionListener
		
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2, BorderLayout.SOUTH);
	}
			
	//method to refresh table contents after add operations
	public void refreshTable(String username,Boolean isSeriesDir) 
	{
		System.out.println("test refresh 1");
		cc1 = new ConnectionClass(ip,dbName,dbUser,dbPass); //for purpose of closing the connection
		
		try {			
				if(isSeriesDir)
				{
					rs1 = rh1.getRaces();
					//System.out.println("test refresh 2");
				}
				else
				{
					rs1 = rh1.getRacesByDirEmail(username);
					//System.out.println("test refresh 3");
				}
			
				tm1 = new RaceTableModel(rs1);

				if(isSeriesDir)
				{
					rs1 = rh1.getRaces();
					//System.out.println("test refresh 4");
				}
				else
				{
					rs1 = rh1.getRacesByDirEmail(username);
					//System.out.println("test refresh 5");
				}
				tm1.buildModel(rs1);
				
				jt1 = new JTable(tm1);
				/////////////////////////////////////////////////////////
				DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
				centreRenderer.setHorizontalAlignment(JLabel.CENTER);
				jt1.getColumnModel().getColumn(0).setCellRenderer(centreRenderer);
				jt1.getColumnModel().getColumn(5).setCellRenderer(centreRenderer);
				////////////////////////////////////////////////////////////////////////
				jt1.getColumnModel().getColumn(0).setPreferredWidth(27);
				jt1.getColumnModel().getColumn(3).setPreferredWidth(35);
				sp1.remove(jt1); //remove existing table from scroll pane
				jp1.remove(sp1); //remove existing scroll pane from panel
				
				sp1 = new JScrollPane(jt1);
				sp1.setPreferredSize(new Dimension(1000,200));	
				cc1.closeConnection();			
				jp1.add(sp1);
				System.out.println("test refresh 5");
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	} 
	
	/*public void testMethod()
	{
		System.out.println("test working"+jt1.getModel().getValueAt(1, 1));
	}*/
	
	
}
