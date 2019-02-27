package com.xxx.jdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author sicwen
 * @date 2019/02/27
 */
public interface RowMapper {
    Object mapRow(ResultSet rs,int rowNum) throws SQLException;
}
