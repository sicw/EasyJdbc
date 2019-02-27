package com.xxx.jdbc.core;

import com.xxx.jdbc.datesource.DataSourceUtils;
import com.xxx.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Object query(String sql, final RowMapper rowMapper, Object... args) {
        class QueryPreparedStatementCallBack<T> implements PreparedStatementCallback{
            public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException {
                ResultSet rs = stmt.executeQuery();
                int rowNum = 0;
                List<Object> results = new ArrayList<Object>();
                while(rs.next()) {
                    Object obj = (Object) rowMapper.mapRow(rs, ++rowNum);
                    results.add(obj);
                }
                return results;
            }
        }
        return execute(sql,new QueryPreparedStatementCallBack(),args);
    }

    public Integer update(String sql, Object... args) {
        class UpdatePreparedStatementCallBack implements PreparedStatementCallback {
            public Integer doInPreparedStatement(PreparedStatement prestmt) throws SQLException {
                return prestmt.executeUpdate();
            }
        }
        return (Integer) execute(sql,new UpdatePreparedStatementCallBack(),args);
    }

    public Object execute(String sql, PreparedStatementCallback action, Object... args){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataSourceUtils.getConnection(getDataSource());
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            return action.doInPreparedStatement(ps);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(conn);
            JdbcUtils.closeStatement(ps);
        }
        return null;
    }
}
