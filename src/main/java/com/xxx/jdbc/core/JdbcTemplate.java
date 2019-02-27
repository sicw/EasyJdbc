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

    public Object query(String sql,RowMapper rowMapper,Object... args){
        return query(new SimplePreparedStatementCreator(sql),new ArgumentPreparedStatementSetter(args),rowMapper);
    }

    public int update(String sql, Object... args){
        return update(new SimplePreparedStatementCreator(sql),new ArgumentPreparedStatementSetter(args));
    }

    public Object query(PreparedStatementCreator psc, final PreparedStatementSetter pss, final RowMapper rowMapper) {
        class QueryPreparedStatementCallBack<T> implements PreparedStatementCallback{
            public Object doInPreparedStatement(PreparedStatement stmt) throws SQLException {
                pss.setValues(stmt);
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
        return execute(psc,new QueryPreparedStatementCallBack());
    }

    public int update(PreparedStatementCreator psc, final PreparedStatementSetter pss) {
        class UpdatePreparedStatementCallBack implements PreparedStatementCallback {
            public Integer doInPreparedStatement(PreparedStatement prestmt) throws SQLException {
                pss.setValues(prestmt);
                return prestmt.executeUpdate();
            }
        }
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
