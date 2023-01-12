package jp.co.isid.advtraining.log;

import java.util.function.Supplier;

import org.seasar.doma.jdbc.AbstractJdbcLogger;
import org.seasar.doma.jdbc.Sql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ch.qos.logback.classic.Level;

public class DbLogger extends AbstractJdbcLogger<Level> {
	private static final Logger logger = LoggerFactory .getLogger(DbLogger.class);

	public DbLogger() {
		super(Level.INFO);
	}


	@Override
	public void logSql(String callerClassName, String callerMethodName, Sql<?> sql) {

		//この部分でエラー 別の記述
		//ユーザID
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		//Principalからログインユーザの情報を取得
		String userName = "anonymousUser";
			if(auth != null) {
				auth.getName();
			}
    	String esqId = "[" + userName + "] ";

    	//発行したSQL
    	String sqlText = "[" + sql.getFormattedSql() + "] ";

		logger.info(sqlText + esqId);
	 }


	@Override
	protected void log(Level level, String callerClassName, String callerMethodName, Throwable throwable,
			Supplier<String> messageSupplier) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
