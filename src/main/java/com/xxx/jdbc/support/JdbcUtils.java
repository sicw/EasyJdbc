package com.xxx.jdbc.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class JdbcUtils {

    private static final Logger logger = LoggerFactory.getLogger(JdbcUtils.class);

    public static void closeConnection(Connection conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex){
                logger.debug("Could not close JDBC Connection", ex);
            } catch(Throwable ex){
                logger.debug("Unexpected exception on closing JDBC Connection", ex);
            }
        }
    }

    public static void closeStatement(Statement st){
        if (st != null) {
            try {
                st.close();
            } catch (Exception ex){
                logger.trace("Could not close JDBC Statement", ex);
            } catch (Throwable ex){
                logger.trace("Unexpected exception on closing JDBC Statement", ex);
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.trace("Could not close JDBC ResultSet", ex);
            }
            catch (Throwable ex) {
                logger.trace("Unexpected exception on closing JDBC ResultSet", ex);
            }
        }
    }
}
