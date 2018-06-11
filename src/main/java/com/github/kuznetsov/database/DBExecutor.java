package com.github.kuznetsov.database;

import java.sql.Connection;

/**
 *
 * @author leonid
 */
public class DBExecutor {
    private final Connection connection;
    private final String description;

    DBExecutor(DBConfig config) {
        this.connection = ConnectionFactory.createConnection(config);
        this.description = config.getDescription();
    }
    
    
}
