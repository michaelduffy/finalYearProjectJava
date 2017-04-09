package userInterfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class DbSignIn extends JFrame
{
	private JLabel lblIp,lblName,lblUser,lblPass;
	private JTextField txtIp, txtName,txtUser,txtPass;
	private JButton btnSubmit;
	private JPanel p1,p2,p3;
	private Boolean validDB;
	private ConnectionClass cc1;
	private String ip,name,user,pass;
	
	public DbSignIn()
	{
		lblIp = new JLabel("Enter database server IP address: ");	
		lblName = new JLabel("Enter database name: ");		
		lblUser = new JLabel("Enter database user name: ");		
		lblPass = new JLabel("Enter database user pasword: ");
		txtIp = new JTextField("localhost");
		txtName = new JTextField("project_database");
		txtUser = new JTextField("root");
		txtPass = new JPasswordField();
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(4,2));
		p1.add(lblIp);
		p1.add(txtIp);
		p1.add(lblName);
		p1.add(txtName);
		p1.add(lblUser);
		p1.add(txtUser);
		p1.add(lblPass);
		p1.add(txtPass);
		
		p2 = new JPanel();
		btnSubmit = new JButton("Submit");
		p2.add(btnSubmit);
		
		p3 = new JPanel();
		ImageIcon image = new ImageIcon("image/keyImg3.jpg");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		p3 = new JPanel(new BorderLayout());
		p3.add( label, BorderLayout.CENTER );
		
		
		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				validDB = false;
				ip = txtIp.getText();
				name = txtName.getText();
				user = txtUser.getText();
				pass = txtPass.getText();
				cc1 = new ConnectionClass(ip,name,user,pass);
				try {
					cc1.openConnection();//attempt connection to confirm details
					cc1.closeConnection();
					validDB = true;
					if(validDB)
					{
						@SuppressWarnings("unused")
						UserSignIn us1 = new UserSignIn(ip,name,user,pass);
						DbSignIn.this.dispose();
					}
				} catch (Exception e) {
					validDB = false;
					JOptionPane.showMessageDialog(null, "Incorect credentials entered");
					//e.printStackTrace();
				}
			}
		});
		this.add(p3,BorderLayout.NORTH);
		this.add(p1,BorderLayout.CENTER);
		this.add(p2,BorderLayout.SOUTH);
		
		
		this.setTitle("Database Credentials Sign In");
		//mu1.setSize(500,300);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		DbSignIn mu1 = new DbSignIn();
		

	}
}
