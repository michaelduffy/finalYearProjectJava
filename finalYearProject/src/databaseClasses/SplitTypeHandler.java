package databaseClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import classes.SplitType;

public class SplitTypeHandler 
{
	private SplitType splitType1;
	private Connection conn;
	
	private PreparedStatement getSplitTypes = null;	
	private String getSplitTypesString;
	
	private PreparedStatement deleteSplitType = null;	
	private String deleteSplitTypeString;
	
	private PreparedStatement editSplitType = null;	
	private String editSplitTypeString;
	
	private PreparedStatement addSplitType = null;	
	private String addSplitTypeString;
	
	private PreparedStatement getSplitIds = null;	
	private String getSplitIdsString;
	
	private boolean splitTypeIdExists;
	
	private ResultSet rs1;
	private ConnectionClass cc1;
	
	public SplitTypeHandler(String ipIn,String dbNameIn,String dbUserIn,String dbPassIn, String username, String userPass)
	{
		cc1 = new ConnectionClass(ipIn,dbNameIn,dbUserIn,dbPassIn);
		splitTypeIdExists = false;
	}
	
	//method to return all SplitTypes in database
	public ResultSet getSplitTypes() throws Exception
	{
		
	    getSplitTypesString ="select * from split_type";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		getSplitTypes = conn.prepareStatement(getSplitTypesString);
		rs1 = getSplitTypes.executeQuery();
									
		return rs1;					
	}
	
	//method to delete a SplitType in database
	public int deleteSplit(int splitIDin)throws Exception
	{
		int objectDeletedId = -1;
    	int deleteCheck = 0;
    	
		deleteSplitTypeString = "DELETE FROM split_type WHERE split_type_id = ?";
		//cc1 = new ConnectionClass();
		conn = cc1.openConnection();
		deleteSplitType =conn.prepareStatement(deleteSplitTypeString);
		deleteSplitType.setInt(1,splitIDin);
    	
    	deleteCheck = deleteSplitType.executeUpdate(); //should return 1 for successful delete otherwise 0;
    	if(deleteCheck !=0)
    	{
        	 objectDeletedId = splitIDin;
      	}
    	deleteSplitType.close();
    	final int I = objectDeletedId;
    	
    	return I;
	}
	
	//method to edit a SplitType in database
	public int editSplitType( int idIn, String nameIn) throws Exception
    {
    	int editCheck = 0; //only 0 if update does not perform
    	
    	splitType1 = new SplitType(idIn,nameIn); //to perform validation checks!!
    	editSplitTypeString = "UPDATE split_type SET split_type_name = ? where split_type_id =?";
    	conn = cc1.openConnection();
    	editSplitType = conn.prepareStatement(editSplitTypeString);
    	
    	
    	editSplitType.setString(1, splitType1.getSplitTypeDescription());
    	editSplitType.setInt(2, splitType1.getSplitTypeId());
    	
    	editCheck = editSplitType.executeUpdate(); //perform update and return confirmation 
    	editSplitType.close();
    	
    	final int I = editCheck;
    	
    	return I;
    }
	
	public  void addSplitType(int idIn, String nameIn) throws Exception 
    {   	
		    splitType1 = new SplitType(idIn,nameIn); //to perform validation checks!!
		    
			addSplitTypeString = "insert into split_type values(?,?)"; 
			
			conn = cc1.openConnection();
       	    
			addSplitType = conn.prepareStatement(addSplitTypeString);
       	    
			addSplitType.setInt(1,splitType1.getSplitTypeId());
			addSplitType.setString(2,splitType1.getSplitTypeDescription());
       	         	          	   							        	   	    	
			splitTypeIdExists = validateSplitID( splitType1);
			
	        if(!splitTypeIdExists)
	        {
	        	addSplitType.execute();
			    JOptionPane.showMessageDialog(null, "New Split Type saved to database.");
	        }
	        else
	        {
	        	JOptionPane.showMessageDialog(null, "Split Type with ID number :"+splitType1.getSplitTypeId()+" already exists in database!!");
	        }  
			
	        rs1.close();
	        addSplitType.close();
	 }
	
	public boolean validateSplitID(SplitType splitType1) throws Exception
	{
			getSplitIdsString ="select split_type_id from split_type";
			conn = cc1.openConnection();
		    getSplitIds = conn.prepareStatement(getSplitIdsString);	       
		    rs1 = getSplitIds.executeQuery();
		    
		    splitTypeIdExists = false;
		    while(rs1.next())
		    {
		    	if( splitType1.getSplitTypeId()==rs1.getInt(1) ) 
		    		splitTypeIdExists = true;
		    }
		    
		    return splitTypeIdExists;
	        
	}
	
	 public int getHighestSplitTypeId() throws Exception
	    {  		
		     getSplitIdsString ="select split_type_id from split_type";
		     conn = cc1.openConnection();
		     getSplitIds = conn.prepareStatement(getSplitIdsString);
		     rs1 = getSplitIds.executeQuery();
	    	 int highestSplitTypeId=0;
	    	 int temp=0;
	    	 
	    	 while(rs1.next())
	    	 {
	    		  temp = rs1.getInt(1);
	    		  if (temp > highestSplitTypeId)
	    			  highestSplitTypeId = temp; 
	         }
	    	 rs1.close();
	    	 getSplitIds.close();
	    	 final int I = highestSplitTypeId;	    	
	    	 return I;
	    }
	
}
