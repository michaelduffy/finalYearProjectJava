package userInterfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import databaseClasses.ConnectionClass;
import databaseClasses.DirectorHandler;
import panels.DirectorPanel;

@SuppressWarnings("serial")
public class AddDirector extends JFrame
{
	private JLabel lblID,lblFname,lblLname,lblPhone,lblEmail,lblSeriesDir,lblRaceDir,lblPass;
	private JTextField txtID,txtFname,txtLname,txtPhone,txtEmail,txtPass;
	private JCheckBox cbSeriesDir,cbRaceDir;
	private JButton btnAdd;
	private JPanel p1, p2;
	private DirectorHandler dh1;
	private int highestId;
	private ConnectionClass cc1;
	private String newFname,newLname,newPhone,newEmail,newPass;
	private Boolean newSeriesDir,newRaceDir;
	
	
	public AddDirector(DirectorPanel panelIn,String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass,Boolean isSeriesDir)
	{
		dh1 = new DirectorHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
		try {
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			highestId = dh1.getHighestDirectorId();
			highestId++; //new director ID
			cc1.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(8,2));
		lblID = new JLabel("Director ID: ");
		lblFname = new JLabel("First Name: ");
		lblLname = new JLabel("Last Name: ");
		lblPhone = new JLabel("Phone number: ");
		lblEmail = new JLabel("Email: ");
		lblSeriesDir = new JLabel("Series Director: ");
		lblRaceDir = new JLabel("Race Director: ");
		lblPass = new JLabel("Password: ");
		
		txtID = new JTextField(""+highestId);
		txtID.setEditable(false);
		txtFname = new JTextField();
		txtLname = new JTextField();
		txtPhone = new JTextField();
		txtEmail = new JTextField();
		cbSeriesDir = new JCheckBox();
		cbRaceDir = new JCheckBox();
		txtPass = new JPasswordField();
		
		p1.add(lblID);
		p1.add(txtID);
		p1.add(lblFname);
		p1.add(txtFname);
		p1.add(lblLname);
		p1.add(txtLname);
		p1.add(lblPhone);
		p1.add(txtPhone);
		p1.add(lblEmail);
		p1.add(txtEmail);
		p1.add(lblSeriesDir);
		p1.add(cbSeriesDir);
		p1.add(lblRaceDir);
		p1.add(cbRaceDir);
		p1.add(lblPass);
		p1.add(txtPass);
		
		
		p2 = new JPanel();
		btnAdd = new JButton("Add New Director");
		p2.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				newFname = txtFname.getText();//get the new entered value
				newLname = txtLname.getText();//get the new entered value
				newPhone = txtPhone.getText();//get the new entered value
				newEmail = txtEmail.getText();//get the new entered value
				newSeriesDir = cbSeriesDir.isSelected();//get the new entered value
				newRaceDir = cbRaceDir.isSelected();//get the new entered value
				newPass = txtPass.getText();//get the new entered value
				
				try {
					dh1.addDirector(highestId,newFname,newLname,newPhone,newEmail,newSeriesDir,newRaceDir,newPass);					
					cc1.closeConnection();
					panelIn.refreshTable(username,isSeriesDir); //to update table with new value
					AddDirector.this.dispose(); //close the add window
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		this.add(p1,BorderLayout.NORTH);
		this.add(p2,BorderLayout.SOUTH);
		
		this.setTitle("Add New Director");
		//mu1.setSize(500,300);
		this.pack();
		this.setLocationRelativeTo(panelIn);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
