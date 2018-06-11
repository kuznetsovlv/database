package com.github.kuznetsov.database;

import java.sql.Connection;

/**
 *
 * @author leonid
 */
class ConnectionFactory {
    public static Connection createConnection(DBConfig config) {
        
        switch(config.getType()) {
            case MySQL:
                return createMySQLConnection(config);
            default:
                return createDefaultConnection(config);
        }
    }
    
    private static Connection createMySQLConnection(DBConfig config) {
        return null;
    }
    
    private static Connection createDefaultConnection(DBConfig config) {
        return null;
    }
}
