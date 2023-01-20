package com.basic.controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.basic.entity.CartItem;
import com.basic.entity.Item;
import com.basic.entity.Slip;
import com.basic.entity.User;
import com.basic.entity.UserDTO;
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
	public User login(@RequestBody String loginId) {
		User loginUser = new User();
		System.out.println(loginId);
		loginUser = shopService.getLoginUser(loginId);
		System.out.println(loginUser.getUserName());
		return loginUser;
	}
	
	@PostMapping("/searchItem")
	public List<Item> serchItem(@RequestBody String searchWord,String radio){
		List<Item> searchItem = new ArrayList<>(); 
		searchItem = shopService.getsearchItem(radio,searchWord);
		System.out.println(searchItem.get(0).getItemName());
		return searchItem;
	}
	

	@PostMapping("/cartitem")
	public List<CartItem> cart(@RequestBody String loginId) {
		List<CartItem> cartItem = new ArrayList<>();
		cartItem = shopService.getCartItem(loginId);
		return cartItem;
	}
	
	@PostMapping("/addItem")
	public void addItem(@RequestBody CartItem newItem) {
		System.out.println(newItem.getUserId());
		shopService.addCartItem(newItem);
	}
	
	@PostMapping("/delItem")
	public void delItem(@RequestBody CartItem newItem) {
		shopService.delCartItem(newItem);
	}
	
	@PostMapping("/purchase")
	public void purchase(@RequestBody User loginUser) {
		shopService.purchaseItem(loginUser);
	}
	
	@PostMapping("/cartReset")
	public void cartReset(@RequestBody User loginUser) {
		shopService.cartReset(loginUser);
	}
	
	
	@PostMapping("/createSlip")
	public void createSlip(@RequestBody Slip slip) {
		shopService.cartReset(slip);
	}
	
	@PostMapping("/returnSlip")
	public List<Slip> returnSlip(@RequestBody String loginId) {
		List<Slip> slipList = new ArrayList<>();
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
	
	@PostMapping("/createNewUser")
	public void createUser(@RequestBody UserDTO newUser) {
		shopService.createUser(newUser);
	}
}
