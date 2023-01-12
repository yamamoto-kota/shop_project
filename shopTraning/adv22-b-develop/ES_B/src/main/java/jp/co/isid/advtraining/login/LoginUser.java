package jp.co.isid.advtraining.login;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import jp.co.isid.advtraining.entity.EsqUser;

public class LoginUser extends User {

	private static final long serialVersionUID = 1L;

	private String esqId;
	private String userName;
	private Integer deptId;

	public LoginUser(EsqUser esqUser){
        super(esqUser.getEsqId(), esqUser.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>());
        esqId = esqUser.getEsqId();
        userName = esqUser.getUserName();
        deptId = esqUser.getDeptId();
	}

	public String getEsqId() {
		return esqId;
	}

	public void setEsqId(String esqId) {
		this.esqId = esqId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}


}
