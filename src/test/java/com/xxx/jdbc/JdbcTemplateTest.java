package com.xxx.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.xxx.jdbc.core.JdbcTemplate;
import com.xxx.jdbc.core.RowMapper;
import com.xxx.jdbc.domain.Student;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplateTest {

    private DataSource dataSource = null;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup(){
        dataSource = new DruidDataSource();
        ((DruidDataSource) dataSource).setDriverClassName("com.mysql.jdbc.Driver");
        ((DruidDataSource) dataSource).setUrl("jdbc:mysql://localhost:3306/mytest?characterEncoding=utf8");
        ((DruidDataSource) dataSource).setUsername("root");
        ((DruidDataSource) dataSource).setPassword("123456");

        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    @Test
    public void testQuery(){
        List<Student> list = (List<Student>) jdbcTemplate.query("select * from student",new RowMapper(){
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAge(rs.getInt("age"));
                return s;
            }
        });
        System.out.println(list);
    }

    @Test
    public void testInsert(){
        Student s = new Student();
        s.setName("lucy");
        s.setAge(12);
        jdbcTemplate.update("insert into student (name,age) values (?,?)",s.getName(),s.getAge());
    }
}
