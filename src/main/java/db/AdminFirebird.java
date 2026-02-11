/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


/**
 *
 * @author danie
 */
//String url = "jdbc:firebirdsql://localhost:3050/C:/Users/danie/OneDrive/Escritorio/Unitec/AdminFirebird/src/database/USERSDB.FDB";
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.*;
import UI.MainFrame;


public class AdminFirebird {
        private static final String URL = "jdbc:firebirdsql://localhost:3050/C:/Users/danie/OneDrive/Escritorio/Unitec/AdminFirebird/src/database/USERSDB.FDB";
        private static final String USER = "SYSDBA";
        private static final String PASSWORD = "masterkey";
        
        public static void main(String[] args) {
             try {

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Si llega aquí, la conexión fue exitosa
            JOptionPane.showMessageDialog(null, "Conectado a Firebird");

            // Abrir MainFrame
            MainFrame main = new MainFrame(conn);
            main.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error de conexión:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        }
}
