package com.basic.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.collection.IsEmptyIterable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.basic.Dao.ShopDao;
import com.basic.service.ShopService;

import static org.hamcrest.MatcherAssert.*;


@SpringBootTest
public class TestShopService {

	
//	@Test
//	@DisplayName("カート内の商品を削除する")
//	public void delItemTest2() {
//		List<SendItem> delList = shopService.getAll().getCartGoods();
//		CartData PC = new CartData();
//		PC.setName("PC");
//		PC.setId(4);
//		PC.setPrice(1000);
//		PC.setQuantity(1);
//		PC.setGenre("other");
//		shopService.sendDelItem(PC);
//		cartGoods = shopService.getAll().getCartGoods();
//
//		assertThat(delList.get(2).getName().isEmpty());
//		assertThat(delList.get(2).getId(),is(4));
//		assertThat(delList.get(2).getQuantity(),is(0));
//		assertThat(delList.get(2).getPrice(),is(0));
//		assertThat(delList.get(2).getGenre().isEmpty());
//	}
	

}
