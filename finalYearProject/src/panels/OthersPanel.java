package panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import databaseClasses.ConnectionClass;
import databaseClasses.DirectorHandler;
import databaseClasses.RaceHandler;

@SuppressWarnings("serial")
public class OthersPanel extends JPanel
{
	private JPanel jp1,jp2,jp3;
	private JButton btnAssignDirRace;
	private JLabel lblRaces, lblDirectors;
	private JComboBox<String>cBoxRaces;
	private String[] raceIds;
	private String[] directorIds;
	private JComboBox<String>cBoxDirectors;
	private TitledBorder tb1;
	private RaceHandler rh1;
	private DirectorHandler dh1;
	private ResultSet rs1;
	private ConnectionClass cc1;
	private int counter;
	
	public OthersPanel(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass, Boolean isSeriesDir)
	{
		rh1 = new RaceHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
		dh1 = new DirectorHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
		cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn);//for purpose of closing the connection
		
		jp1 = new JPanel();
		jp1.setLayout(new GridLayout(2,2));
		lblRaces = new JLabel("Choose Race:  ");
		
		// populating arrays with database values for combo boxes //////////////
		try 
		{
			//// race IDs ////////////////////
			rs1 = rh1.getRaceIds();
			//rs1.s
			counter = 0;
			while(rs1.next())
			{
				counter++;
			}
			rs1 = rh1.getRaceIds();
			raceIds = new String[counter];
			counter = 0;
			
			while(rs1.next())
			{				
				raceIds[counter] = rs1.getInt(1)+"";
				counter++;
			}
			////////director IDs /////////////////////////////
			rs1 = dh1.getDirectorIds();
			//rs1.s
		    counter = 0;
			while(rs1.next())
			{
				counter++;
			}
			rs1 = dh1.getDirectorIds();
			directorIds = new String[counter];
			counter = 0;
			
			while(rs1.next())
			{
				directorIds[counter] = rs1.getInt(1)+"";
				counter++;
			}
			cc1.closeConnection();
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cBoxRaces = new JComboBox<String>(raceIds);
		
		lblDirectors = new JLabel("Choose Director:  ");
		cBoxDirectors = new JComboBox<String>(directorIds);
		jp1.add(lblRaces);
		jp1.add(cBoxRaces);
		jp1.add(lblDirectors);
		jp1.add(cBoxDirectors);
		
		jp2 = new JPanel();
		btnAssignDirRace = new JButton("Assign Director to Race");
		jp2.add(btnAssignDirRace);
		
		jp3 = new JPanel();
		tb1 = new TitledBorder("Director to Race");
		jp3.setBorder(tb1);
		jp3.setLayout(new BorderLayout());
		
		jp3.add(jp1, BorderLayout.NORTH);
		jp3.add(jp2, BorderLayout.SOUTH);
		
		this.add(jp3);	
		
		btnAssignDirRace.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{									
				int selectedRaceId = Integer.valueOf((String) cBoxRaces.getSelectedItem());
				int selectedDirectorId = Integer.valueOf((String) cBoxDirectors.getSelectedItem());
				
				try 
				{
					dh1.directorToRace(selectedDirectorId, selectedRaceId);
				} 
				catch (Exception e) 
				{
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Exception occured while attemptimg to add dir to race.");
				}
			}
		});
		
	}
}
