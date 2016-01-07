package gov.ons.ness.data.data;

public class NessAreaSearchDTO implements Comparable<NessAreaSearchDTO>
{
	private String extcode;
	private String name;
	private String type;
	private String parentExtcode;
	private String parentName;
	private String envelope;

	public NessAreaSearchDTO(String extcode, String name, String envelope, String type,
	      String parentExtcode, String parentName)
	{
		super();
		this.extcode = extcode;
		this.name = name;
		this.envelope = envelope;
		this.type = type;
		this.parentExtcode = parentExtcode;
		this.parentName = parentName;
	}

	public String getExtcode()
	{
		return extcode;
	}

	public void setExtcode(String extcode)
	{
		this.extcode = extcode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getParentExtcode()
	{
		return parentExtcode;
	}

	public void setParentExtcode(String parent_extcode)
	{
		this.parentExtcode = parent_extcode;
	}

	public String getParentName()
	{
		return parentName;
	}

	public void setParentName(String parent_name)
	{
		this.parentName = parent_name;
	}

	public String getEnvelope()
	{
		return envelope;
	}

	public void setEnvelope(String envelope)
	{
		this.envelope = envelope;
	}

	@Override
	public int compareTo(NessAreaSearchDTO o)
	{
		return this.name.compareToIgnoreCase(o.name);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((envelope == null) ? 0 : envelope.hashCode());
		result = prime * result + ((extcode == null) ? 0 : extcode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
		      + ((parentExtcode == null) ? 0 : parentExtcode.hashCode());
		result = prime * result
		      + ((parentName == null) ? 0 : parentName.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NessAreaSearchDTO other = (NessAreaSearchDTO) obj;
		if (envelope == null)
		{
			if (other.envelope != null)
				return false;
		}
		else if (!envelope.equals(other.envelope))
			return false;
		if (extcode == null)
		{
			if (other.extcode != null)
				return false;
		}
		else if (!extcode.equals(other.extcode))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (parentExtcode == null)
		{
			if (other.parentExtcode != null)
				return false;
		}
		else if (!parentExtcode.equals(other.parentExtcode))
			return false;
		if (parentName == null)
		{
			if (other.parentName != null)
				return false;
		}
		else if (!parentName.equals(other.parentName))
			return false;
		if (type == null)
		{
			if (other.type != null)
				return false;
		}
		else if (!type.equals(other.type))
			return false;
		return true;
	}
}
