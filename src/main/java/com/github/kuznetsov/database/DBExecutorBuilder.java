package com.github.kuznetsov.database;

/**
 *
 * @author leonid
 */
public class DBExecutorBuilder {
    
    private final DBTypes type;
    DBConfig config;

    public DBExecutorBuilder(DBTypes type) {
        this.type = type;
        this.config = new DBConfig(type)
                .setPort(type.getDefaultPort())
                .setDriverName(type.getDriverName())
                .setProtocol(type.getProtocol())
                .setDescription(type.getDescription());
    }
    
    public DBExecutorBuilder setProperty(String key, Object value) {
        config.setProperty(key, value);
        return this;
    }
    
    public DBExecutorBuilder setPort(int port) {
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
}
