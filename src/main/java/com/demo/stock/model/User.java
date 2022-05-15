package com.demo.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
public class User {
	
	@Getter @Setter 
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userid;
	
	@Getter @Setter
	@Column(nullable = false, unique = true)
	private String username;

	@Getter @Setter 
	@Column(nullable = false)
	private String password;
	
	@Getter @Setter 
	@Column(nullable = false)
	private String role;
}
