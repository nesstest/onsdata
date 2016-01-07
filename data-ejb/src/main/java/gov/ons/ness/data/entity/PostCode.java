package gov.ons.ness.data.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the post_code database table.
 * 
 */
@Entity
@Table(name="post_code")
@NamedQueries({
   @NamedQuery(name = "NessArea.findPostCode", query = "SELECT p FROM PostCode p WHERE p.pcds = :postcode") })
public class PostCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String ccgcd;

	private String ccgnm;

	private String ctrycd;

	private String ctrynm;

	private Integer doterm;

	private String gorcd;

	private String gornm;

	private String lauacd;

	private String lauanm;

	private String oa11cd;

	private int oaeast1m;

	private int oanrth1m;

	private String pcds;

	private String pconcd;

	private String pconnm;

	private String wardcd;

	private String wardnm;

	public PostCode() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCcgcd() {
		return this.ccgcd;
	}

	public void setCcgcd(String ccgcd) {
		this.ccgcd = ccgcd;
	}

	public String getCcgnm() {
		return this.ccgnm;
	}

	public void setCcgnm(String ccgnm) {
		this.ccgnm = ccgnm;
	}

	public String getCtrycd() {
		return this.ctrycd;
	}

	public void setCtrycd(String ctrycd) {
		this.ctrycd = ctrycd;
	}

	public String getCtrynm() {
		return this.ctrynm;
	}

	public void setCtrynm(String ctrynm) {
		this.ctrynm = ctrynm;
	}

	public Integer getDoterm() {
		return this.doterm;
	}

	public void setDoterm(Integer doterm) {
		this.doterm = doterm;
	}

	public String getGorcd() {
		return this.gorcd;
	}

	public void setGorcd(String gorcd) {
		this.gorcd = gorcd;
	}

	public String getGornm() {
		return this.gornm;
	}

	public void setGornm(String gornm) {
		this.gornm = gornm;
	}

	public String getLauacd() {
		return this.lauacd;
	}

	public void setLauacd(String lauacd) {
		this.lauacd = lauacd;
	}

	public String getLauanm() {
		return this.lauanm;
	}

	public void setLauanm(String lauanm) {
		this.lauanm = lauanm;
	}

	public String getOa11cd() {
		return this.oa11cd;
	}

	public void setOa11cd(String oa11cd) {
		this.oa11cd = oa11cd;
	}

	public int getOaeast1m() {
		return this.oaeast1m;
	}

	public void setOaeast1m(int oaeast1m) {
		this.oaeast1m = oaeast1m;
	}

	public int getOanrth1m() {
		return this.oanrth1m;
	}

	public void setOanrth1m(int oanrth1m) {
		this.oanrth1m = oanrth1m;
	}

	public String getPcds() {
		return this.pcds;
	}

	public void setPcds(String pcds) {
		this.pcds = pcds;
	}

	public String getPconcd() {
		return this.pconcd;
	}

	public void setPconcd(String pconcd) {
		this.pconcd = pconcd;
	}

	public String getPconnm() {
		return this.pconnm;
	}

	public void setPconnm(String pconnm) {
		this.pconnm = pconnm;
	}

	public String getWardcd() {
		return this.wardcd;
	}

	public void setWardcd(String wardcd) {
		this.wardcd = wardcd;
	}

	public String getWardnm() {
		return this.wardnm;
	}

	public void setWardnm(String wardnm) {
		this.wardnm = wardnm;
	}

}