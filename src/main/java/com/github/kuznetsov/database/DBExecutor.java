package com.github.kuznetsov.database;

import com.github.kuznetsov.database.connections.ConnectionFactory;
import com.github.kuznetsov.database.configs.DBConfig;
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
