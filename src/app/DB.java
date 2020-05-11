package app;

import java.sql.*;

public class DB {
    private static Connection gameConn;

    static void access() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/appdata";
        String user = "root";
        String password = "Password";
        try{
            gameConn = DriverManager.getConnection(url, user, password);
            Statement myStatement = gameConn.createStatement();
            ResultSet returned = myStatement.executeQuery("select * from appdata.scores");

            while (returned.next()){
               //make new scores
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    static void close() throws SQLException {
        gameConn.close();
    }
}
