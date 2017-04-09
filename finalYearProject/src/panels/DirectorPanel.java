package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import databaseClasses.ConnectionClass;
import databaseClasses.DirectorHandler;
import tableModels.DirectorTableModel;
import userInterfaces.AddDirector;

@SuppressWarnings("serial")
public class DirectorPanel extends JPanel
{
	private JPanel jp1,jp2;
	private JButton btnAdd, btnEdit, btnDelete;
	private JTable jt1;
	private ConnectionClass cc1;
	private DirectorHandler dh1;
	private JScrollPane sp1;
	private ResultSet rs1;
	private DirectorTableModel tm1;
	
	private String ip;
	private String dbName;
	private String dbUser;
	private String dbPass;
			
	public DirectorPanel(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass, Boolean isSeriesDir)
	{
		this.setLayout(new BorderLayout());
		ip = ipIn;
		dbName = dbNameIn;
		dbUser = dbUserIn;
		dbPass = dbPassIn; //required for method outside constructor
		
		dh1 = new DirectorHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
				
		jp1 = new JPanel();
		jp2 = new JPanel();
				
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
				
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				@SuppressWarnings("unused")
				AddDirector d1 = new AddDirector(DirectorPanel.this,ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass,isSeriesDir);
			}
		});
						
		try //create a JTable and load with database values
		{
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			if(isSeriesDir)
			{
				rs1 = dh1.getDirectors();
			}
			else
			{
				rs1 = dh1.getDirectorByEmail(username);
			}
			
			tm1 = new DirectorTableModel(rs1);
			
			if(isSeriesDir)
			{
				rs1 = dh1.getDirectors();
			}
			else
			{
				rs1 = dh1.getDirectorByEmail(username);
			}
			
			tm1.buildModel(rs1);
			jt1 = new JTable(tm1);	
			jt1.getColumnModel().getColumn(0).setPreferredWidth(27);
			jt1.getColumnModel().getColumn(3).setPreferredWidth(35);			
			sp1 = new JScrollPane(jt1);
			sp1.setPreferredSize(new Dimension(1000,200));	
			cc1.closeConnection();			
			jp1.add(sp1);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		btnDelete.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				int selectedDirId = -1;
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
				 //determine the selected rows ID
				 selectedDirId = (int)jt1.getModel().getValueAt(rowIndex, 0);
				 
				 try 
					{
						 int result = JOptionPane.showConfirmDialog(null, "are you sure???",
							        "alert", JOptionPane.OK_CANCEL_OPTION);
						 if(result == 0)
						 {
							//call method to remove the selected row from the database
							dh1.deleteDirector(selectedDirId);
							refreshTable(username,isSeriesDir);
						 
							JOptionPane.showMessageDialog(null, "Director with ID "+ selectedDirId+" successfully deleted from database.");
						 }
					} catch (Exception e) {
						e.printStackTrace();
					}
					//remove the deleted row from the table					
				}
				else
				{
					//nothing
				}								
			}
		}); //end of btnDelete actionListener
		
		btnEdit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				 if (jt1.isEditing()) //to enable recognition of edited cell
						jt1.getCellEditor().stopCellEditing();
				 
				int selectedDirId = -1;
				String newDirFName = "";
				String newDirLName = "";
				String newDirPhone = "";
				String newDirEmail = "";
				Boolean newIsSeriesDir = false;
				Boolean newIsRaceDir = false;
				String newPass = "";
				
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
					 //determine the selected rows ID
					 selectedDirId = (int)jt1.getModel().getValueAt(rowIndex, 0);
					 newDirFName = (String) jt1.getModel().getValueAt(rowIndex, 1);
					 newDirLName = (String) jt1.getModel().getValueAt(rowIndex, 2);
					 newDirPhone = (String) jt1.getModel().getValueAt(rowIndex, 3);
					 newDirEmail = (String) jt1.getModel().getValueAt(rowIndex, 4);
					 newIsSeriesDir = (Boolean) jt1.getModel().getValueAt(rowIndex, 5);
					 newIsRaceDir = (Boolean) jt1.getModel().getValueAt(rowIndex, 6);
					 newPass = (String) jt1.getModel().getValueAt(rowIndex, 7);
					 //System.out.println("row selected = "+rowIndex+" ,id = "+SelectedSplitId+" ,entered value = "+newSplitName);
					 								
					 try 
						{
							//call method to edit the selected row in the database
							dh1.editDirector(selectedDirId,newDirFName, newDirLName,newDirPhone,newDirEmail,newIsSeriesDir,newIsRaceDir,newPass);	
							JOptionPane.showMessageDialog(null, "Director with ID "+ selectedDirId+" successfully edited and saved to database.");
						} catch (Exception e) {
							
							e.printStackTrace();
						}					
				}
				else
				{
					//nothing
				}								
			}
		});//end of btnEdit actionListener
		
		this.add(jp1,BorderLayout.NORTH);
		this.add(jp2, BorderLayout.SOUTH);
	}
			
	//method to refresh table contents after add operations
	public void refreshTable(String username,Boolean isSeriesDir) 
	{
		cc1 = new ConnectionClass(ip,dbName,dbUser,dbPass); //for purpose of closing the connection
		
		try {			
				if(isSeriesDir)
				{
					rs1 = dh1.getDirectors();
				}
				else
				{
					rs1 = dh1.getDirectorByEmail(username);
				}
			
				tm1 = new DirectorTableModel(rs1);

				if(isSeriesDir)
				{
					rs1 = dh1.getDirectors();
				}
				else
				{
					rs1 = dh1.getDirectorByEmail(username);
				}
				tm1.buildModel(rs1);
				
				jt1 = new JTable(tm1);	
				jt1.getColumnModel().getColumn(0).setPreferredWidth(27);
				jt1.getColumnModel().getColumn(3).setPreferredWidth(35);
				sp1.remove(jt1); //remove existing table from scroll pane
				jp1.remove(sp1); //remove existing scroll pane from panel
				
				sp1 = new JScrollPane(jt1);
				sp1.setPreferredSize(new Dimension(1000,200));	
				cc1.closeConnection();			
				jp1.add(sp1);
		}catch(Exception e)
		{
			//nothing
		}		
	} 

}
