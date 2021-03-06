package com.github.kuznetsov.database;

import com.github.kuznetsov.database.configs.DBConfig;
import com.github.kuznetsov.database.configs.DBMySQLConfig;
import com.github.kuznetsov.database.exceptions.DBExecutorBuildException;
import com.github.kuznetsov.tcp.TCPIncorrectPortException;
import java.sql.SQLException;

/**
 *
 * @author leonid
 */
public class DBExecutorBuilder {
    
    DBConfig config;

    public DBExecutorBuilder(DBTypes type) {
        
        //TODO: 
        switch (type) {
            case MySQL:
                this.config = new DBMySQLConfig(type);
            default:
                this.config = new DBConfig(type);
        }
    }
    
    public DBExecutorBuilder setProperty(String key, Object value) throws TCPIncorrectPortException {
        config.setProperty(key, value);
        return this;
    }
    
    public DBExecutorBuilder setHost(String host) {
        config.setHost(host);
        return this;
    }
    
    public DBExecutorBuilder setPort(int port) throws TCPIncorrectPortException {
        config.setPort(port);
        return this;
    }
    
    public DBExecutorBuilder setDataBaseName(String dataBaseName) {
        config.setDataBaseName(dataBaseName);
        return this;
    }
    
    public DBExecutorBuilder setUserName(String userName) {
        config.setUserName(userName);
        return this;
    }
    
    public DBExecutorBuilder setPassword(String password) {
        config.setPassword(password);
        return this;
    }
    
    public DBExecutorBuilder setAutoconnect(boolean autoconnect) {
        config.setAutoconnect(autoconnect);
        return this;
    }
    
    public DBExecutorBuilder setForceConnect(boolean forceConnect) {
        config.setForceConnect(forceConnect);
        return this;
    }
    
    public DBExecutor build() throws DBExecutorBuildException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (config.isConfigReady()) {
            return new DBExecutor(config);
        } else {
            throw new DBExecutorBuildException(config.getNeads());
        }
    }
}
