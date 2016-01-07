package gov.ons.ness.data.data;

import gov.ons.ness.data.entity.NessArea;

public class NessAreaDTO
{
	private NessArea nessArea;

	public NessAreaDTO(NessArea nessArea)
	{
		super();
		this.nessArea = nessArea;
	}
	
	public boolean hasWard()
	{		
		return nessArea.getWardExtcode() != null ? true : false;
	}
	
	public boolean hasLa()
	{
		return nessArea.getLaExtcode() != null ? true : false;
	}

	public boolean hasRegion()
	{
		return nessArea.getRegExtcode() != null ? true : false;
	}
	
	public boolean hasCountry()
	{
		return nessArea.getCountryExtcode() != null ? true : false;
	}
	
	public boolean hasParli()
	{
		return nessArea.getParliExtcode() != null ? true : false;
	}

	public NessArea getNessArea()
	{
		return nessArea;
	}
}

