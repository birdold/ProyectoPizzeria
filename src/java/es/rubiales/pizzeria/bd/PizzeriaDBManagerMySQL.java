package es.rubiales.pizzeria.bd;

/*
 * Copyright (C) 2014 Javier García Escobedo (javiergarbedo.es)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */



import es.rubiales.pizzeria.beans.Pizzeria;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import es.rubiales.pizzeria.beans.Pizzeria;

/**
 *
 * @author Javier García Escobedo (javiergarbedo.es)
 * @version 0.1.0
 * @date 2014-02-26
 */
public class PizzeriaDBManagerMySQL {

    private static Connection connection;

    public static void connect(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        String strConection = "jdbc:mysql://" + databaseServer + "/" + databaseName;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(strConection, databaseUser, databasePassword);
            createTables();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1049) {
                Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                        "Database not found: " + strConection + " - " + databaseUser + "/" + databasePassword);
                createDatabase(databaseServer, databaseName, databaseUser, databasePassword);
                Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                        "Database created");
                try {
                    connection = DriverManager.getConnection(strConection, databaseUser, databasePassword);
                    createTables();
                } catch (SQLException ex1) {
                    Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean isConnected() {
        if(connection!=null) {
            return true;
        } else {
            return false;
        }
    }

    private static void createDatabase(String databaseServer, String databaseName, String databaseUser, String databasePassword) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String strConecction = "jdbc:mysql://" + databaseServer;
            connection = DriverManager.getConnection(strConecction, databaseUser, databasePassword);
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE " + databaseName;
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createTables() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS pizza ("
                    + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(50), "
                    + "price VARCHAR(100), "
                    + "comments TEXT, "
                    + "photo_file_name VARCHAR(50))";
            Statement statement = connection.createStatement();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertPizzeria(Pizzeria pizzeria) {

        try {
            String sql = "INSERT INTO pizza "
                    //No se incluye el ID, ya que es autonumérico
                    + "(name, price, comments, photo_file_name) "
                    + "VALUES ("
                    + "'" + pizzeria.getName() + "', "
                    + "'" + pizzeria.getPrice() + "', "
                    + "'" + pizzeria.getComments() + "', "
                    + "'" + pizzeria.getPhotoFileName() + "')";
            Statement statement = connection.createStatement();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Pizzeria> getPizzeriaList() {
        ArrayList<Pizzeria> pizzeriaList = new ArrayList();
        try {
            String sql = "SELECT * FROM pizza";
            Statement statement = connection.createStatement();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            boolean result = rs.isBeforeFirst();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
            while (rs.next()) {
                int columnIndex = 1;
                int id = rs.getInt(columnIndex++);
                String name = rs.getString(columnIndex++);
                String price = rs.getString(columnIndex++);
                String comments = rs.getString(columnIndex++);
                String photoFileName = rs.getString(columnIndex++);
                Pizzeria pizzeria = new Pizzeria(id, name, price, comments, photoFileName);
                pizzeriaList.add(pizzeria);
            }
            return pizzeriaList;
        } catch (SQLException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Pizzeria getPizzeriaByID(int pizzeriaId) {
        Pizzeria pizzeria = null;
        try {
            String sql = "SELECT * FROM pizza WHERE id=" + pizzeriaId;
            Statement statement = connection.createStatement();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            ResultSet rs = statement.executeQuery(sql);
            boolean result = rs.isBeforeFirst();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
            if (rs.next()) {
                int columnIndex = 1;
                int id = rs.getInt(columnIndex++);
                String name = rs.getString(columnIndex++);
                String price = rs.getString(columnIndex++);
                String comments = rs.getString(columnIndex++);
                String photoFileName = rs.getString(columnIndex++);
                pizzeria = new Pizzeria(id, name, price, comments, photoFileName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pizzeria;
    }

    public static void updatePizzeria(Pizzeria pizzeria) {
    try {
            String sql = "UPDATE pizza SET "
                    + "name='" + pizzeria.getName() + "', "
                    + "price='" + pizzeria.getPrice() + "', "
                    + "comments='" + pizzeria.getComments() + "', "
                    + "photo_file_name='" + pizzeria.getPhotoFileName() + "' "
                    + "WHERE id=" + pizzeria.getId();
            Statement statement = connection.createStatement();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void deletePizzeriaById(String id) {
        try {
            String sql = "DELETE FROM pizza WHERE id=" + id;
            Statement statement = connection.createStatement();
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "Executing SQL statement: " + sql);
            boolean result = statement.execute(sql);
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.FINE,
                    "SQL result: " + result);
        } catch (SQLException ex) {
            Logger.getLogger(PizzeriaDBManagerMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
