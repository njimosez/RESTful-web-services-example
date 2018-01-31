package model;

import javax.persistence.Transient;


public class Link {
	private String link;
	private String rel;
	
	@Transient
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Transient
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	
}