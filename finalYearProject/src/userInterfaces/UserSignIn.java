package userInterfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import databaseClasses.ConnectionClass;

@SuppressWarnings("serial")
public class UserSignIn extends JFrame
{
	private String email, pass;	
	private JLabel lblEmail,lblPass;
	private JTextField txtEmail,txtPass;
	private JButton btnSubmit;
	private JPanel p1,p2,p3;
	private Boolean validUser;
	private ConnectionClass cc1;
	private Connection conn;
	private ResultSet rs1;
	private Boolean isSeriesDir,isRaceDir;
	
	private PreparedStatement getAllDirectorNameAndPass = null;	
	private String getAllDirectorNameAndPassString;
	
	
	
	public UserSignIn(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn)
	{
		lblEmail = new JLabel("Enter email: ");
		lblPass = new JLabel("Enter User Password: ");
		
		txtEmail = new JTextField("mictriduf@hotmail.co.uk");
		txtPass = new JPasswordField("password");
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2,2));
		p1.add(lblEmail);
		p1.add(txtEmail);
		p1.add(lblPass);
		p1.add(txtPass);
		
		btnSubmit = new JButton("Submit");
		p2= new JPanel();
		p2.add(btnSubmit);
		
		p3 = new JPanel();
		ImageIcon image = new ImageIcon("image/keyImg4.jpg");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		p3 = new JPanel(new BorderLayout());
		p3.add( label, BorderLayout.CENTER );
		
		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				validUser = false;
				//ip = txtIp.getText();
				email = txtEmail.getText();
				pass = txtPass.getText();
				
				cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn);
				try {
					//cc1.openConnection();//attempt connection to confirm details
					//cc1.closeConnection();
					getAllDirectorNameAndPassString ="select email, password, is_series_director, is_race_director from director";
					//cc1 = new ConnectionClass();
					conn = cc1.openConnection();
					getAllDirectorNameAndPass = conn.prepareStatement(getAllDirectorNameAndPassString);
					rs1 = getAllDirectorNameAndPass.executeQuery();
					
					while(rs1.next())
				    {
				    	if( email.equals(rs1.getString(1))&&pass.equals(rs1.getString(2)) ) 
				    	{				    		
				    		validUser = true;
				    		isSeriesDir = rs1.getBoolean(3);
				    		isRaceDir = rs1.getBoolean(4);
				    		System.out.println("email = "+email);
				    		System.out.println("password = "+pass);
				    		System.out.println("series dir = = "+isSeriesDir);
				    		System.out.println("race dir = = "+isRaceDir);
				    		break;
				    	}
				    }
					
					if(validUser == true)
					{
						@SuppressWarnings("unused")
						MainUI mainU1 = new MainUI(ipIn,dbNameIn,dbUserIn,dbPassIn,email,pass,isSeriesDir,isRaceDir);
						UserSignIn.this.dispose();
					}
			    	else
			    		JOptionPane.showMessageDialog(null, "Incorect credentials entered");
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//JOptionPane.showMessageDialog(null, "Error!!!! encountered!!");
				}
			}
		});
		this.add(p3,BorderLayout.NORTH);
		this.add(p1,BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);
		
		this.setTitle("User Credentials Sign In");
		//mu1.setSize(500,300);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/*public static void main(String[] args)
	{
		UserSignIn mu1 = new UserSignIn("","","","");
		

	}*/
}
