/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author danie
 */
public class FirebirdConnector {
    public static Connection connect(ConnectionProfile profile) throws SQLException {
        return DriverManager.getConnection(
                profile.getUrl(),
                profile.getUser(),
                profile.getPassword()
        );
    }
}
