package jp.co.isid.advtraining.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jp.co.isid.advtraining.VM.AdminUserVM;
import jp.co.isid.advtraining.adminUser.EnqueteAdminUserService;
import jp.co.isid.advtraining.dao.EnqueteAdminUserDao;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback
public class EnqueteAdminUserServiceTest {

	@Autowired
	private EnqueteAdminUserService enqueteAdminUserService;
	@Autowired
	private EnqueteAdminUserDao enqueteAdminUserDao ;

	@Test
	@Order(1)
	@DisplayName("受け取ったアンケートIDに対応するアンケート名と管理者一覧が返ってくる)")
    public void testService000_getEnqueteAdminUser() {

		Integer enqueteId=1;
		AdminUserVM  adVM=enqueteAdminUserService.getEnqueteAdminUser(enqueteId);

		adVM= enqueteAdminUserService.getEnqueteAdminUser(1);

		assertThat(adVM.getAdminEsqUserDeptList().get(0).getEsqId(),is("li9010"));
		assertThat(adVM.getAdminEsqUserDeptList().get(0).getDeptName(),is("監査室"));
		assertThat(adVM.getAdminEsqUserDeptList().get(0).getUserName(),is("須賀尚紀"));
	}



	@Test
	@Order(2)
	@DisplayName("検索した氏名に対応するESQIDと名前のListが返ってくる")
    public void testService001_search() {
		AdminUserVM adVM= enqueteAdminUserService.search(1, "ジョン");
		assertThat(adVM.getEsqUserDeptList().get(0).getEsqId(),is("li9011"));
		assertThat(adVM.getEsqUserDeptList().get(0).getUserName(),is("ジョンスミス"));
	}

	@Test
	@Order(3)
	@DisplayName("DBのレコードが0件のため、空のListが返ってくる（すでに管理者の人を検索した場合)")
    public void testService002_search() {
		AdminUserVM  adVM= enqueteAdminUserService.search(1, "須賀");
		assertThat(adVM.getEsqUserDeptList(),is(empty()));
	}

	@Test
	@Order(4)
	@DisplayName("DBのレコードが0件のため、空のListが返ってくる(該当する名前の人がいない場合)")
    public void testService003_search() {
		AdminUserVM  adVM= enqueteAdminUserService.search(1, "浅野");
		assertThat(adVM.getEsqUserDeptList(),is(empty()));
	}

	@Test
	@Order(5)
	@DisplayName("今まで一度も管理者になったことがない人を、管理者に追加する。")
    public void testService004_insertUpdate() {
		Boolean test= enqueteAdminUserService.insertUpdate(1, "li9011", 0);
		assertThat(test,	is(true));
	}

	@Test
	@Order(6)
	@DisplayName("過去管理者だった人を、管理者に追加する。")
    public void testService005_insertUpdate() {
		enqueteAdminUserDao.selectById(2, "li9012");
		Boolean test= enqueteAdminUserService.insertUpdate(2, "li9012", 0);
		assertThat(test,	is(true));
	}

	@Test
	@Order(7)
	@DisplayName("管理者である人を、管理者から削除する。")
    public void testService006_insertUpdate() {
		enqueteAdminUserDao.selectById(1, "li9011");
		Boolean test= enqueteAdminUserService.insertUpdate(2, "li9011", 0);
		assertThat(test,	is(false));
	}

	@Test
	@Order(8)
	@DisplayName("排他制御されているとき、nullを返す。")
	public void testService002_checkadmin() {
	 int enq =enqueteAdminUserService.checkAdmin(1, "li9010");
	 assertThat(enq,is(0));

	   }

	@Test
	@Order(9)
	@DisplayName("管理者権限が剝奪されている場合、エラーメッセージを返す。")
	public void testService003_checkadmin() {
		int enq=enqueteAdminUserService.checkAdmin(3, "li9012");
		assertThat(enq, is(1));
	    }

	@Test
	@Order(10)
	@DisplayName("アンケートが存在し、管理者権限を所持している場合、。")
	public void testService004_checkadmin() {
		int enq=enqueteAdminUserService.checkAdmin(1, "li9011");
		assertThat(enq, is(2));
	    }

}
