package model;
// Generated Aug 24, 2017 8:08:58 AM by Hibernate Tools 5.2.3.Final

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Books generated by hbm2java
 */
@XmlRootElement
@Entity
@Transactional
@Table(name = "BOOKS")
public class Books implements java.io.Serializable {

	private static final long serialVersionUID = 3280125668353031193L;
	private String isbn13;

	private String title;
	private String authorName;
	private Date publishDate;
	private BigDecimal price;
	private Blob content;

	public Books() {
	}

	public Books(String isbn13) {
		this.isbn13 = isbn13;
	}

	public Books(String isbn13, String title, String authorName, Date publishDate, BigDecimal price, Blob content) {
		this.isbn13 = isbn13;

		this.title = title;
		this.authorName = authorName;
		this.publishDate = publishDate;
		this.price = price;
		this.content = content;
	}

	@Id

	@Column(name = "ISBN_13", unique = true, nullable = false, length = 13)
	public String getIsbn13() {
		return this.isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	@Column(name = "TITLE", length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "AUTHOR", length = 80)
	public String getauthorName() {
		return this.authorName;
	}

	public void setauthorName(String authorName) {
		this.authorName = authorName;
	}

	@XmlTransient
	@Column(name = "PUBLISH_DATE", length = 7)
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "PRICE", precision = 6)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@XmlTransient
	@Transient
	@Column(name = "CONTENT")
	public Blob getContent() {
		return this.content;
	}

	public void setContent(Blob content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Books [isbn13=" + isbn13 + ",  title=" + title + ", authorName=" + authorName + ", publishDate="
				+ publishDate + ", price=" + price + "]";
	}

}