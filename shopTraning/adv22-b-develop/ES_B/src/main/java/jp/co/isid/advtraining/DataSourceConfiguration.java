package jp.co.isid.advtraining;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.NoCacheSqlFileRepository;
import org.seasar.doma.jdbc.SqlFileRepository;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jp.co.isid.advtraining.log.DbLogger;



@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

//application.propertyに記述しているデーターベース接続設定を読み込む場合は
// 30行目-40行目のように記述します

	@Autowired
	private DataSourceProperties properties;

	@Bean
	public DataSource dataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(properties.getDriverClassName());
		dataSource.setUrl(properties.getUrl());
		dataSource.setUsername(properties.getUsername());
		dataSource.setPassword(properties.getPassword());

		return new TransactionAwareDataSourceProxy(dataSource);
	}


//直接データーベース接続設定を記述する場合は45行目-53行目のように記述します
/*
@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/training?useUnicode=true&characterEncoding=utf8");
		dataSource.setUsername("suser");
		dataSource.setPassword("pass");
		return new TransactionAwareDataSourceProxy(dataSource);
	}
*/

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public Dialect dialect() {
		return new MysqlDialect();
	}

	@Bean
	public SqlFileRepository sqlFileRepository() {
		return new NoCacheSqlFileRepository();
	}

	@Bean
	public Config config() {
		return new Config() {
			@Override
			public Dialect getDialect() {
				return dialect();
			}
			@Override
			public DataSource getDataSource() {
				return dataSource();
			}
			@Override
			public SqlFileRepository getSqlFileRepository() {
				return sqlFileRepository();
			}
			@Override
	 		public JdbcLogger getJdbcLogger() {
	 		return new DbLogger();
	 		}
		};
	}
}
