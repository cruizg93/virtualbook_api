package com.virtualbook.api.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity()
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@NaturalId
	@Column	
	private RoleName name;
	
	@Column(name="description")
	private String description;
	
	//0:NO_ACTIVE 1:ACTIVE
	private int status;
}
