package com.basic.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basic.Dao.ShopDao;
import com.basic.getEntity.GetCartItem;
import com.basic.getEntity.GetPurchaseItem;
import com.basic.getEntity.GetUser;
import com.basic.getEntity.Item;
import com.basic.getEntity.GetSlip;
import com.basic.getEntity.GetUser;
import com.basic.getEntity.UserDTO;
import com.basic.sendEntity.PurchaseItem;
import com.basic.sendEntity.SendCartItem;
import com.basic.sendEntity.SendPurchaseItem;
import com.basic.sendEntity.SendSlip;
import com.basic.sendEntity.SendUser;
import com.basic.sendEntity._SendPurchaseItem;

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
	public GetUser getLoginUser(String id) {
		GetUser loginUser = new GetUser();
		String loginId = id.replace("=","");
		loginUser = shopDao.selectLoginUser(loginId);
		return loginUser;
	}

//ログインしてるユーザーのカート情報取得
	public List<GetCartItem> getCartItem(String id) {
		List<GetCartItem> cartItem = new ArrayList<GetCartItem>();
		String loginId = id.replace("=", "");
		cartItem = shopDao.selectCartItem(loginId);
    	return cartItem;
	}

//カートに商品追加
	public void addCartItem(GetCartItem newItem) {
		SendCartItem sendcartItem = new SendCartItem();
		sendcartItem.setGenre(newItem.getGenre());
		sendcartItem.setImg(newItem.getImg());
		sendcartItem.setItemId(newItem.getItemId());
		sendcartItem.setItemName(newItem.getItemName());
		sendcartItem.setPrice(newItem.getPrice());
		sendcartItem.setQuantity(newItem.getQuantity());
		sendcartItem.setUserId(newItem.getUserId());
		if (newItem.getQuantity() == 1) {
		int i = shopDao.insertCart(sendcartItem);
		}else if(newItem.getQuantity() > 1) {
			int i = shopDao.updateCart(newItem);
			}
	}

//カートの商品削除
	public void delCartItem(GetCartItem newItem) {
		SendCartItem sendcartItem = new SendCartItem();
		sendcartItem.setGenre(newItem.getGenre());
		sendcartItem.setImg(newItem.getImg());
		sendcartItem.setItemId(newItem.getItemId());
		sendcartItem.setItemName(newItem.getItemName());
		sendcartItem.setPrice(newItem.getPrice());
		sendcartItem.setQuantity(newItem.getQuantity());
		sendcartItem.setUserId(newItem.getUserId());
		if(newItem.getQuantity() == 1) {
			int i = shopDao.delCartItem(newItem);
		}else if(newItem.getQuantity() > 1) {
			newItem.setQuantity(newItem.getQuantity()-1);
				int i = shopDao .updateCart(newItem);
				}

	}

//購入を所持金に反映
	public void purchaseItem(GetUser loginUser) {
		int i = shopDao.updateMoney(loginUser);
	}
	
//購入後カート内をリセット
	public void cartReset(GetUser loginUser) {
		String loginId = loginUser.getUserId();
		int i = shopDao.delAllCart(loginId);
		
	}

//伝票作成
	public void createSlip(SendSlip slip) {
		int i = shopDao.insertSlip(slip);
	}

//ログインユーザーの伝票一覧取得
	public List<GetSlip> getSlipList(String id) {
	List<GetSlip> slipList = new ArrayList<>();
	String loginId = id.replace("=", "");
	slipList = shopDao.selectSlip(loginId);
		return slipList;
	}

//csvファイルダウンロード
	public String getcsvData(String id) {
		String loginId = id.replace("=", "");
		StringBuilder csv = new StringBuilder();
		List<GetSlip> slipList = new ArrayList<>();
		slipList = shopDao.selectSlip(loginId);
		csv.append("\uFEFF");
		csv.append("購入者ID");
		csv.append(",");
		csv.append("購入金額");
		csv.append(",");
		csv.append("購入日");
		csv.append("\n");

		for(GetSlip s: slipList) {
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
		String sendsearchWord = searchWord.replace("=","");
		searchItem = shopDao.selectsearchItem(sendsearchWord);
		return searchItem;
	}

//新規会員登録
	public GetUser createUser(UserDTO userDTO) {
		
		//DTOからEntityにセット
		SendUser newUser = new SendUser();
		newUser.setUserId(userDTO.getUserId());
		newUser.setUserName(userDTO.getUserName());
		newUser.setAddress(userDTO.getAddress());
		newUser.setMail(userDTO.getMail());
		newUser.setMoney(userDTO.getMoney());
		//DBに登録
		shopDao.insertNewUser(newUser);
		
		//登録した情報を画面に返す
		GetUser newLoginUser = new GetUser();
		newLoginUser = shopDao.selectLoginUser(newUser.getUserId());
		return newLoginUser;
		
	}
	//購入履歴詳細取得
	public List<GetPurchaseItem> getPurchaseList(int slipId) {
		List<GetPurchaseItem> purchaseList = new ArrayList<>();
		purchaseList = shopDao.selectPurchaseItem(slipId);
		return purchaseList;
		
	}
//購入商品登録
	public void addPurchaseItem(List<GetCartItem> cartItemList) {
		for(GetCartItem gci: cartItemList) {
			SendPurchaseItem purchaseItem = new SendPurchaseItem();
			purchaseItem.setGenre(gci.getGenre());
			purchaseItem.setImg(gci.getImg());
			purchaseItem.setItemId(gci.getItemId());
			purchaseItem.setItemName(gci.getItemName());
			purchaseItem.setPrice(gci.getPrice());
			purchaseItem.setQuantity(gci.getQuantity());
			purchaseItem.setUserId(gci.getUserId());
			List<GetSlip> lastSlip = new ArrayList<>();
			lastSlip = shopDao.selectMaxSlipId(gci.getUserId());
			purchaseItem.setSlip(lastSlip.get(0).getSlipId());
			shopDao.insertPurchaseItem(purchaseItem);
		}
	}

}
