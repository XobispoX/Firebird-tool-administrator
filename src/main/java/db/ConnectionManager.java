package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {

    private static ConnectionManager instance;

    // Guardar perfiles para multiples conexiones
    private Map<String, ConnectionProfile> profiles = new HashMap<>();
    private Map<String, Connection> connections = new HashMap<>();

    // Perfil activo 
    private String activeProfileName;

    private ConnectionManager() {}

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public boolean hasProfile(String name) {
        return profiles.containsKey(name);
    }

    public void addProfile(ConnectionProfile profile) {
        profiles.put(profile.getName(), profile);
    }

    public ConnectionProfile getProfile(String name) {
        return profiles.get(name);
    }

    public Set<String> getProfileNames() {
        return profiles.keySet();
    }

    // Devuelve la conexion
    public Connection getConnection(String name) throws SQLException {
        if (!connections.containsKey(name)) {
            ConnectionProfile profile = profiles.get(name);
            if (profile != null) {
                connections.put(name, FirebirdConnector.connect(profile));
            }
        }
        return connections.get(name);
    }

    //Logica para el perfil activo

    public void setActiveProfile(ConnectionProfile profile) throws SQLException {
        if (!profiles.containsKey(profile.getName())) {
            addProfile(profile);
        }

        activeProfileName = profile.getName();

        getConnection(activeProfileName);
    }

    public ConnectionProfile getActiveProfile() {
        if (activeProfileName == null) return null;
        return profiles.get(activeProfileName);
    }

    public Connection getActiveConnection() throws SQLException {
        if (activeProfileName == null) return null;
        return getConnection(activeProfileName);
    }

    public void closeActiveConnection() throws SQLException {
        if (activeProfileName != null &&
            connections.containsKey(activeProfileName)) {

            connections.get(activeProfileName).close();
            connections.remove(activeProfileName);
        }

        activeProfileName = null;
    }
}