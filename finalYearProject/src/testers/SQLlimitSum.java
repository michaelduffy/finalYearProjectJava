package testers;

public class SQLlimitSum 
{
	private String s;
	
	
	public String sqlSumLimitString(String colName, String colAlias, String tableName, 
			String orderingArrangment, int limitIn,
			String subjectIdentifier)
	{
		s="";
		s += "SELECT SUM(subt."+colName+") AS '"+colAlias+"'"
				+"FROM (select "+tableName+"."+colName+" "
				+"FROM "+tableName+" "
				+ "WHERE "+tableName+"."+colName+" = "+subjectIdentifier+" "
				+"ORDER BY "+tableName+"."+colName+" "+orderingArrangment+" LIMIT "+limitIn+") as subt";
		return s;
	}
	
	public static void main(String[]args)
	{
		SQLlimitSum s1 = new SQLlimitSum();
		String s = s1.sqlSumLimitString("ath_race_points","total_points","athlete_race_result","DESC",2,"selectedAthID");
		System.out.println(s);
	}
}
