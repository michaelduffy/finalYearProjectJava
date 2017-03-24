package tableModels;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class RaceTableModel extends AbstractTableModel
{	
	private String [] columnNames;
	private Object [][] data;
	
	
	public RaceTableModel(ResultSet rsIn)
	{
		
		try
		{
	        ResultSetMetaData metaData = rsIn.getMetaData();
			
			//get Column names and number of
			int columnCount = metaData.getColumnCount();
			//System.out.println("colCount(Race) = "+columnCount);
			//get row count
			int rowCount = 0;
			while(rsIn.next())
			{
				rowCount++; //get data rows
			}			
			//System.out.println("Constructor rowCount(Race) = "+rowCount);
			
			columnNames = new String[columnCount];
			data = new Object[rowCount][columnCount];
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void buildModel(ResultSet rsIn) throws SQLException
	{		
		ResultSetMetaData metaData = rsIn.getMetaData();
		int columnCount = metaData.getColumnCount();
						
	    for (int column = 1; column <= columnCount; column++) 
	    {	      
	    	columnNames[column-1] = metaData.getColumnName(column);
	       // System.out.println("(race)col = "+columnNames[column-1]);
	    }
	    
	    int rowIndex = 0;	    	    
	    while (rsIn.next()) 
	    {
	    		    	
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	        {
	            data[rowIndex][columnIndex-1] = rsIn.getObject(columnIndex);
	          //  System.out.print("(race)data ="+data[rowIndex][columnIndex-1]);
	        }
	        System.out.println("");	       
	        rowIndex++;	      
	    }	  
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		//System.out.println("rowCount = "+data.length);//to test
		return data.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		//System.out.println("colCount = "+columnNames.length);//to test
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		// TODO Auto-generated method stub
		
		return data[rowIndex][columnIndex];
	}
	
	@Override
    public String getColumnName(int index) {
        return columnNames[index];
    }
	
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
	        return getValueAt(0, c).getClass();
	    }

	 public boolean isCellEditable(int row, int col) {
	      
	        if (col < 1) {
	            return false;
	        } else {
	            return true;
	        }
	    }
	 
	 public void setValueAt(Object value, int row, int col) {
		 	data[row][col] = value;
	        fireTableCellUpdated(row, col);
	    }
}

