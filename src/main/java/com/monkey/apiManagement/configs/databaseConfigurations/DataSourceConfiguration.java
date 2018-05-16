package com.monkey.apiManagement.configs.databaseConfigurations;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.monkey.apiManagement",
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "customerTransactionManager")
@EnableTransactionManagement
@EnableEncryptableProperties
public class DataSourceConfiguration {
    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.setDatabase(Database.SQL_SERVER);
        return jpaProperties;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.qa.datasource")
    public DataSource qaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.stage.datasource")
    public DataSource stageDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSource clientDataSource() {
        DataSourceRouter router = new DataSourceRouter();

        final Map<Object, Object> map = new HashMap<>(3);
        map.put(DatabaseEnvironment.QA, qaDataSource());
        map.put(DatabaseEnvironment.STAGE, stageDataSource());
        router.setTargetDataSources(map);
        return router;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager(
            final JpaProperties jpaProperties) {

        EntityManagerFactoryBuilder builder =
                createEntityManagerFactoryBuilder(jpaProperties);

        return builder.dataSource(clientDataSource()).packages("com.monkey.apiManagement")
                .persistenceUnit("entityManager").build();
    }

    @Bean
    @Primary
    public JpaTransactionManager customerTransactionManager(
            @Qualifier("entityManager") final EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    private EntityManagerFactoryBuilder createEntityManagerFactoryBuilder(
            JpaProperties jpaProperties) {
        JpaVendorAdapter jpaVendorAdapter =
                createJpaVendorAdapter(jpaProperties);
        return new EntityManagerFactoryBuilder(jpaVendorAdapter,
                jpaProperties.getProperties(), this.persistenceUnitManager);
    }

    private JpaVendorAdapter createJpaVendorAdapter(
            JpaProperties jpaProperties) {
        AbstractJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(jpaProperties.isShowSql());
        adapter.setDatabase(jpaProperties.getDatabase());
        adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
        adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
        return adapter;
    }
}
