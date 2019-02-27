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

    public Object query(String sql, final RowMapper rowMapper, final Object... args) {
        class QueryPreparedStatementCallBack<T> implements PreparedStatementCallback{
            public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException {
                for (int i = 0; i < args.length; i++) {
                    stmt.setObject(i+1,args[i]);
                }
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
        PreparedStatementCreator psc = new SimplePreparedStatementCreator(sql);
        return execute(psc,new QueryPreparedStatementCallBack());
    }

    public int update(String sql, final Object... args) {
        class UpdatePreparedStatementCallBack implements PreparedStatementCallback {
            public Integer doInPreparedStatement(PreparedStatement prestmt) throws SQLException {
                for (int i = 0; i < args.length; i++) {
                    prestmt.setObject(i+1,args[i]);
                }
                return prestmt.executeUpdate();
            }
        }
        PreparedStatementCreator psc = new SimplePreparedStatementCreator(sql);
        return (Integer) execute(psc,new UpdatePreparedStatementCallBack());
    }

    public Object execute(PreparedStatementCreator psc, PreparedStatementCallback action){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DataSourceUtils.getConnection(getDataSource());
            ps = psc.createPreparedStatement(conn);
            return action.doInPreparedStatement(ps);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(conn);
            JdbcUtils.closeStatement(ps);
        }
        return null;
    }

    private static class SimplePreparedStatementCreator implements PreparedStatementCreator{

        private final String sql;

        public SimplePreparedStatementCreator(String sql){
            this.sql = sql;
        }

        public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
            return conn.prepareStatement(sql);
        }
    }
}
