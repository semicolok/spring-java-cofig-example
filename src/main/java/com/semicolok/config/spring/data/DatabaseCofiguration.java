package com.semicolok.config.spring.data;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories("com.semicolok.web.repository")
public class DatabaseCofiguration {
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USER_NAME = "db.username";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";

    @Autowired private Environment env;

//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/ttest?useUnicode=true&amp;connectionCollation=utf8_general_ci&amp;characterSetResults=utf8&amp;characterEncoding=utf8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("");
//        dataSource.setInitialSize(5);
//        dataSource.setMaxActive(10);
//        dataSource.setMinIdle(5);
//        dataSource.setMaxIdle(10);
//        dataSource.setMaxWait(-1);
//        
//        return dataSource;
//    }
    @Bean
    public DataSource boneCPDataSource() {
 
        BoneCPDataSource boneCPDataSource = new BoneCPDataSource();
        boneCPDataSource.setDriverClass(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
//        boneCPDataSource.setDriverClass("com.mysql.jdbc.Driver");
        boneCPDataSource.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
        boneCPDataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USER_NAME));
        boneCPDataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
        boneCPDataSource.setIdleConnectionTestPeriodInMinutes(60);
        boneCPDataSource.setIdleMaxAgeInMinutes(420);
        boneCPDataSource.setMaxConnectionsPerPartition(30);
        boneCPDataSource.setMinConnectionsPerPartition(10);
        boneCPDataSource.setPartitionCount(3);
        boneCPDataSource.setAcquireIncrement(5);
        boneCPDataSource.setStatementsCacheSize(100);
 
        return boneCPDataSource;
 
    }
    
    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    @Autowired
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        
        entityManagerFactoryBean.setPersistenceUnitName("testBoard");
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.semicolok.web.entity");
        
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(false);;
        
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        entityManagerFactoryBean.setJpaProperties(hibProperties());
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.connection.autocommit", env.getRequiredProperty("hibernate.connection.autocommit"));
        properties.put("hibernate.current_session_context_class", env.getRequiredProperty("hibernate.current_session_context_class"));
        return properties;
    }

    @Bean
    @Autowired
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        JpaDialect jpaDialect = new HibernateJpaDialect();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setJpaDialect(jpaDialect);
        return transactionManager;
    }
}
