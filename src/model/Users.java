package model;
// Generated Aug 22, 2017 9:17:06 AM by Hibernate Tools 5.2.3.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "USERS")
public class Users implements java.io.Serializable {

	private static final long serialVersionUID = 1226854614841133737L;
	private String username;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private String phone;
	private Set<Author> authors = new HashSet<Author>(0);
	private Set<Subscriber> subscribers = new HashSet<Subscriber>(0);

	public Users() {
	}

	public Users(String username) {
		this.username = username;
	}

	
	
	@Id

	@Column(name = "USERNAME", unique = true, nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	
	@Column(name = "EMAIL", length = 128)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	@Column(name = "FIRSTNAME")
	public String getFirstname() {
		return this.firstname;
	}

	@Column(name = "LASTNAME")
	public String getLastname() {
		return this.lastname;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return this.phone;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Author> getAuthors() {
		return this.authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Subscriber> getSubscribers() {
		return this.subscribers;
	}

	public void setSubscribers(Set<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}

	@Override
	public String toString() {
		return "Users [username=" + username + ", email=" + email + ", password=" + password + ", firstname="
				+ firstname + ", lastname=" + lastname + ", phone=" + phone + "]";
	}

}
