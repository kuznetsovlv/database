package com.github.kuznetsov.database;

import com.github.kuznetsov.database.connections.ConnectionFactory;
import com.github.kuznetsov.database.configs.DBConfig;
import com.github.kuznetsov.database.exceptions.DBExecutorBuildException;
import com.github.kuznetsov.database.exceptions.DBUsingClosedExecutor;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author leonid
 */
public class DBExecutor implements AutoCloseable{
    private Connection connection;
    private final String dataBaseName;
    private final String description;
    
    private boolean open;
    
    DBExecutor(DBConfig config) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, DBExecutorBuildException {
        try {
            this.connection = ConnectionFactory.createConnection(config, config.getAutoconnect());
        } catch (SQLException e) {
            if (config.getAutoconnect() && config.getForceconnect()) {
                this.connection = ConnectionFactory.createConnection(config);
                try {
                    forceConnect();
                } catch (DBUsingClosedExecutor ex) {
                    throw new DBExecutorBuildException(ex);
                }
            } else {
                throw e;
            }
        }
        
        this.dataBaseName = config.getDataBaseName();
        this.description = config.getDescription();
        this.open = true;
    }
    
    public int forceConnect() throws DBUsingClosedExecutor, SQLException{
        try {
            return connect();
        } catch (SQLException ex) {
            return createBase() + connect();
        }
    }
    
    public int connect() throws DBUsingClosedExecutor, SQLException {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        try(Statement statment = connection.createStatement()) {
            return statment.executeUpdate("use " + dataBaseName);
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public int createBase() throws DBUsingClosedExecutor, SQLException {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        try(Statement statment = connection.createStatement()) {
            return statment.executeUpdate("create database " + dataBaseName);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void close() throws Exception {
        open = false;
        connection.close();
    }

    
}
