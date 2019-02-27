package com.xxx.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author sicwen
 * @date 2019/02/27
 */
public interface PreparedStatementCallback {
    Object doInPreparedStatement(PreparedStatement stmt) throws SQLException;
}