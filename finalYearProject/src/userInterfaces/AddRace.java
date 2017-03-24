package userInterfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import databaseClasses.ConnectionClass;
import databaseClasses.DirectorHandler;
import databaseClasses.RaceHandler;
import panels.DirectorPanel;
import panels.RacePanel;

@SuppressWarnings("serial")
public class AddRace extends JFrame
{
	private JLabel lblID,lblName,lblLocation,lblDate,lblMultipleWave,lblNoRecSplits;
	private JTextField txtID,txtName,txtLocation,txtDate,txtNoRecSplits;
	private JCheckBox cbMultipleWave;
	private JButton btnAdd;
	private JPanel p1, p2;
	private RaceHandler rh1;
	private int highestId;
	private ConnectionClass cc1;
	private String newName,newLocation;
	private Date newDate;
	private int newNoRecSplits;
	private Boolean newMultipleWave;
	//private Calendar c1;
	
	
	public AddRace(RacePanel panelIn,String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass,Boolean isSeriesDir)
	{
		rh1 = new RaceHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
		try {
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			highestId = rh1.getHighestRaceId();
			highestId++; //new director ID
			cc1.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(6,2));
		lblID = new JLabel("Race ID: ");
		lblName = new JLabel("Race Name: ");
		lblLocation = new JLabel("Race Location: ");
		lblDate = new JLabel("Race Date: ");
		lblMultipleWave = new JLabel("Multiple Wave ");
		lblNoRecSplits = new JLabel("Number of Recorded Splits: ");
				
		txtID = new JTextField(""+highestId);
		txtID.setEditable(false);
		txtName = new JTextField();
		txtLocation = new JTextField();
		txtDate = new JTextField();		
		cbMultipleWave = new JCheckBox();
		txtNoRecSplits = new JTextField();
		
		///////////////////////////////////////////////////////////////////		
		JDatePicker picker = new JDateComponentFactory().createJDatePicker();		
	   // picker.setTextEditable(true);
		picker.setShowYearButtons(true);		
		picker.getModel().setSelected(true);
		////////////////////////////////////////////////////////////////////////
		
		p1.add(lblID);
		p1.add(txtID);
		p1.add(lblName);
		p1.add(txtName);
		p1.add(lblLocation);
		p1.add(txtLocation);
		p1.add(lblDate);
		p1.add((JComponent)picker);
		p1.add(lblMultipleWave);
		p1.add(cbMultipleWave);
		p1.add(lblNoRecSplits);
		p1.add(txtNoRecSplits);
		
		
		
		p2 = new JPanel();
		btnAdd = new JButton("Add New Race");
		p2.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				newName = txtName.getText();//get the new entered value
				newLocation = txtLocation.getText();//get the new entered value
                ///////////// getting the new date values /////////////////////////////////////////////////////
				Calendar c1 = Calendar.getInstance();
				c1.set(picker.getModel().getYear(), picker.getModel().getMonth(), picker.getModel().getDay());
				newDate = c1.getTime();
				/////////////////////////////////////////////////////////////////////////////////////////////////
				newMultipleWave = cbMultipleWave.isSelected();//get the new entered value				
				newNoRecSplits = Integer.parseInt(txtNoRecSplits.getText());//get the new entered value				
				
				try {
					rh1.addRace(highestId,newName,newLocation,newDate,newMultipleWave,newNoRecSplits);					
					cc1.closeConnection();
					panelIn.refreshTable(username,isSeriesDir); //to update table with new value
					AddRace.this.dispose(); //close the add window
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		this.add(p1,BorderLayout.NORTH);
		this.add(p2,BorderLayout.SOUTH);
		
		this.setTitle("Add New Race");
		//mu1.setSize(500,300);
		this.pack();
		this.setLocationRelativeTo(panelIn);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/*public static void main(String[] args)
	{
		AddSplitType s1 = new AddSplitType();
	}*/
	
	
}

