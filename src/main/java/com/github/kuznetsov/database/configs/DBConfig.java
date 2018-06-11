package com.github.kuznetsov.database.configs;

import com.github.kuznetsov.database.DBTypes;
import com.github.kuznetsov.tcp.TCPIncorrectPortException;
import com.github.kuznetsov.tcp.TCPPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leonid
 */
public class DBConfig {   
    private static final String PORT = "port";
    private static final String DRIVER_NAME = "driverName";
    private static final String PROTOCOL = "protocol";
    private static final String HOST = "host";
    private static final String DB_NAME = "dataBaseName";
    private static final String DESCRIPTION = "description";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
   
    private TCPPort port;
    private String driverName;
    private String protocol;
    private String host;
    private String dataBaseName;
    private String description;
    private String userName;
    private String password;
    private boolean autoconnect;
    private boolean forceConnect;
    
    private final Map<String, Object> properties;
    private final DBTypes type;

    public DBConfig(DBTypes type) {
        this.properties = new HashMap<>();
        this.type = type;
        
        try {
            this.port = new TCPPort(type.getDefaultPort());
        } catch (TCPIncorrectPortException ex) {
            this.port = null;
        }
        
        this.driverName = type.getDriverName();
        this.protocol = type.getProtocol();
        this.description = type.getDescription();
    }
    
    public DBTypes getType() {
        return type;
    }

    public DBConfig setPort(int port) throws TCPIncorrectPortException {
        this.port = new TCPPort(port);
        return this;
    }
    
    public DBConfig setPort(TCPPort port) {
        this.port = port;
        return this;
    }

    public TCPPort getPort() {
        return port;
    }
    
    public int getPortValue() {
        return port.value();
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
    
    public DBConfig setHost(String host) {
        this.host = host;
        return this;
    }
    
    public String getHost() {
        return host;
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

    public DBConfig setProperty(String key, Object value) throws TCPIncorrectPortException {
        if(key != null) {
            switch (key) {
                case PORT:
                    if (value instanceof TCPPort) {
                        return setPort((TCPPort) value);
                    } else {
                        return setPort(Integer.parseInt(value.toString()));
                    }
                case DRIVER_NAME:
                    return setDriverName(value.toString());
                case PROTOCOL:
                    return setProtocol(value.toString());
                case HOST:
                    return setHost(value.toString());
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
            case HOST:
                return getHost();
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
    
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    public DBConfig setAutoconnect(boolean autoconnect) {
        this.autoconnect = autoconnect;
        return this;
    }
    
    public boolean getAutoconnect() {
        return autoconnect;
    }
    
    public DBConfig setForceConnect(boolean forceConnect) {
        this.forceConnect = forceConnect;
        if (forceConnect) {
            return setAutoconnect(true);
        }
        return this;
    }
    
    public boolean getForceconnect() {
        return forceConnect;
    }

    private String[] getNecessary() {
        return new String[]{
            PORT,
            DRIVER_NAME,
            PROTOCOL,
            HOST,
            DB_NAME,
            DESCRIPTION,
            USER_NAME,
            PASSWORD
        };
    }

    public boolean isConfigReady() {
        for(String key: getNecessary()) {
            if(getProperty(key) == null) {
                return false;
            }
        }
        return true;
    }
    
    public List<String> getNeads() {
        List<String> neads = new ArrayList<>();
        
        for(String key: getNecessary()) {
            if(getProperty(key) == null) {
                neads.add(key);
            }
        }
        
        return neads;
    }
}
