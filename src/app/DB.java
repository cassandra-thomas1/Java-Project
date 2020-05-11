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
                       returned.getInt("timeailve"), returned.getInt("shipsdestroyed")));
            }
            gameConn.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }


        return allScores;
    }

    static void add(Score s) throws SQLException {
        allScores.add(s);

        //INSERT INTO scores VALUES(500, "beep", 30, 3)
        gameConn = DriverManager.getConnection(url, user, password);

        String statement = "INSERT INTO scores" + " (score, username, timeailve, shipsdestroyed)"
                +" VALUES ("+ s.getScore() +", " + s.getPlayerName()
                + ", " + s.getTime() +", " + s.getShipsKilled() +")";

        Statement add = gameConn.createStatement();

        add.executeUpdate(statement);

        gameConn.close();
    }

    static void close() throws SQLException {
        gameConn.close();
    }
}