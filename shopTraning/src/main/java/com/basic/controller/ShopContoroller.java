package com.basic.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.seasar.doma.jdbc.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.basic.getEntity.GetCartItem;
import com.basic.getEntity.GetPurchaseItem;
import com.basic.getEntity.Item;
import com.basic.getEntity.GetSlip;
import com.basic.getEntity.GetUser;
import com.basic.getEntity.UserDTO;
import com.basic.sendEntity.PurchaseItem;
import com.basic.sendEntity.SendCartItem;
import com.basic.sendEntity.SendSlip;
import com.basic.service.ShopService;



@RestController
@CrossOrigin
public class ShopContoroller {
	
	@Autowired
	private ShopService shopService;

	@GetMapping("/")
	public List<Item> allItem(){
		List<Item> allItem = new ArrayList<>(); 
		allItem = shopService.getAllItem();
		return allItem;
	}
	
	@PostMapping("/login")
	public GetUser login(@RequestBody(required = false) String loginId) {
		GetUser loginUser = new GetUser();
		try {
			loginUser = shopService.getLoginUser(loginId);
			return loginUser;
	    } catch (NullPointerException e) {
	    	System.out.println("/////////////");
	        return loginUser;
	    }
		
	}
	
	@PostMapping("/searchItem")
	public List<Item> serchItem(@RequestBody String searchWord,String radio){
		List<Item> searchItem = new ArrayList<>(); 
		searchItem = shopService.getsearchItem(radio,searchWord);
		return searchItem;
	}
	

	@PostMapping("/cartitem")
	public List<GetCartItem> cart(@RequestBody String loginId) {
		List<GetCartItem> cartItem = new ArrayList<>();
		cartItem = shopService.getCartItem(loginId);
		return cartItem;
	}
	
	@PostMapping("/addItem")
	public void addItem(@RequestBody GetCartItem newItem) {
		shopService.addCartItem(newItem);
	}
	
	@PostMapping("/delItem")
	public void delItem(@RequestBody GetCartItem newItem) {
		shopService.delCartItem(newItem);
	}
	
	@PostMapping("/purchase")
	public void purchase(@RequestBody GetUser loginUser) {
		shopService.purchaseItem(loginUser);
	}
	
	@PostMapping("/cartReset")
	public void cartReset(@RequestBody GetUser loginUser) {
		shopService.cartReset(loginUser);
	}
	
	
	@PostMapping("/createSlip")
	public void createSlip(@RequestBody SendSlip slip) {
		shopService.createSlip(slip);
	}
	
	//購入標品登録
	@PostMapping("/sendPurchaseItem")
	public void sendPurchaseItem(@RequestBody List<GetCartItem> cartItem) {
		shopService.addPurchaseItem(cartItem);
	}
	
	@PostMapping("/returnSlip")
	public List<GetSlip> returnSlip(@RequestBody String loginId) {
		List<GetSlip> slipList = new ArrayList<>();
		slipList = shopService.getSlipList(loginId);
		return slipList;
	}
	
	@PostMapping("/csvDownload")
	public void csvDownload(@RequestBody String loginId) {
		String csv = shopService.getcsvData(loginId); 
		FileOutputStream fos = null;
		 OutputStreamWriter osw = null;
		 BufferedWriter bw = null;
		try {
			
			 // 出力ファイルの作成
            FileWriter fw = new FileWriter("購入履歴.csv", false);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            // ヘッダーの指定
            pw.print(csv);
            
            pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//新規会員登録
	@PostMapping("/createNewUser")
	public GetUser createUser(@RequestBody UserDTO newUser) {
		shopService.createUser(newUser);
		
		GetUser newloginUser = new GetUser();
		return newloginUser;
	}
	
	//購入履歴詳細表示
	@PostMapping("/purchaseList")
	public List<GetPurchaseItem> showPurchaseList(@RequestBody String str) {
		List<GetPurchaseItem> purchaseList = new ArrayList<>();
		String str2 = str.replace("=","");
		int slipId = 0;
		try{
			slipId = Integer.parseInt(str2);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		purchaseList = shopService.getPurchaseList(slipId);
		return purchaseList;
	}
	
	@PostMapping("/test")
	public String test(@RequestBody String test) {
		//int i = 1;
		System.out.println("////////////////////");
		System.out.println(test);
		//System.out.println(i*num);
		return test;
	}
	
}
