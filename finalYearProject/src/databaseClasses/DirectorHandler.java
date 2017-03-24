package databaseClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import classes.Director;


public class DirectorHandler 
{
	private Director director1;
	private Connection conn;
	
	private PreparedStatement getDirectors = null;	
	private String getDirectorsString;
	
	private PreparedStatement deleteDirector = null;	
	private String deleteDirectorString;
	
	private PreparedStatement editDirector = null;	
	private String editDirectorString;
	
	private PreparedStatement addDirector = null;	
	private String addDirectorString;
	
	private PreparedStatement getDirectorIds = null;	
	private String getDirectorIdsString;
	
	private boolean directorIdExists;
	
	private ResultSet rs1;
	private ConnectionClass cc1;
	
	public DirectorHandler(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn,
							String username, String userPass)
	{
		cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn);
		directorIdExists = false;
	}
	
	//method to return all SplitTypes in database
	public ResultSet getDirectors() throws Exception
	{
		
	    getDirectorsString ="select * from director";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getDirectors = conn.prepareStatement(getDirectorsString);
		rs1 = getDirectors.executeQuery();
									
		return rs1;					
	}
	
	public ResultSet getDirectorByEmail(String email) throws Exception
	{
		
	    getDirectorsString ="select * from director where email = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getDirectors = conn.prepareStatement(getDirectorsString);
		getDirectors.setString(1,email);
		rs1 = getDirectors.executeQuery();
										
		return rs1;					
	}
	
	//method to delete a SplitType in database
	public int deleteDirector(int directorIdIn)throws Exception
	{
		int objectDeletedId = -1;
    	int deleteCheck = 0;
    	
		deleteDirectorString = "DELETE FROM director WHERE director_id = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		deleteDirector =conn.prepareStatement(deleteDirectorString);
		deleteDirector.setInt(1,directorIdIn);
    	
    	deleteCheck = deleteDirector.executeUpdate(); //should return 1 for successful delete otherwise 0;
    	if(deleteCheck !=0)
    	{
        	 objectDeletedId = directorIdIn;
      	}
    	deleteDirector.close();
    	final int I = objectDeletedId;
    	
    	return I;
	}
	
	//method to edit a SplitType in database
	public int editDirector( int idIn, String fNameIn,String lNameIn,String phoneIn,String emailIn,
							Boolean seriesDirIn, Boolean raceDirIn,String passIn) throws Exception
    {
    	int editCheck = 0; //only 0 if update does not perform
    	
    	director1 = new Director(idIn,fNameIn,lNameIn,phoneIn,emailIn,seriesDirIn,raceDirIn,passIn); //to perform validation checks!!
    	editDirectorString = "UPDATE director SET director_first_name = ?, "
    			           + "director_last_name = ?,"
    			           + "phone_num = ?,"
    			           + "email = ?,"
    			           + "is_series_director = ?,"
    			           + "is_race_director = ?,"
    			           + "password = ? WHERE director_id =?";
    	conn = cc1.openConnection();
    	editDirector = conn.prepareStatement(editDirectorString);
    	
    	
    	editDirector.setString(1, director1.getDirFirstName());
    	editDirector.setString(2, director1.getDirLastName());
    	editDirector.setString(3, director1.getPhoneNum());
    	editDirector.setString(4, director1.getEmail());
    	editDirector.setBoolean(5, director1.getIsSeriesDir());
    	editDirector.setBoolean(6, director1.getIsRaceDir());
    	editDirector.setString(7, director1.getPassword());
    	editDirector.setInt(8, director1.getDirector_id());
    	
    	
    	editCheck = editDirector.executeUpdate(); //perform update and return confirmation 
    	editDirector.close();
    	
    	final int I = editCheck;
    	
    	return I;
    }
	
	public  void addDirector(int idIn, String fNameIn,String lNameIn,String phoneIn,String emailIn,
			Boolean seriesDirIn, Boolean raceDirIn,String passIn) throws Exception 
    {   	
		    director1 = new Director(idIn,fNameIn,lNameIn,phoneIn,emailIn,seriesDirIn,raceDirIn,passIn); 
		    
			addDirectorString = "insert into director values(?,?,?,?,?,?,?,?)"; 
			
			conn = cc1.openConnection();
       	    
			addDirector = conn.prepareStatement(addDirectorString);
			
			addDirector.setInt(1, director1.getDirector_id());
			addDirector.setString(2, director1.getDirFirstName());
			addDirector.setString(3, director1.getDirLastName());
			addDirector.setString(4, director1.getPhoneNum());
			addDirector.setString(5, director1.getEmail());
			addDirector.setBoolean(6, director1.getIsSeriesDir());
			addDirector.setBoolean(7, director1.getIsRaceDir());
			addDirector.setString(8, director1.getPassword());
       	         	          	   							        	   	    	
			directorIdExists = validateDirectorID(director1);
			
	        if(!directorIdExists)
	        {
	        	addDirector.execute();
			    JOptionPane.showMessageDialog(null, "New Director saved to database.");
	        }
	        else
	        {
	        	JOptionPane.showMessageDialog(null, "Director with ID number :"+director1.getDirector_id()+" already exists in database!!");
	        }  
			
	        rs1.close();
	        addDirector.close();
	 }
	
	public boolean validateDirectorID(Director dir1) throws Exception
	{
			getDirectorIdsString ="select director_id from director";
			conn = cc1.openConnection();
		    getDirectorIds = conn.prepareStatement(getDirectorIdsString);	       
		    rs1 = getDirectorIds.executeQuery();
		    
		    directorIdExists = false;
		    while(rs1.next())
		    {
		    	if( dir1.getDirector_id()==rs1.getInt(1) ) 
		    		directorIdExists = true;
		    }
		    
		    return directorIdExists;
	        
	}
	
	 public int getHighestDirectorId() throws Exception
	    {  		
		     getDirectorIdsString ="select director_id from director";
		     conn = cc1.openConnection();
		     getDirectorIds = conn.prepareStatement(getDirectorIdsString);
		     rs1 = getDirectorIds.executeQuery();
	    	 int highestDirId=0;
	    	 int temp=0;
	    	 
	    	 while(rs1.next())
	    	 {
	    		  temp = rs1.getInt(1);
	    		  if (temp > highestDirId)
	    			  highestDirId = temp; 
	         }
	    	 rs1.close();
	    	 getDirectorIds.close();
	    	 final int I = highestDirId;	    	
	    	 return I;
	    }
	
}

