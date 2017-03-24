package databaseClasses;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass 
{
	private static Connection conn;
	private static String ipAddress;
	private static String databaseName;
	private static String databaseUser;
	private static String databasePassword;
	
	
	public ConnectionClass(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn)
	{
		 ipAddress = ipIn;
		 databaseName = dbNameIn;
		 databaseUser = dbUserIn;
		 databasePassword = dbPassIn;
	}
	
	/*public ConnectionClass()
	{
		 ipAddress = "localhost";
		 databaseName = "project_database";
		 databaseUser = "root";
		 databasePassword = ""; 
	}*/
	
	
	/*public Connection openConnection() throws Exception
	{
		System.out.println("attempting to connect to DB");
        Class.forName("com.mysql.jdbc.Driver"); 
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/project_database","root","");	
		System.out.println("success");
		
		return conn;
	}*/
	
	public Connection openConnection() throws Exception
	{
		System.out.println("attempting to connect to DB");
        Class.forName("com.mysql.jdbc.Driver"); 
		conn=DriverManager.getConnection("jdbc:mysql://"+ipAddress+":3306/"+databaseName+"",""+databaseUser+"",""+databasePassword+"");
		System.out.println("success");
		
		return conn;
	}
	
	
	public void closeConnection()throws Exception
	{
		conn.close();	
	}
	
}
