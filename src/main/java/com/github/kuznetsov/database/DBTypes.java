package com.github.kuznetsov.database;

/**
 *
 * @author leonid
 */
public enum DBTypes {
    MySQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql", 3306, "MySQL data base")
    ;
    
    private final String driverName;
    private final String protocol;
    private final int defaultPort;
    private final String description;

    private DBTypes(String driverName, String protocol, int defaultPort, String description) {
        this.driverName = driverName;
        this.protocol = protocol;
        this.defaultPort = defaultPort;
        this.description = description;
    }
    
    public String getDriverName() {
        return driverName;
    }
    
    public String getProtocol() {
        return protocol;
    }
    
    public int getDefaultPort() {
        return defaultPort;
    }
    
    public String getDescription() {
        return description;
    }
}
