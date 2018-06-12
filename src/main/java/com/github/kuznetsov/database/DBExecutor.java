package com.github.kuznetsov.database;

import com.github.kuznetsov.database.connections.ConnectionFactory;
import com.github.kuznetsov.database.configs.DBConfig;
import com.github.kuznetsov.database.exceptions.DBExecutorBuildException;
import com.github.kuznetsov.database.exceptions.DBUsingClosedExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

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
        
        try(Statement statement = connection.createStatement()) {
            return statement.executeUpdate("use " + dataBaseName);
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public int createBase() throws DBUsingClosedExecutor, SQLException {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        try(Statement statement = connection.createStatement()) {
            return statement.executeUpdate("create database " + dataBaseName);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void close() throws SQLException, DBUsingClosedExecutor {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        open = false;
        connection.close();
    }
    
    public <T> T executeQuery(String query, DBResultHandler<T> handler) throws SQLException, DBUsingClosedExecutor {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        try(Statement statement = connection.createStatement()) {
            statement.execute(query);
            
            try (ResultSet result = statement.getResultSet()) {
                return handler.handle(result);
            } catch (SQLException ex) {
                throw ex;
            }
        }catch (SQLException ex) {
            throw ex;
        }
    }

    public int executeUpdate(String update) throws SQLException, DBUsingClosedExecutor {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        try(Statement statement = connection.createStatement()) {
            return statement.executeUpdate(update);
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public int executeUpdate(Collection<String> updates) throws SQLException, DBUsingClosedExecutor {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        int changes = 0;
        
        try {
            connection.setAutoCommit(false);
            
            for(String update: updates) {
                try(Statement statement = connection.createStatement()) {
                    changes += statement.executeUpdate(update);
                } catch (SQLException ex) {
                    throw ex;
                }
            }
            
            return changes;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    
    public <S> int executeUpdate (String update, S data, DBPreparedExecutor<S> executor ) throws SQLException, DBUsingClosedExecutor {
        if(!open) {
            throw new DBUsingClosedExecutor();
        }
        
        try(PreparedStatement statement = connection.prepareStatement(update)) {
            return executor.execute(statement, data);
        } catch(SQLException ex) {
            throw ex;
        }
    }
}
