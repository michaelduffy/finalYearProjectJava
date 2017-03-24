package classes;

public class Director 
{
	private int director_id;
	private String dirFirstName;
	private String dirLastName;
	private String phoneNum;
	private String email;
	private Boolean isSeriesDir;
	private Boolean isRaceDir;
	private String password;
	
	public Director(int director_id, String dirFirstName, String dirLastName, String phoneNum, String email,
			Boolean isSeriesDir, Boolean isRaceDir, String password) 
	{
		this.director_id = director_id;
		this.dirFirstName = dirFirstName;
		this.dirLastName = dirLastName;
		this.phoneNum = phoneNum;
		this.email = email;
		this.isSeriesDir = isSeriesDir;
		this.isRaceDir = isRaceDir;
		this.password = password;
	}

	/**
	 * @return the director_id
	 */
	public int getDirector_id() {
		return director_id;
	}

	/**
	 * @param director_id the director_id to set
	 */
	public void setDirector_id(int director_id) {
		this.director_id = director_id;
	}

	/**
	 * @return the dirFirstName
	 */
	public String getDirFirstName() {
		return dirFirstName;
	}

	/**
	 * @param dirFirstName the dirFirstName to set
	 */
	public void setDirFirstName(String dirFirstName) {
		this.dirFirstName = dirFirstName;
	}

	/**
	 * @return the dirLastName
	 */
	public String getDirLastName() {
		return dirLastName;
	}

	/**
	 * @param dirLastName the dirLastName to set
	 */
	public void setDirLastName(String dirLastName) {
		this.dirLastName = dirLastName;
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
	 * @return the isSeriesDir
	 */
	public Boolean getIsSeriesDir() {
		return isSeriesDir;
	}

	/**
	 * @param isSeriesDir the isSeriesDir to set
	 */
	public void setIsSeriesDir(Boolean isSeriesDir) {
		this.isSeriesDir = isSeriesDir;
	}

	/**
	 * @return the isRaceDir
	 */
	public Boolean getIsRaceDir() {
		return isRaceDir;
	}

	/**
	 * @param isRaceDir the isRaceDir to set
	 */
	public void setIsRaceDir(Boolean isRaceDir) {
		this.isRaceDir = isRaceDir;
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
		return "Director [director_id=" + director_id + ", dirFirstName=" + dirFirstName + ", dirLastName="
				+ dirLastName + ", phoneNum=" + phoneNum + ", email=" + email + ", isSeriesDir=" + isSeriesDir
				+ ", isRaceDir=" + isRaceDir + ", password=" + password + "]";
	}
	
	
	
	
}
