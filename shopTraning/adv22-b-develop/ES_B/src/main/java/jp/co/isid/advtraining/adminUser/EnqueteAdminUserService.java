package jp.co.isid.advtraining.adminUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.isid.advtraining.VM.AdminUserVM;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;
import jp.co.isid.advtraining.dao.EnqueteDao;
import jp.co.isid.advtraining.entity.AdminEsqUserDept;
import jp.co.isid.advtraining.entity.Enquete;
import jp.co.isid.advtraining.entity.EnqueteAdminUser;
import jp.co.isid.advtraining.entity.EsqUser;

/**
 * @author FLM
 * @version 1.0.0
 */
@Service
public class EnqueteAdminUserService {
	@Autowired
	EnqueteDao enqueteDao;

	@Autowired
	EnqueteAdminUserDao enqueteAdminUserDao;

	//排他制御確認
	public int checkAdmin(Integer enqueteId, String esqId) {
		int z = 0;
		Enquete enqueteID = enqueteDao.selectById(enqueteId);
		if (enqueteID == null) {
			z = 1;
			return z;
		}
		EnqueteAdminUser adminUserID = enqueteAdminUserDao.selectById(enqueteId, esqId);
		if (adminUserID == null || adminUserID.getDeleteFlag() != 0) {
			z = 2;
			return z;
		}
		return z;
	}

	//画面表示
	public AdminUserVM getEnqueteAdminUser(Integer enqueteId) {
		AdminUserVM adminUserVM = new AdminUserVM();
		adminUserVM.setEnquete(enqueteDao.selectById(enqueteId));
		adminUserVM.setAdminEsqUserDeptList(enqueteAdminUserDao.getEnqueteAdminUser(enqueteId));
		adminUserVM.setEsqUserDeptList(null);
		return adminUserVM;
	}

	//検索
	public AdminUserVM search(Integer enqueteId, String searchWord) {
		AdminUserVM adminUserVM = new AdminUserVM();
		adminUserVM.setEnquete(enqueteDao.selectById(enqueteId));
		adminUserVM.setAdminEsqUserDeptList(enqueteAdminUserDao.getEnqueteAdminUser(enqueteId));
		adminUserVM.setEsqUserDeptList(enqueteAdminUserDao.selectBySearchWord(enqueteId, searchWord));
		if(adminUserVM.getEsqUserDeptList().isEmpty()) {
			for(AdminEsqUserDept user :  adminUserVM.getAdminEsqUserDeptList()) {
				if(user.getUserName().equals(searchWord)) {
					adminUserVM = null;
					return adminUserVM;
				}
			}
		}
		return adminUserVM;
	}

	//削除・追加
	public Boolean insertUpdate(Integer enqueteId, String esqId, Integer deleteFlag) {
		Boolean ret = true;
		int result;
		EnqueteAdminUser enqueteAdminUser = new EnqueteAdminUser();
		if (enqueteAdminUserDao.selectById(enqueteId, esqId) == null) {
			enqueteAdminUser.setEnqueteId(enqueteId);
			enqueteAdminUser.setEsqId(esqId);
			enqueteAdminUser.setDeleteFlag(deleteFlag);
			result = enqueteAdminUserDao.insert(enqueteAdminUser);
		} else {
			EnqueteAdminUser esqUser = enqueteAdminUserDao.selectById(enqueteId, esqId);
			if (esqUser.getDeleteFlag() == deleteFlag) {
				ret = false;
			} else {
				enqueteAdminUser = enqueteAdminUserDao.selectById(enqueteId, esqId);
				enqueteAdminUser.setDeleteFlag(deleteFlag);
				result = enqueteAdminUserDao.update(enqueteAdminUser);
			}
		}
	return ret;
	}
}
