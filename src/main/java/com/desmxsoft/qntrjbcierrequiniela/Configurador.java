package com.desmxsoft.qntrjbcierrequiniela;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = {"com.desmxsoft.qntrjbcierrequiniela", "com.desmxsoft.qntrdtoracle.dao.impl"})
public class Configurador {
	
	static DataSource dataSource = null;
	
	@Bean
	public DataSource dataSource() {
		if(dataSource != null) {
			return dataSource;
		}

		DriverManagerDataSource dataSourceOracle = new DriverManagerDataSource();
		dataSourceOracle.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSourceOracle.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		dataSourceOracle.setUsername("QUINIELATOR");
		dataSourceOracle.setPassword("3)0[Y3XS7Om^");
		dataSource = dataSourceOracle;

		return dataSourceOracle;
	}

    @Bean
    public JdbcTemplate jdbcTemplate() {
	        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
	        return new NamedParameterJdbcTemplate(dataSource());
    }


    @Bean
    public DataSourceTransactionManager txManager() {
    	return new DataSourceTransactionManager(dataSource());
    }

}
