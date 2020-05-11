package app;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private static Connection gameConn;
    private static ArrayList<Score> allScores = new ArrayList<Score>();
    static ArrayList<Score> access() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/appdata";
        String user = "root";
        String password = "Password";
        try{
            //access the DB using parameters above
            gameConn = DriverManager.getConnection(url, user, password);
            Statement myStatement = gameConn.createStatement();
            ResultSet returned = myStatement.executeQuery("select * from appdata.scores");

            //iterate the DB file and creates new score instances
            while (returned.next()){
               allScores.add(new Score(returned.getString("username"), returned.getInt("score"),
                       returned.getInt("timealive"), returned.getInt("shipsdestroyed")));
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }


        return allScores;
    }

    static void close() throws SQLException {
        gameConn.close();
    }
}
