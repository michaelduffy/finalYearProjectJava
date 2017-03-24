package classes;

public class SplitType 
{
	private int splitTypeId;
	private String splitTypeDescription;
	
	/**
	 * @param splitTypeId
	 * @param splitTypeDescription
	 */
	public SplitType(int splitTypeId, String splitTypeDescription) 
	{
		this.splitTypeId = splitTypeId;
		this.splitTypeDescription = splitTypeDescription;
	}

	/**
	 * @return the splitTypeId
	 */
	public int getSplitTypeId() 
	{
		return splitTypeId;
	}

	/**
	 * @param splitTypeId the splitTypeId to set
	 */
	public void setSplitTypeId(int splitTypeId) 
	{
		this.splitTypeId = splitTypeId;
	}

	/**
	 * @return the splitTypeDescription
	 */
	public String getSplitTypeDescription() 
	{
		return splitTypeDescription;
	}

	/**
	 * @param splitTypeDescription the splitTypeDescription to set
	 */
	public void setSplitTypeDescription(String splitTypeDescription) 
	{
		this.splitTypeDescription = splitTypeDescription;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SplitType [splitTypeId=" + splitTypeId + ", splitTypeDescription=" + splitTypeDescription + "]";
	}
	
}
