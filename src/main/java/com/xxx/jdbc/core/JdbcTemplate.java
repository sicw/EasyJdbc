package com.xxx.jdbc.core;

import com.xxx.jdbc.datesource.DataSourceUtils;
import com.xxx.jdbc.domain.Student;
import com.xxx.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcTemplate {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void execute() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DataSourceUtils.getConnection(getDataSource());

            ps = conn.prepareStatement("select * from student where id = ?");
            ps.setInt(1,1);
            rs = ps.executeQuery();
            Student s = new Student();
            while(rs.next()){
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAge(rs.getInt("age"));
            }
            System.out.println(s);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(conn);
            JdbcUtils.closeStatement(ps);
            JdbcUtils.closeResultSet(rs);
        }
    }
}
