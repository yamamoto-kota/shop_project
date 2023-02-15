package com.basic.getEntity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "USERLIST")
public class GetUser {
	
	@Id
	@Column(name = "UNIQUEID")
	Integer uniqueId;

	@Column(name = "USERID")
	String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Column(name = "USERNAME")
	String userName;
	
	@Column(name = "ADDRESS")
	String address;
	
	@Column(name = "MAIL")
	String mail;
	
	@Column(name = "MONEY")
	Integer money;
	
	
}
