package panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JSpinner.DateEditor;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import databaseClasses.ConnectionClass;
import databaseClasses.DirectorHandler;
import databaseClasses.RaceHandler;
import databaseClasses.RaceResultHandler;
import tableModels.DateEditor;
import tableModels.DirectorTableModel;
import tableModels.RaceResultTableModel;
import tableModels.RaceTableModel;
import userInterfaces.AddDirector;
import userInterfaces.AddRace;



@SuppressWarnings("serial")
public class RaceResultPanel extends JPanel
{
	private JPanel jp1,jp2,jp3;
	private JButton btnAddOveralls,btnAddSplits,btnDisplay, btnDelete,btnCalcPoints;
	private JLabel lblSearch,lblSpace;
	private JComboBox<String>cBoxRaces;
	private JTable jt1;
	private ConnectionClass cc1;
	private RaceResultHandler rrh1;
	private JScrollPane sp1;
	private ResultSet rs1;
	private RaceResultTableModel tm1;
	private int firstRaceId,displayedRaceId;
	private int selectedRaceId;
	private int selectedComboIndex;
	private int noCols;
	private String ip;
	private String dbName;
	private String dbUser;
	private String dbPass;
			
	public RaceResultPanel(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass, Boolean isSeriesDir)
	{
		this.setLayout(new BorderLayout());
		ip = ipIn;
		dbName = dbNameIn;
		dbUser = dbUserIn;
		dbPass = dbPassIn; //required for method outside constructor
		
		rrh1 = new RaceResultHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
				
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		
		//jp2.setLayout(new GridLayout(3,1));;
		
		btnAddOveralls = new JButton("Add Overall Result");
		btnAddSplits = new JButton("Add Splits");
		//btnEdit = new JButton("Edit");
		btnDelete = new JButton("Delete Result");
		
		lblSearch = new JLabel("Select race to display: ");
		cBoxRaces = new JComboBox<String>();
		
		//loading the combo box and getting first race in list id
		try { 
			if(isSeriesDir)
			{
				rs1 = rrh1.getRaceNames();
				while(rs1.first())
				{
					firstRaceId = rs1.getInt(2);
					displayedRaceId = firstRaceId;
					System.out.println("(RaceresultPanel)displayedRaceId SeriesDir = "+displayedRaceId);
					//System.out.println("firstRaceId = "+firstRaceId);
					break;
				}
				rs1 = rrh1.getRaceNames();
			}
			else
			{
				rs1 = rrh1.getRaceNamesByDirEmail(username);
				while(rs1.first())
				{
					firstRaceId = rs1.getInt(2);
					displayedRaceId = firstRaceId;
					System.out.println("(RaceresultPanel)displayedRaceId RaceDir= "+displayedRaceId);
					//System.out.println("firstRaceId = "+firstRaceId);
					break;
				}
				rs1 = rrh1.getRaceNamesByDirEmail(username);
			}
			while(rs1.next())
			{
				cBoxRaces.addItem(rs1.getString(1));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnDisplay = new JButton("Display Results");
		
		lblSpace = new JLabel("                                    ");
		btnCalcPoints = new JButton("Calculate Race Points");
		
		
		if(!isSeriesDir)
		{		
			btnDelete.setEnabled(false);
		}
				
		jp2.add(btnAddOveralls);
		jp2.add(btnAddSplits);
		//jp2.add(btnEdit);
		jp2.add(btnDelete);
		jp3.add(lblSearch);
		jp3.add(cBoxRaces);
		jp3.add(btnDisplay);
		jp3.add(lblSpace);
		jp3.add(btnCalcPoints);
		
		/*	btnRefresh.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				
			}
		});*/
		
		btnDelete.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				try
				{
					 int result = JOptionPane.showConfirmDialog(null, "are you sure???",
						        "alert", JOptionPane.OK_CANCEL_OPTION);
					 System.out.println("result = "+result);
					 if(result == 0)
					 {
						cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); 
						rrh1.deleteOverallRaceResult(displayedRaceId);
						rrh1.deleteRaceSplits(displayedRaceId);
						/////////////////////////////////refresh table////////////////////////////////////////////////////////////////////////
						rs1 = rrh1.getResults(selectedRaceId);
						tm1 = new RaceResultTableModel(rs1);
						rs1 = rrh1.getResults(selectedRaceId);
						tm1.buildModel(rs1);
						
						jt1 = new JTable(tm1);
						/////////////////////////////////////////////////////////
						DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
						centreRenderer.setHorizontalAlignment(JLabel.CENTER);
						noCols = jt1.getColumnModel().getColumnCount();
						for(int i=0;i<noCols;i++)
						{
							jt1.getColumnModel().getColumn(i).setCellRenderer(centreRenderer);
						}
						////////////////////////////////////////////////////////////////////////
						jt1.getColumnModel().getColumn(0).setPreferredWidth(27);
						jt1.getColumnModel().getColumn(3).setPreferredWidth(35);
						sp1.remove(jt1); //remove existing table from scroll pane
						jp1.remove(sp1); //remove existing scroll pane from panel
						
						sp1 = new JScrollPane(jt1);
						sp1.setPreferredSize(new Dimension(1000,200));	
						cc1.closeConnection();			
						jp1.add(sp1);
					 }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btnCalcPoints.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				try
				{
					cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); 
					//first get the highest race position
					int highestPosition = rrh1.getHighestRacePosition(displayedRaceId);
					System.out.println("(RaceresultPanel)highestPosition = "+highestPosition);
					
					//next get the thirtieth percentile position
					int thirtyPercentilePosition = highestPosition/3;
					System.out.println("(RaceresultPanel)thirtyPercentilePosition = "+thirtyPercentilePosition);
					
					int thirtyPercentileTime = rrh1.getAthRaceTimeInSecs(displayedRaceId, thirtyPercentilePosition);
					System.out.println("(RaceresultPanel)thirtyPercentileTime = "+thirtyPercentileTime);
					
					rs1 = rrh1.getAllAthleteOverallTime(displayedRaceId);//get all athlete overall race times for displayed race
					while(rs1.next()) //for each athlete in the result set
					{
						int athRaceNo = rs1.getInt(1); //get athlete race number
						int overallTime = rs1.getInt(2); //get athlete overall time
						double racePoints = (thirtyPercentileTime*100.0)/overallTime; //calculate athlete race points
						rrh1.editAthletePoints(displayedRaceId, athRaceNo, racePoints); //assign points to athlete
					}
					
					/////////////////////////////////refresh table////////////////////////////////////////////////////////////////////////
					rs1 = rrh1.getResults(selectedRaceId);
					tm1 = new RaceResultTableModel(rs1);
					rs1 = rrh1.getResults(selectedRaceId);
					tm1.buildModel(rs1);
					
					jt1 = new JTable(tm1);
					/////////////////////////////////////////////////////////
					DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
					centreRenderer.setHorizontalAlignment(JLabel.CENTER);
					noCols = jt1.getColumnModel().getColumnCount();
					for(int i=0;i<noCols;i++)
					{
						jt1.getColumnModel().getColumn(i).setCellRenderer(centreRenderer);
					}
					////////////////////////////////////////////////////////////////////////
					jt1.getColumnModel().getColumn(0).setPreferredWidth(27);
					jt1.getColumnModel().getColumn(3).setPreferredWidth(35);
					sp1.remove(jt1); //remove existing table from scroll pane
					jp1.remove(sp1); //remove existing scroll pane from panel
					
					sp1 = new JScrollPane(jt1);
					sp1.setPreferredSize(new Dimension(1000,200));	
					cc1.closeConnection();			
					jp1.add(sp1);
					
					//cc1.closeConnection();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
		btnDisplay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); 
				selectedComboIndex = cBoxRaces.getSelectedIndex();
				System.out.println("(RaceresultPanel)selectedComboIndex = "+selectedComboIndex);
				if(selectedComboIndex != -1)
				{
					try
					{
						if(isSeriesDir)
						{
							rs1 = rrh1.getRaceNames();
						}
						else
						{
							rs1 = rrh1.getRaceNamesByDirEmail(username);
						}
						int loopCount = 0;
						while(rs1.next())
						{
							selectedRaceId = rs1.getInt(2); //get the selected race id
							displayedRaceId = selectedRaceId;
							
							
							if(loopCount == selectedComboIndex)
							{
								System.out.println("(RaceresultPanel)selectedRaceId = "+selectedRaceId);
								System.out.println("(RaceresultPanel)displayedRaceId = "+displayedRaceId);
								break;							
							}
							loopCount++;
						}
						
						/////////////////refresh table ///////////////////////////////////////////////////////////////
						rs1 = rrh1.getResults(selectedRaceId);
						tm1 = new RaceResultTableModel(rs1);
						rs1 = rrh1.getResults(selectedRaceId);
						tm1.buildModel(rs1);
						
						jt1 = new JTable(tm1);
						/////////////////////////////////////////////////////////
						DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
						centreRenderer.setHorizontalAlignment(JLabel.CENTER);
						noCols = jt1.getColumnModel().getColumnCount();
						for(int i=0;i<noCols;i++)
						{
							jt1.getColumnModel().getColumn(i).setCellRenderer(centreRenderer);
						}
						
						////////////////////////////////////////////////////////////////////////
						jt1.getColumnModel().getColumn(0).setPreferredWidth(27);
						jt1.getColumnModel().getColumn(3).setPreferredWidth(35);
						sp1.remove(jt1); //remove existing table from scroll pane
						jp1.remove(sp1); //remove existing scroll pane from panel
						
						sp1 = new JScrollPane(jt1);
						sp1.setPreferredSize(new Dimension(1000,200));	
						cc1.closeConnection();			
						jp1.add(sp1);
						//System.out.println("test refresh 5");
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		btnAddOveralls.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				String filePath = "";
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			    		"CSV files", "csv");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(RaceResultPanel.this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) //if user chooses file
			    {
			      
					filePath = chooser.getSelectedFile().getPath();				
			      // filePath.replaceAll("\\","/");
					filePath = filePath.replace("\\", "/");//to change path dividers from backslashes to forward slashes
			       	
			      // System.out.println("You chose to open this file: " +chooser.getSelectedFile());
			      // System.out.println("path ="+filePath);
			    }
			    try
			    {
			    	cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			    	rrh1.loadOverallResultsFromCSV(filePath);
			    	cc1.closeConnection();
			    	JOptionPane.showMessageDialog(null, "Race overall result successfully added to database.");
			    	
			    }
			    catch(Exception e)
			    {
			    	JOptionPane.showMessageDialog(null, "Exception occured while addidng race overall result to database.");
			    	e.printStackTrace();
			    }
			}
		});
		
		btnAddSplits.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				String filePath = "";
				JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			    		"CSV files", "csv");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(RaceResultPanel.this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) //if user chooses file
			    {
			      
					filePath = chooser.getSelectedFile().getPath();				
			      // filePath.replaceAll("\\","/");
					filePath = filePath.replace("\\", "/");//to change path dividers from backslashes to forward slashes
			       	
			      // System.out.println("You chose to open this file: " +chooser.getSelectedFile());
			      // System.out.println("path ="+filePath);
			    }
			    try
			    {
			    	cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			    	rrh1.loadSplitsFromCSV(filePath);
			    	cc1.closeConnection();
			    	JOptionPane.showMessageDialog(null, "Race split results successfully added to database.");
			    	
			    }
			    catch(Exception e)
			    {
			    	JOptionPane.showMessageDialog(null, "Exception occured while adding race split results to database.");
			    	e.printStackTrace();
			    }
			}
		});
						
		try //create a JTable and load with database values
		{
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			//System.out.println("name displayed in combo = "+cBoxRaces.getItemAt(0));
			
			if(isSeriesDir)
			{				
				rs1 = rrh1.getResults(firstRaceId);
			}
			else
			{
				rs1 = rrh1.getResults(firstRaceId);
			}
			
			tm1 = new RaceResultTableModel(rs1);
			
			if(isSeriesDir)
			{
				rs1 = rrh1.getResults(firstRaceId);
			}
			else
			{
				rs1 = rrh1.getResults(firstRaceId);
			}
			
			tm1.buildModel(rs1);
			jt1 = new JTable(tm1);
			////////////////////////////////////
			/*TableColumn dateColumn = jt1.getColumnModel().getColumn(3);
			DateEditor de1 = new DateEditor();
			dateColumn.setCellEditor(de1);*/
			//////////////////////////////////////////
			DefaultTableCellRenderer centreRenderer = new DefaultTableCellRenderer();
			centreRenderer.setHorizontalAlignment(JLabel.CENTER);
			noCols = jt1.getColumnModel().getColumnCount();
			for(int i=0;i<noCols;i++)
			{
				jt1.getColumnModel().getColumn(i).setCellRenderer(centreRenderer);
			}
			
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
		
		/*btnDelete.addActionListener(new ActionListener() 
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
		}); //end of btnDelete actionListener */
		
	
		
		this.add(jp1,BorderLayout.CENTER);
		this.add(jp3, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.SOUTH);
	}
			
	//method to refresh table contents after add operations
	/*public void refreshTable(String username,Boolean isSeriesDir) 
	{
		System.out.println("test refresh 1");
		cc1 = new ConnectionClass(ip,dbName,dbUser,dbPass); //for purpose of closing the connection
		
		try {			
				if(isSeriesDir)
				{
					rs1 = rrh1.getResults(1);
					//System.out.println("test refresh 2");
				}
				else
				{
					rs1 = rrh1.getRacesByDirEmail(username);
					//System.out.println("test refresh 3");
				}
			
				tm1 = new RaceResultTableModel(rs1);

				if(isSeriesDir)
				{
					rs1 = rrh1.getResults(1);
					//System.out.println("test refresh 4");
				}
				else
				{
					rs1 = rrh1.getRacesByDirEmail(username);
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
	} */
	
	public void fillTable()
	{
		
	}
	
	/*public void testMethod()
	{
		System.out.println("test working"+jt1.getModel().getValueAt(1, 1));
	}*/
	
	
}
