package classes;

import java.util.Date;

import javax.swing.JOptionPane;

public class Athlete 
{
	private int athId;
	private String athFname;
	private String athLname;
	private String addLine1;
	private String addLine2;
	private String addLine3;
	private String county;
	private String gender;
	private Date dob;
	private String phoneNum;
	private String email;
	private String password;
	
	public Athlete(int athId, String athFname, String athLname, String addLine1, String addLine2, String addLine3,
			String county, String gender, Date dob, String phoneNum, String email, String password)
	{
		if(gender.length() > 1)
		{
			gender = "f";
			JOptionPane.showMessageDialog(null, "Athlete gender must be no more than 1\n!! GENDER NOW SET TO DEFAULT OF F !!");			
		}

		if((!gender.equalsIgnoreCase("f")) && (!gender.equalsIgnoreCase("m")))
		{
			gender = "f";
			JOptionPane.showMessageDialog(null, "Athlete gender must either f or m \n!! GENDER NOW SET TO DEFAULT OF F !!");			
		}
		
		if(gender.equals("F"))
			gender = "f";
		if(gender.equals("M"))
			gender = "m";
				
			
				
		
		this.athId = athId;
		this.athFname = athFname;
		this.athLname = athLname;
		this.addLine1 = addLine1;
		this.addLine2 = addLine2;
		this.addLine3 = addLine3;
		this.county = county;
		this.gender = gender;
		this.dob = dob;
		this.phoneNum = phoneNum;
		this.email = email;
		this.password = password;
	}

	/**
	 * @return the athId
	 */
	public int getAthId() {
		return athId;
	}

	/**
	 * @param athId the athId to set
	 */
	public void setAthId(int athId) {
		this.athId = athId;
	}

	/**
	 * @return the athFname
	 */
	public String getAthFname() {
		return athFname;
	}

	/**
	 * @param athFname the athFname to set
	 */
	public void setAthFname(String athFname) {
		this.athFname = athFname;
	}

	/**
	 * @return the athLname
	 */
	public String getAthLname() {
		return athLname;
	}

	/**
	 * @param athLname the athLname to set
	 */
	public void setAthLname(String athLname) {
		this.athLname = athLname;
	}

	/**
	 * @return the addLine1
	 */
	public String getAddLine1() {
		return addLine1;
	}

	/**
	 * @param addLine1 the addLine1 to set
	 */
	public void setAddLine1(String addLine1) {
		this.addLine1 = addLine1;
	}

	/**
	 * @return the addLine2
	 */
	public String getAddLine2() {
		return addLine2;
	}

	/**
	 * @param addLine2 the addLine2 to set
	 */
	public void setAddLine2(String addLine2) {
		this.addLine2 = addLine2;
	}

	/**
	 * @return the addLine3
	 */
	public String getAddLine3() {
		return addLine3;
	}

	/**
	 * @param addLine3 the addLine3 to set
	 */
	public void setAddLine3(String addLine3) {
		this.addLine3 = addLine3;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Athlete [athId=" + athId + ", athFname=" + athFname + ", athLname=" + athLname + ", addLine1="
				+ addLine1 + ", addLine2=" + addLine2 + ", addLine3=" + addLine3 + ", county=" + county + ", gender="
				+ gender + ", dob=" + dob + ", phoneNum=" + phoneNum + ", email=" + email + ", password=" + password
				+ "]";
	}
	
	
	
	

}
