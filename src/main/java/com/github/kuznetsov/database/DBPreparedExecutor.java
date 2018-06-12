package com.github.kuznetsov.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author leonid
 */
public interface DBPreparedExecutor <T> {
    long[] execute(PreparedStatement statement, T data) throws SQLException;
}
