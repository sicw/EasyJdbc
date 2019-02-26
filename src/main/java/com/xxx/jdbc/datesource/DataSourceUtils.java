package com.xxx.jdbc.datesource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DataSourceUtils {
    public static Connection getConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

}
