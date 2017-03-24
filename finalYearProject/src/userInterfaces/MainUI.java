package userInterfaces;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import panels.AthletePanel;
import panels.DirectorPanel;
import panels.RacePanel;
import panels.RaceResultPanel;
import panels.SplitTypePanel;

@SuppressWarnings("serial")
public class MainUI extends JFrame
{
	private JTabbedPane applicationTabs;
	private SplitTypePanel stp1;
	private DirectorPanel dp1;
	private RacePanel rp1;
	private AthletePanel ap1;
	private RaceResultPanel rrp1;
	private JLabel lblUserName;
	private JButton btnLogout;
	private JPanel p1;
		
	public MainUI(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass, boolean isSeriesDir, boolean isRaceDir)
	{
		lblUserName = new JLabel("Current Application User: "+username);		
		btnLogout = new JButton("Logout");
		applicationTabs = new JTabbedPane();
		p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(lblUserName,BorderLayout.WEST);
		
		p1.add(btnLogout,BorderLayout.EAST);
		
		stp1= new SplitTypePanel(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass,isSeriesDir);
		dp1= new DirectorPanel(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass,isSeriesDir);
		rp1= new RacePanel(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass,isSeriesDir);
		ap1= new AthletePanel(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass,isSeriesDir);
	    rrp1= new RaceResultPanel(ipIn,dbNameIn,dbUserIn,dbPassIn,username,userPass,isSeriesDir);
		
		applicationTabs.add("Split Type", stp1);
		applicationTabs.add("Directors", dp1);
		applicationTabs.add("Races", rp1);
		applicationTabs.add("Athletes", ap1);
		applicationTabs.add("Results", rrp1);
		
		applicationTabs.setToolTipTextAt(0, "Split Types Administration Operations");
		applicationTabs.setToolTipTextAt(1, "Director Administration Operations");
		applicationTabs.setToolTipTextAt(2, "Race Administration Operations");
		applicationTabs.setToolTipTextAt(3, "Athlete Administration Operations");
		applicationTabs.setToolTipTextAt(4, "Result Administration Operations");
		
		btnLogout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{	
				@SuppressWarnings("unused")
				UserSignIn us1 = new UserSignIn(ipIn,dbNameIn,dbUserIn,dbPassIn);
				MainUI.this.dispose();
			}
		});
		
		this.add(p1,BorderLayout.NORTH);
		this.add(applicationTabs,BorderLayout.CENTER);
		
		this.setTitle("Database Managment Application");
		//mu1.setSize(500,300);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	
	
	
	/*public static void main(String[] args)
	{
		MainUI mu1 = new MainUI("localhost","project_database","root","","michael duffy","");
		mu1.setTitle("Database Managment Application");
		//mu1.setSize(500,300);
		mu1.pack();
		mu1.setLocationRelativeTo(null);
		mu1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mu1.setVisible(true);

	}*/

}
