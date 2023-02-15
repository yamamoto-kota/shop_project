package com.basic.getEntity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity
@Table(name = "ALLITEM")
public class Item {
	
	@Id
	@Column(name = "ITEMID")
	Integer itemId;
	
	@Column(name = "ITEMNAME")
	String itemName;
	
	@Column(name = "GENRE")
	String genre;

	@Column(name = "PRICE")
	Integer price;
	
	@Column(name = "IMG")
	String img;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
