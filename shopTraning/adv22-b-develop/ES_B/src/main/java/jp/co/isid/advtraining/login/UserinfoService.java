package jp.co.isid.advtraining.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.isid.advtraining.dao.EsqUserDao;
import jp.co.isid.advtraining.entity.EsqUser;

@Service
public class UserinfoService implements UserDetailsService{

	private final EsqUserDao esqUserDao;

	@Autowired
	public UserinfoService(EsqUserDao esqUserDao) {
		this.esqUserDao = esqUserDao;
	}

	@Override
	public UserDetails loadUserByUsername(String esqId) throws UsernameNotFoundException {
		EsqUser esqUser = esqUserDao.selectById(esqId);
		if(esqUser == null) {
			throw new UsernameNotFoundException(esqId + "not found");
		}
		return new LoginUser(esqUser);
	}
}
