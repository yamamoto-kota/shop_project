package jp.co.isid.advtraining.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jp.co.isid.advtraining.login.UserinfoService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// httpパスアクセス許可、不許可設定
		// 指定パスへは全ユーザーアクセス許可
		http.authorizeRequests().antMatchers("/", "/images/**", "/css/**", "/javascript/**", "/webjars/**").permitAll()
		.anyRequest().authenticated();//そのほかのリクエストは承認が必要とする。
		http.formLogin().loginPage("/login") // ログインフォームのパス
		.defaultSuccessUrl("/list", true) // ログイン成功時の遷移先
		.usernameParameter("esqId") // ログインフォームで使用するユーザー名のinput name
		.passwordParameter("password")// ログインフォームで使用するパスワードのinput name
		.permitAll();
		http.logout().
		// ログアウトがパス(GET)の場合設定する（CSRF対応）
		logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
		// .logoutUrl("/logout") // ログアウトがPOSTの場合設定する
		.permitAll();
//		http.exceptionHandling()
//        .accessDeniedPage("/errorPage");
		return http.build();
	}

	@Configuration
	protected static class  AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private UserinfoService userinfoService;

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception{
			auth.userDetailsService(userinfoService).passwordEncoder(new Argon2PasswordEncoder());
		}
	}

}
