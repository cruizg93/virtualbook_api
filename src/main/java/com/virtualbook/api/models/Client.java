package com.virtualbook.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity(name="clients")
public class Client {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String email;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	private String address;
	
	//Know as Nit in Colombia
	private String taxId;
	
	//0:NO_ACTIVE 1:ACTIVE
	private int status;
}
