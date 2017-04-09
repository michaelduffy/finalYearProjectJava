package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import databaseClasses.AthleteHandler;
import databaseClasses.ConnectionClass;
import tableModels.DateEditor;
import tableModels.AthleteTableModel;

@SuppressWarnings("serial")
public class AthletePanel extends JPanel
{	
	private JPanel jp1,jp2;
	private JButton btnEdit, btnDelete;
	private JTable jt1;
	private ConnectionClass cc1;
	private AthleteHandler ah1;
	private JScrollPane sp1;
	private ResultSet rs1;
	private AthleteTableModel tm1;
	
	private String ip;
	private String dbName;
	private String dbUser;
	private String dbPass;
			
	public AthletePanel(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass, Boolean isSeriesDir)
	{
		this.setLayout(new BorderLayout());
		ip = ipIn;
		dbName = dbNameIn;
		dbUser = dbUserIn;
		dbPass = dbPassIn; //required for method outside constructor
		
		ah1 = new AthleteHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
				
		jp1 = new JPanel();
		jp2 = new JPanel();
				
		btnEdit = new JButton("Edit");
		btnDelete = new JButton("Delete");
		
		if(!isSeriesDir)
		{		
			btnDelete.setEnabled(false);
		}
						
		jp2.add(btnEdit);
		jp2.add(btnDelete);
												
		try //create a JTable and load with database values
		{
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
						
			rs1 = ah1.getAthletes();
			tm1 = new AthleteTableModel(rs1);							
			rs1 = ah1.getAthletes();			
			tm1.buildModel(rs1);
			jt1 = new JTable(tm1);
			////////////////////////////////////
			TableColumn dateColumn = jt1.getColumnModel().getColumn(8);
			DateEditor de1 = new DateEditor();
			dateColumn.setCellEditor(de1);
			//////////////////////////////////////////
			DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
			centreRenderer.setHorizontalAlignment(JLabel.CENTER);
			jt1.getColumnModel().getColumn(0).setCellRenderer(centreRenderer);
			jt1.getColumnModel().getColumn(7).setCellRenderer(centreRenderer);
			//////////////////////////////////////////////////////
			jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
			jt1.getColumnModel().getColumn(3).setPreferredWidth(80);
			jt1.getColumnModel().getColumn(4).setPreferredWidth(80);
			jt1.getColumnModel().getColumn(5).setPreferredWidth(80);
			jt1.getColumnModel().getColumn(6).setPreferredWidth(40);
			jt1.getColumnModel().getColumn(7).setPreferredWidth(40);
			
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
				int selectedAthId = -1;
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
				 //determine the selected rows ID
				 selectedAthId = (int)jt1.getModel().getValueAt(rowIndex, 0);
				 
				 try 
					{
						 int result = JOptionPane.showConfirmDialog(null, "are you sure???",
							        "alert", JOptionPane.OK_CANCEL_OPTION);
						 if(result == 0)
						 {
							//call method to remove the selected row from the database
							ah1.deleteAthlete(selectedAthId);							
							refreshTable(username,isSeriesDir);
						 
							JOptionPane.showMessageDialog(null, "Athlete with ID "+ selectedAthId+" successfully deleted from database.");
						 }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}								
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
				 
				int selectedAthId = -1;
				String newFname = "";
				String newLname = "";
				String newAdd1 = "";
				String newAdd2 = "";
				String newAdd3 = "";
				String newCounty = "";
				String newGender;
				Date newDob = null;
				String newPhone = "";
				String newEmail = "";
				String newPass = "";
				
				//get the selected row index
				int rowIndex = jt1.getSelectedRow();
				
				if(rowIndex != -1)//if a row has been selected
				{
					 //determine the selected rows ID
					selectedAthId = (int)jt1.getModel().getValueAt(rowIndex, 0);
					newFname = (String) jt1.getModel().getValueAt(rowIndex, 1);
					newLname = (String) jt1.getModel().getValueAt(rowIndex, 2);
					newAdd1 = (String) jt1.getModel().getValueAt(rowIndex, 3);
					newAdd2 = (String) jt1.getModel().getValueAt(rowIndex, 4);
					newAdd3 = (String) jt1.getModel().getValueAt(rowIndex, 5);
					newCounty = (String) jt1.getModel().getValueAt(rowIndex, 6);
					newGender = (String)jt1.getModel().getValueAt(rowIndex, 7);
					newDob = (Date) jt1.getModel().getValueAt(rowIndex, 8);
					newPhone = (String) jt1.getModel().getValueAt(rowIndex, 9);
					newEmail = (String) jt1.getModel().getValueAt(rowIndex, 10);
					newPass = (String) jt1.getModel().getValueAt(rowIndex, 11);
														 								
					 try 
						{
							//call method to edit the selected row in the database
							ah1.editAthlete(selectedAthId,newFname,newLname,newAdd1,newAdd2,newAdd3,newCounty,newGender,newDob,newPhone,newEmail,newPass);	
							refreshTable(username,isSeriesDir);
							JOptionPane.showMessageDialog(null, "Athlete with ID "+ selectedAthId+" successfully edited and saved to database.");
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
				rs1 = ah1.getAthletes();
				tm1 = new AthleteTableModel(rs1);				
				rs1 = ah1.getAthletes();
				tm1.buildModel(rs1);				
				jt1 = new JTable(tm1);
				////////////////////////////////////
				TableColumn dateColumn = jt1.getColumnModel().getColumn(8);
				DateEditor de1 = new DateEditor();
				dateColumn.setCellEditor(de1);
				//////////////////////////////////////////
				DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
				centreRenderer.setHorizontalAlignment(JLabel.CENTER);
				jt1.getColumnModel().getColumn(0).setCellRenderer(centreRenderer);
				jt1.getColumnModel().getColumn(7).setCellRenderer(centreRenderer);
				//////////////////////////////////////////////////////////////////////////
				jt1.getColumnModel().getColumn(0).setPreferredWidth(50);
				jt1.getColumnModel().getColumn(3).setPreferredWidth(80);
				jt1.getColumnModel().getColumn(4).setPreferredWidth(80);
				jt1.getColumnModel().getColumn(5).setPreferredWidth(80);
				jt1.getColumnModel().getColumn(6).setPreferredWidth(40);
				jt1.getColumnModel().getColumn(7).setPreferredWidth(40);
				
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

