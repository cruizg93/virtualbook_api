package com.virtualbook.api.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.virtualbook.api.models.audit.DateAudit;

import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"}),
		@UniqueConstraint(columnNames = {"email"})
})
public class User extends DateAudit {

	public User(@NotBlank @Size(max = 40) String firstName, @NotBlank @Size(max = 40) String lastName, @NotBlank @Size(max = 15) String username,
			@NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	private String password;
	
	private String username;
	
	private String email;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	//0:NO_ACTIVE 1:ACTIVE
	private int status;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", 
	joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name ="role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();
}