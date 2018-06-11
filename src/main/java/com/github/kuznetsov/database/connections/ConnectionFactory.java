package com.github.kuznetsov.database.connections;

import com.github.kuznetsov.database.configs.DBConfig;
import com.github.kuznetsov.url.URLBuilder;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author leonid
 */
public class ConnectionFactory {
    public static Connection createConnection(DBConfig config) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        return createConnection(config, false);
    }
    
    public static Connection createConnection(DBConfig config, boolean autoconnect) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        
        switch(config.getType()) {
            case MySQL:
                return createMySQLConnection(config, autoconnect);
            default:
                return createDefaultConnection(config, autoconnect);
        }
    }
    
    private static Connection createMySQLConnection(DBConfig config, boolean autoconnect) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        DriverManager.registerDriver((Driver) Class.forName(config.getDriverName()).newInstance());
        return DriverManager.getConnection(getUrl(config, true, autoconnect), config.getUserName(), config.getPassword());
    }
    
    private static Connection createDefaultConnection(DBConfig config, boolean autoconnect) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        DriverManager.registerDriver((Driver) Class.forName(config.getDriverName()).newInstance());
        return DriverManager.getConnection(getUrl(config, false, autoconnect), config.getUserName(), config.getPassword());
    }
    
    private static String getUrl(DBConfig config, boolean withUserInfo, boolean autoconnect) {
        URLBuilder url = new URLBuilder();
        
        url.setProtool(config.getProcol());
        url.setHost(config.getHost());
        url.setPort(config.getPort());
        
        if (autoconnect) {
            url.setPath(config.getDataBaseName());
        }
        
        if (withUserInfo) {
            url.addParameter("user", config.getUserName());
            url.addParameter("password", config.getPassword());
        }
        
        Map<String, Object> parameters = config.getProperties();
        
        if (parameters != null && !parameters.isEmpty()) {
            url.addParameters(parameters);
        }
        
        try {
            return url.build().toString();
        } catch (MalformedURLException ex) {
            return url.buildStringURL();
        }
    }
}
