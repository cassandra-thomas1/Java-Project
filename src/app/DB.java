package app;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private static Connection gameConn;
    private static ArrayList<Score> allScores = new ArrayList<Score>();
    private static String url;
    private static  String user;
    private static  String password;

    private static Statement myStatement;
    private static ResultSet returned;

    static ArrayList<Score> access() throws SQLException {
        url = "jdbc:mysql://localhost:3306/appdata";
        user = "root";
        password = "Password";


        try{
            //access the DB using parameters above
            gameConn = DriverManager.getConnection(url, user, password);
            myStatement = gameConn.createStatement();
            returned = myStatement.executeQuery("select * from appdata.scores");

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

    static void add(Score s) throws SQLException {
        allScores.add(s);
        myStatement.executeUpdate("insert into scores values("+ s.getScore() +"," + s.getPlayerName()
                + "," + s.getTime() +"," + s.getShipsKilled() +")");
    }

    static void close() throws SQLException {
        gameConn.close();
    }
}
