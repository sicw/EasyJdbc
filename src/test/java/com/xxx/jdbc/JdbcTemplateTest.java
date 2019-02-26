package com.xxx.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxx.jdbc.core.JdbcTemplate;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

public class JdbcTemplateTest {

    private DataSource dataSource = null;

    @Before
    public void setup(){
        dataSource = new DruidDataSource();
        ((DruidDataSource) dataSource).setDriverClassName("com.mysql.jdbc.Driver");
        ((DruidDataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/mytest?characterEncoding=utf8");
        ((DruidDataSource) dataSource).setUsername("root");
        ((DruidDataSource) dataSource).setPassword("123456");
    }

    @Test
    public void testDataSource(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        jdbcTemplate.execute();
    }
}
