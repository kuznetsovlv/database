package com.github.kuznetsov.database;

import com.github.kuznetsov.database.range.Range;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leonid
 */
class DBConfig {
    
    private static final Range availablePorts = new Range(0, 65535);
   
    private static final String PORT = "port";
    private static final String DRIVER_NAME = "driverName";
    private static final String PROTOCOL = "protocol";
    private static final String DB_NAME = "dataBaseName";
    private static final String DESCRIPTION = "description";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
   
    private int port;
    private String driverName;
    private String protocol;
    private String dataBaseName;
    private String description;
    private String userName;
    private String password;
    private final Map<String, Object> properties;
    private final DBTypes type;

    public DBConfig(DBTypes type) {
        this.properties = new HashMap<>();
        this.port = type.getDefaultPort();
        this.type = type;
    }
    
    public DBTypes getType() {
        return type;
    }

    public DBConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public int getPort() {
        return port;
    }

    public DBConfig setDriverName(String driverName) {
        this.driverName = driverName;
        return this;
    }

    public String getDriverName() {
        return driverName;
    }

    public DBConfig setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getProcol() {
        return protocol;
    }

    public DBConfig setDataBaseName (String dataBaseName) {
        this.dataBaseName = dataBaseName;
        return this;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public DBConfig setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DBConfig setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public DBConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public DBConfig setProperty(String key, Object value) {
        if(key != null) {
            switch (key) {
                case PORT:
                    return setPort(Integer.parseInt(value.toString()));
                case DRIVER_NAME:
                    return setDriverName(value.toString());
                case PROTOCOL:
                    return setProtocol(value.toString());
                case DB_NAME:
                    return setDataBaseName(value.toString());
                case DESCRIPTION:
                    return setDescription(value.toString());
                case USER_NAME:
                    return setUserName(value.toString());
                 case PASSWORD:
                     return setPassword(value.toString());
            }

             properties.put(key, value);
        }
        return this;
    }

    public Object getProperty(String key) {
        if (key == null) {
            return null;
        }

        switch(key) {
            case PORT:
                return getPort();
            case DRIVER_NAME:
                return getDriverName();
            case PROTOCOL:
                return getProcol();
            case DB_NAME:
                return getDataBaseName();
            case DESCRIPTION:
                return getDescription();
            case USER_NAME:
                return getUserName();
            case PASSWORD:
                return getPassword();
        }

        return properties.get(key);
    }

    private String[] getNecessary() {
        return new String[]{ DRIVER_NAME, PROTOCOL, DB_NAME, DESCRIPTION, USER_NAME, PASSWORD };
    }

    public boolean isConfigReady() {
        for(String key: getNecessary()) {
            if(getProperty(key) == null) {
                return false;
            }
        }
        return availablePorts.inRange(getPort());
    }
}
