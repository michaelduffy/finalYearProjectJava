package classes;

import java.util.Date;

public class Race
{
	private int raceId;
	private String raceName;
	private String raceLocation;
	private Date raceDate;
	private Boolean isMultiWave;
	private int noRecordedSplits;
	
	public Race(int raceId, String raceName, String raceLocation, Date raceDate, Boolean isMultiWave,
			int noRecordedSplits) 
	{
		
		this.raceId = raceId;
		this.raceName = raceName;
		this.raceLocation = raceLocation;
		this.raceDate = raceDate;
		this.isMultiWave = isMultiWave;
		this.noRecordedSplits = noRecordedSplits;
	}

	/**
	 * @return the raceId
	 */
	public int getRaceId() {
		return raceId;
	}

	/**
	 * @param raceId the raceId to set
	 */
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}

	/**
	 * @return the raceName
	 */
	public String getRaceName() {
		return raceName;
	}

	/**
	 * @param raceName the raceName to set
	 */
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	/**
	 * @return the raceLocation
	 */
	public String getRaceLocation() {
		return raceLocation;
	}

	/**
	 * @param raceLocation the raceLocation to set
	 */
	public void setRaceLocation(String raceLocation) {
		this.raceLocation = raceLocation;
	}

	/**
	 * @return the raceDate
	 */
	public Date getRaceDate() {
		return raceDate;
	}

	/**
	 * @param raceDate the raceDate to set
	 */
	public void setRaceDate(Date raceDate) {
		this.raceDate = raceDate;
	}

	/**
	 * @return the isMultiWave
	 */
	public Boolean getIsMultiWave() {
		return isMultiWave;
	}

	/**
	 * @param isMultiWave the isMultiWave to set
	 */
	public void setIsMultiWave(Boolean isMultiWave) {
		this.isMultiWave = isMultiWave;
	}

	/**
	 * @return the noRecordedSplits
	 */
	public int getNoRecordedSplits() {
		return noRecordedSplits;
	}

	/**
	 * @param noRecordedSplits the noRecordedSplits to set
	 */
	public void setNoRecordedSplits(int noRecordedSplits) {
		this.noRecordedSplits = noRecordedSplits;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Race [raceId=" + raceId + ", raceName=" + raceName + ", raceLocation=" + raceLocation + ", raceDate="
				+ raceDate + ", isMultiWave=" + isMultiWave + ", noRecordedSplits=" + noRecordedSplits + "]";
	}
	
	
	
	
}
