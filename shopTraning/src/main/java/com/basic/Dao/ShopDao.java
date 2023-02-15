package com.basic.Dao;

import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.data.annotation.CreatedDate;

import com.basic.getEntity.GetCartItem;
import com.basic.getEntity.GetPurchaseItem;
import com.basic.getEntity.GetUser;
import com.basic.getEntity.Item;
import com.basic.sendEntity.PurchaseItem;
import com.basic.sendEntity.SendCartItem;
import com.basic.sendEntity.SendPurchaseItem;
import com.basic.sendEntity.SendSlip;
import com.basic.sendEntity.SendUser;
import com.basic.getEntity.GetSlip;
import com.basic.getEntity.GetUser;

@Dao
@ConfigAutowireable
public interface ShopDao {

	@Select
	List<Item> selectAll();

	@Select
	GetUser selectLoginUser(String loginId);

	@Select
	List<GetCartItem> selectCartItem(String loginId);

	@Insert
	int insertCart(SendCartItem newItem);

	@Update
	int updateCart(GetCartItem newItem);

	@Delete
	int delCartItem(GetCartItem newItem);

	@Update
	int updateMoney(GetUser loginUser);

	@Delete(sqlFile = true)
	int delAllCart(String loginId);

	@Insert
	int insertSlip(SendSlip slip);
	
//	@Insert
//	org.seasar.doma.jdbc.Result<GetSlip> insertSlip(SendSlip slip);

	@Select
	List<GetSlip> selectSlip(String loginId);
	
	@Select
	List<Item> selectsearchItem(String searchWord);

	@Insert
	int insertNewUser(SendUser newUser);

	@Insert
	int insertPurchaseItem(SendPurchaseItem purchaseItem);

	@Select
	List<GetPurchaseItem> selectPurchaseItem(int slipId);

	@Select
	List<GetSlip> selectMaxSlipId(String userId);
}