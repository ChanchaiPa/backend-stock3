package com.demo.stock.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Accessors(chain = true)
public class Product {
	
	@Getter @Setter	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "p_id")
	private int pid;
	
	@Getter @Setter	
	@Column(name = "p_name", length = 100, nullable = false, unique = true)
	private String pname;
	
	@Getter @Setter	
	private String image;
	
	@Getter @Setter	
	private int price;
	
	@Getter @Setter	
	private int stock;
	
	@Getter @Setter(AccessLevel.NONE)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;
	
	@Getter @Setter(AccessLevel.NONE)
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	private Date updateDate;
}
