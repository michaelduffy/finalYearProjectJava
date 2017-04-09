package userInterfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import databaseClasses.ConnectionClass;
import databaseClasses.SplitTypeHandler;
import panels.SplitTypePanel;

@SuppressWarnings("serial")
public class AddSplitType extends JFrame
{
	private JLabel lblID, lblName;
	private JTextField txtID, txtName;
	private JButton btnAdd;
	private JPanel p1, p2;
	private SplitTypeHandler sth1;
	private int highestId;
	private ConnectionClass cc1;
	private String newSplitTypeName;
	
	
	public AddSplitType(SplitTypePanel panelIn,String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass)
	{
		sth1 = new SplitTypeHandler(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass);
		try {
			cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn); //for purpose of closing the connection
			highestId = sth1.getHighestSplitTypeId();
			highestId++; //new splitType ID
			cc1.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2,2));
		lblID = new JLabel("Split Type ID: ");
		lblName = new JLabel("Split Type Name: ");
		txtID = new JTextField(""+highestId);
		txtID.setEditable(false);
		txtName = new JTextField();
		
		p1.add(lblID);
		p1.add(txtID);
		p1.add(lblName);
		p1.add(txtName);
		
		p2 = new JPanel();
		btnAdd = new JButton("Add New Split Type");
		p2.add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				newSplitTypeName = txtName.getText();//get the new entered split type name
				try {
					sth1.addSplitType(highestId, newSplitTypeName);					
					cc1.closeConnection();
					panelIn.refreshTable(); //to update table with new value
					AddSplitType.this.dispose(); //close the add window
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		this.add(p1,BorderLayout.NORTH);
		this.add(p2,BorderLayout.SOUTH);
		
		this.setTitle("Add New Split Type");
		//mu1.setSize(500,300);
		this.pack();
		this.setLocationRelativeTo(panelIn);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
		
}
