package com.basic.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basic.Dao.ShopDao;
import com.basic.entity.CartItem;
import com.basic.entity.CreateUser;
import com.basic.entity.Item;
import com.basic.entity.Slip;
import com.basic.entity.User;
import com.basic.entity.UserDTO;

@Service
public class ShopService {
	@Autowired
	private ShopDao shopDao;

//商品一覧取得
	public List<Item> getAllItem() {
		List<Item> allItem = new ArrayList<Item>();
    	allItem = shopDao.selectAll();
		return allItem;
	}

	//ログイン認証
	public User getLoginUser(String id) {
		User loginUser = new User();
		String loginId = id.replace("=","");
		System.out.println(loginId);
		loginUser = shopDao.selectLoginUser(loginId);
		return loginUser;
	}

//ログインしてるユーザーのカート情報取得
	public List<CartItem> getCartItem(String id) {
		List<CartItem> cartItem = new ArrayList<CartItem>();
		String loginId = id.replace("=", "");
		cartItem = shopDao.selectCartItem(loginId);
    	return cartItem;
	}

//カートに商品追加
	public void addCartItem(CartItem newItem) {
		if (newItem.getQuantity() == 1) {
		int i = shopDao.insertCart(newItem);
		}else if(newItem.getQuantity() > 1) {
			int i = shopDao .updateCart(newItem);
			}
	}

//カートの商品削除
	public void delCartItem(CartItem newItem) {
		if(newItem.getQuantity() == 1) {
			int i = shopDao.delCartItem(newItem);
		}else if(newItem.getQuantity() > 1) {
			newItem.setQuantity(newItem.getQuantity()-1);
				int i = shopDao .updateCart(newItem);
				}

	}

//購入を所持金に反映
	public void purchaseItem(User loginUser) {
		int i = shopDao.updateMoney(loginUser);
	}
	
//購入後カート内をリセット
	public void cartReset(User loginUser) {
		String loginId = loginUser.getUserId();
		int i = shopDao.delAllCart(loginId);
		
	}

//伝票作成
	public void cartReset(Slip slip) {
		int i = shopDao.insertSlip(slip);
	}

//ログインユーザーの伝票一覧取得
	public List<Slip> getSlipList(String id) {
	List<Slip> slipList = new ArrayList<>();
	String loginId = id.replace("=", "");
	slipList = shopDao.selectSlip(loginId);
		return slipList;
	}

//csvファイルダウンロード
	public String getcsvData(String id) {
		String loginId = id.replace("=", "");
		StringBuilder csv = new StringBuilder();
		List<Slip> slipList = new ArrayList<>();
		slipList = shopDao.selectSlip(loginId);
		csv.append("\uFEFF");
		csv.append("購入者ID");
		csv.append(",");
		csv.append("購入金額");
		csv.append(",");
		csv.append("購入日");
		csv.append("\n");

		for(Slip s: slipList) {
			csv.append("\"");
			csv.append(s.getUserId());
			csv.append("\"");
			csv.append(",");
			
			csv.append("\"");
			csv.append(s.getPurchasePrice());
			csv.append("\"");
			csv.append(",");
			
			csv.append("\"");
			csv.append(s.getPurchaseDate());
			csv.append("\"");
			csv.append("\n");
		}
		
		String csvData = csv.toString();
		return csvData;
	}

//商品検索
	public List<Item> getsearchItem(String radio, String searchWord) {
		List<Item> searchItem = new ArrayList<Item>();
		//String sendradio = radio.replace("=","");
		String sendsearchWord = searchWord.replace("=","");
		searchItem = shopDao.selectsearchItem(sendsearchWord);
		return searchItem;
	}

//新規会員登録
	public void createUser(UserDTO userDTO) {
		
		//DTOからEntityにセット
		CreateUser newUser = new CreateUser();
		newUser.setUserId(userDTO.getUserId());
		newUser.setUserName(userDTO.getUserName());
		newUser.setAddress(userDTO.getAddress());
		newUser.setMail(userDTO.getMail());
		newUser.setMoney(userDTO.getMoney());
		
		
		System.out.println(userDTO.getUserName());
		System.out.println(userDTO.getUserId());
		System.out.println(userDTO.getAddress());
		System.out.println(userDTO.getMail());
		System.out.println(userDTO.getMoney());
		
		shopDao.insertNewUser(newUser);
	}

}
