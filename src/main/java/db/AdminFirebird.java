/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */


/**
 *
 * @author danie
 */
//String url = "jdbc:firebirdsql://localhost:3050/C:/Users/danie/OneDrive/Escritorio/Unitec/AdminFirebird/src/database/USERSDB.FDB";

import javax.swing.*;
import UI.LoginFrame;


public class AdminFirebird {
        
        public static void main(String[] args) {
             try {
            java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame().setVisible(true);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error de conexión:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        }
}
