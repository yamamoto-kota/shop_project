package com.basic.getEntity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "SLIP")
public class GetSlip {

	@Id
	@Column(name = "SLIPID")
	Integer slipId;
	
	@Column(name = "USERID")
	String userId;
	
	@Column(name = "ALLITEMNAME")
	String allItemName;
	
	@Column(name = "PURCHASEPRICE")
	Integer purchasePrice;
	
	@Column(name = "PURCHASEDATE")
	String purchaseDate;

	public Integer getSlipId() {
		return slipId;
	}

	public void setSlipId(Integer slipId) {
		this.slipId = slipId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getAllItemName() {
		return allItemName;
	}

	public void setAllItemName(String allItemName) {
		this.allItemName = allItemName;
	}
}
