package kr.enterkey.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import kr.enterkey.dao.UserDao;

@Configuration
@Lazy
@EnableTransactionManagement(proxyTargetClass=true)
@MapperScan(basePackages={"kr.enterkey.dao"})
public class UserDBConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Bean(name="userDataSource")
	@ConfigurationProperties(prefix="spring.datasource.user.master")
	public DataSource userDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name="userSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(userDataSource());
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/user/*.xml"));
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name="userDataSourceTransactionManager")
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(userDataSource());
	}

	@Bean(name="userContentsDao")
	public MapperFactoryBean<?> userContentsDao() throws Exception {
		MapperFactoryBean<UserDao> mapperFactoryBean = new MapperFactoryBean<>();
		mapperFactoryBean.setMapperInterface(UserDao.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactoryBean;
	}
}
