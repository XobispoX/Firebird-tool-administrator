/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author danie
 */
public class ConnectionProfile {
    private final String name;          
    private final String host;          
    private final String port;          
    private final String databasePath;  
    private final String user;         
    private final String password;     

    public ConnectionProfile(String name, String host, String port, String databasePath,
                             String user, String password) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.databasePath = databasePath;
        this.user = user;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {return "jdbc:firebirdsql://" + host + ":" + port + "/" + databasePath;}

    public String getHost() {return host;}

    public String getPort() {return port;}

    public String getDatabasePath() {return databasePath;}

    public String getUser() {return user;}

    public String getPassword() {return password;}

    @Override
    public String toString() {return name + " (" + databasePath + ")";}

    public static ConnectionProfile createNewProfile(String connectionName, String databaseName,
                                                     String user, String password) {
        String host = "localhost";
        String port = "3050";
        String basePath = "C:/Users/danie/OneDrive/Escritorio/Unitec/AdminFirebird/src/database/";
        String fullPath = basePath + databaseName + ".fdb";

        return new ConnectionProfile(connectionName, host, port, fullPath, user, password);
    }
}