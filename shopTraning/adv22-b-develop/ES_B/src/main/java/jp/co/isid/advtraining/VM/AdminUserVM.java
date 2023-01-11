package jp.co.isid.advtraining.VM;

import java.util.List;

import jp.co.isid.advtraining.entity.AdminEsqUserDept;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EsqUserDept;

public class AdminUserVM {
	Enquete	enquete;
	List<AdminEsqUserDept>	adminEsqUserDeptList;
	List<EsqUserDept>	esqUserDeptList;

	public Enquete getEnquete() {
		return enquete;
	}
	public void setEnquete(Enquete enquete) {
		this.enquete = enquete;
	}
	public List<AdminEsqUserDept> getAdminEsqUserDeptList() {
		return adminEsqUserDeptList;
	}
	public void setAdminEsqUserDeptList(List<AdminEsqUserDept> adminEsqUserDeptList) {
		this.adminEsqUserDeptList = adminEsqUserDeptList;
	}
	public List<EsqUserDept> getEsqUserDeptList() {
		return esqUserDeptList;
	}
	public void setEsqUserDeptList(List<EsqUserDept> esqUserDeptList) {
		this.esqUserDeptList = esqUserDeptList;
	}


}
