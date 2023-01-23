package com.basic.Dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.springframework.data.annotation.CreatedDate;

import com.basic.entity.CartItem;
import com.basic.entity.CreateUser;
import com.basic.entity.Item;
import com.basic.entity.Slip;
import com.basic.entity.User;

@Dao
@ConfigAutowireable
public interface ShopDao {

	@Select
	List<Item> selectAll();

	@Select
	User selectLoginUser(String loginId);

	@Select
	List<CartItem> selectCartItem(String loginId);

	@Insert
	int insertCart(CartItem newItem);

	@Update
	int updateCart(CartItem newItem);

	@Delete
	int delCartItem(CartItem newItem);

	@Update
	int updateMoney(User loginUser);

	@Delete(sqlFile = true)
	int delAllCart(String loginId);


	@Insert
	int insertSlip(Slip slip);

	@Select
	List<Slip> selectSlip(String loginId);
	
	@Select
	List<Item> selectsearchItem(String searchWord);

	@Insert
	int insertNewUser(CreateUser newUser);	
	
}