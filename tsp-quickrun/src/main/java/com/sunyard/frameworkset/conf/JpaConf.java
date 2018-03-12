package com.sunyard.frameworkset.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by liyingdan on 2016/8/2.
 */
@Configuration
@Profile( "dev")
public class JpaConf {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${pool.minIdle}")
    private String minIdle;

    @Value("${pool.maxActive}")
    private String maxActive;

    @Value("${pool.initialSize}")
    private String initialSize;

    @Value("${pool.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;

    @Value("${pool.maxOpenPreparedStatements}")
    private String maxOpenPreparedStatements;

    @Value("${pool.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;

    @Value ( "${fap.database}" )
    private String database;

    @Value ( "${validation.sql}" )
    private String validationSql;

    @Value ( "${scan.packages}" )
    private String scanPackage;

    @Value ( "${jpa.showSql}" )
    private boolean showSql;
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSource dataSource () throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setFilters("stat");
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
        dataSource.setValidationQuery(validationSql);
        dataSource.setMinIdle(Integer.parseInt(minIdle));
        dataSource.setMaxActive(Integer.parseInt(maxActive));
        dataSource.setInitialSize(Integer.parseInt(initialSize));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(maxOpenPreparedStatements));

        return dataSource;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource")DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);

        entityManagerFactory.setPackagesToScan(scanPackage.split ( "," ) );

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(showSql);

        hibernateJpaVendorAdapter.setDatabase(Database.valueOf ( database ));
        hibernateJpaVendorAdapter.setGenerateDdl(false);


        entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        return entityManagerFactory;
    }
}
