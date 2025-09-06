package io.paymeter.assessment.infraestructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;


@Configuration
@EnableJpaRepositories(
        basePackages = {"io.paymeter.assessment.infraestructure.databaseadapter"},
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "primaryTransactionManager")
public class DataBaseConfig {

    @Value("${io.paymenter.datasource.url}")
    private String urlDatabase;

    @Value("${io.paymenter.datasource.username}")
    private String usernameDatabase;

    @Value("${io.paymenter.datasource.password}")
    private String passwordDatabase;

    @Value("${io.paymenter.datasource.driver.class.name}")
    private String driverClassName;

    @Value("${io.paymenter.datasource.database.platform}")
    private String databasePlatform;

    @Value("${io.paymenter.datasource.schema}")
    private String schema;

    @Value("${io.paymenter.datasource.show-sql}")
    private String showSql;

    @Value("${io.paymenter.datasource.hikari.maximum-pool-size}")
    private int hikariMaximumPoolSize;

    @Value("${io.paymenter.datasource.hikari.minimum-idle}")
    private int minimumIdle;

    @Value("${io.paymenter.datasource.hikari.keepalive-time}")
    private int keepaliveTime;

    @Value("${io.paymenter.datasource.hikari.validation-timeout}")
    private int validationTimeout;

    @Value("${io.paymenter.datasource.hikari.idle-timeout}")
    private int idleTimeout;


    @Value("${io.paymenter.datasource.hikari.max-lifetime}")
    private int maxLifeTime;


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(hikariConfig());
        em.setPackagesToScan("io.paymeter.assessment.infraestructure.databaseadapter");
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("spring.jpa.database-platform", databasePlatform);
        properties.put("hibernate.dialect", databasePlatform);
        properties.put("spring.jpa.show-sql", showSql);
        properties.put("hibernate.show_sql", showSql);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public HikariDataSource hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(urlDatabase);
        hikariConfig.setUsername(usernameDatabase);
        hikariConfig.setPassword(passwordDatabase);
        hikariConfig.setMaximumPoolSize(hikariMaximumPoolSize);
        hikariConfig.setMinimumIdle(minimumIdle);
        hikariConfig.setMaxLifetime(maxLifeTime);
        hikariConfig.setSchema(schema);
        hikariConfig.setKeepaliveTime(keepaliveTime);
        hikariConfig.setValidationTimeout(validationTimeout);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setPoolName("paymeterPool");

        return new HikariDataSource(hikariConfig);

    }

    @Bean
    public PlatformTransactionManager primaryTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }
    
}
