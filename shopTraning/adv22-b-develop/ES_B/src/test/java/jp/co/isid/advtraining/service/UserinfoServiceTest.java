package jp.co.isid.advtraining.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.co.isid.advtraining.dao.EsqUserDao;
import jp.co.isid.advtraining.login.LoginUser;
import jp.co.isid.advtraining.login.UserinfoService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserinfoServiceTest {
	@Autowired
	private EsqUserDao esqUserDao;

	@Autowired
	private UserinfoService userinfoService;
	private LocalDateTime current;



	@Test
	@Order(1)
	@DisplayName("ログインに成功する(該当するユーザーがいる)")
    public void testService000_loadUserByUsername() {
		UserDetails userDetails = userinfoService.loadUserByUsername("li9011");
		LoginUser loginUser = (LoginUser)userDetails;
		assertEquals(loginUser.getEsqId(), "li9011");
		assertEquals(loginUser.getUserName(), "ジョンスミス");
	}

	@Test
	@Order(2)
	@DisplayName("ログインに失敗する(該当するユーザーがいない)")
    public void testService001_loadUserByUsername() {
		assertThrows(UsernameNotFoundException.class,  () -> userinfoService.loadUserByUsername("li2022"));
	}
}
