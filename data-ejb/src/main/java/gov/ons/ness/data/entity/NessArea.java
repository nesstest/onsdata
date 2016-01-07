package gov.ons.ness.data.entity;

import java.io.Serializable;
import javax.persistence.*;

import gov.ons.ness.data.data.NessAreaSearchDTO;

/**
 * The persistent class for the ness_area database table.
 * 
 */
@Entity
@Table(name = "ness_area")
@NamedQueries({
//      @NamedQuery(name = "NessArea.findAll", query = "SELECT n FROM  NessArea n"),
      @NamedQuery(name = "NessArea.findChildren", query = "SELECT n FROM NessArea n WHERE n.parentExtcode = :parentExtcode"),
      @NamedQuery(name = "NessArea.findParent", query = "SELECT n FROM NessArea n WHERE n.extcode = :childExtcode"),
      @NamedQuery(name = "NessArea.basicSearch", query = "SELECT n FROM NessArea n WHERE n.name LIKE :searchTerm"),
      @NamedQuery(name = "NessArea.findEnvelope", query = "SELECT n FROM NessArea n WHERE n.extcode = :extcode")})
@SqlResultSetMapping(name = "SearchResult", classes = {
      @ConstructorResult(targetClass = NessAreaSearchDTO.class, columns = {
            @ColumnResult(name = "extcode"), @ColumnResult(name = "name"),
            @ColumnResult(name = "envelope"), @ColumnResult(name = "type"),
            @ColumnResult(name = "parent_extcode"),
            @ColumnResult(name = "parent_name") }) })
public class NessArea implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "country_extcode")
	private String countryExtcode;

	@Column(name = "country_name")
	private String countryName;

	private String extcode;

	@Column(name = "la_extcode")
	private String laExtcode;

	@Column(name = "la_name")
	private String laName;

	private String name;

	@Column(name = "parent_extcode")
	private String parentExtcode;

	@Column(name = "reg_extcode")
	private String regExtcode;

	@Column(name = "reg_name")
	private String regName;

	private String type;

	@Column(name = "ward_extcode")
	private String wardExtcode;

	@Column(name = "ward_name")
	private String wardName;
	
	@Column(name = "parli_extcode")
	private String parliExtcode;

	@Column(name = "parli_name")
	private String parliName;
	
	private String envelope;

	public NessArea()
	{
	}

	public int getId()
	{
		return this.id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCountryExtcode()
	{
		return this.countryExtcode;
	}

	public void setCountryExtcode(String countryExtcode)
	{
		this.countryExtcode = countryExtcode;
	}

	public String getCountryName()
	{
		return this.countryName;
	}

	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}

	public String getExtcode()
	{
		return this.extcode;
	}

	public void setExtcode(String extcode)
	{
		this.extcode = extcode;
	}

	public String getLaExtcode()
	{
		return this.laExtcode;
	}

	public void setLaExtcode(String laExtcode)
	{
		this.laExtcode = laExtcode;
	}

	public String getLaName()
	{
		return this.laName;
	}

	public void setLaName(String laName)
	{
		this.laName = laName;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getParentExtcode()
	{
		return this.parentExtcode;
	}

	public void setParentExtcode(String parentExtcode)
	{
		this.parentExtcode = parentExtcode;
	}

	public String getRegExtcode()
	{
		return this.regExtcode;
	}

	public void setRegExtcode(String regExtcode)
	{
		this.regExtcode = regExtcode;
	}

	public String getRegName()
	{
		return this.regName;
	}

	public void setRegName(String regName)
	{
		this.regName = regName;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getWardExtcode()
	{
		return this.wardExtcode;
	}

	public void setWardExtcode(String wardExtcode)
	{
		this.wardExtcode = wardExtcode;
	}

	public String getWardName()
	{
		return this.wardName;
	}

	public void setWardName(String wardName)
	{
		this.wardName = wardName;
	}

	public String getParliExtcode()
	{
		return parliExtcode;
	}

	public void setParliExtcode(String parliExtcode)
	{
		this.parliExtcode = parliExtcode;
	}

	public String getParliName()
	{
		return parliName;
	}

	public void setParliName(String parliName)
	{
		this.parliName = parliName;
	}

	public String getEnvelope()
	{
		return envelope;
	}

	public void setEnvelope(String envelope)
	{
		this.envelope = envelope;
	}

}