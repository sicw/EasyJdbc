package com.xxx.jdbc.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author sicwen
 * @date 2019/02/27
 */
public class ArgumentPreparedStatementSetter implements PreparedStatementSetter {

    private final Object[] args;

    public ArgumentPreparedStatementSetter(Object[] args){
        this.args = args;
    }

    public void setValues(PreparedStatement ps) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1,args[i]);
        }
    }
}