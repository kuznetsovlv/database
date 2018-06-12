package com.github.kuznetsov.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author leonid
 */
public interface DBResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
