package tableModels;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.sql.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

@SuppressWarnings("serial")
public class DateEditor extends AbstractCellEditor implements TableCellEditor,ActionListener 
{		
	java.util.Date d1;
	Calendar c1;	   
    JDatePicker picker;
	    
	public DateEditor()
	{					
		c1 = Calendar.getInstance();
	        picker = new JDateComponentFactory().createJDatePicker();		
	 	   // picker.setTextEditable(true);
	 		picker.setShowYearButtons(true);		
	 		picker.getModel().setSelected(true); 	 			 		 
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) 
	{
		
		//System.out.println( picker);
		//System.out.println( value.getClass()); //printing date in cell
	    
		String [] dates = value.toString().split("-");
		/*for(String s:dates)
		{
			System.out.println(s); //date as string array
		}*/
		picker.getModel().setYear(Integer.parseInt(dates[0]));
		picker.getModel().setMonth(Integer.parseInt(dates[1])-1);
		picker.getModel().setDay(Integer.parseInt(dates[2]));
		
	    return (Component) picker;
	}
	
	@Override
	public Date getCellEditorValue() 
	{		
		//System.out.println("getCellEd"+d1);
		int day = picker.getModel().getDay();
		int month = picker.getModel().getMonth();
		int year = picker.getModel().getYear();
		
		c1.set(year, month, day);
		d1 = c1.getTime();
		java.sql.Date sqlDate = new java.sql.Date(d1.getTime());
		return  sqlDate;
	}

}
