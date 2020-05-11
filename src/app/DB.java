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

    //pre: Game needs to open database and access it's data
    //post: Returns an arraylist representing all the data in the DB
    static ArrayList<Score> access() throws SQLException {
        //these are the credentials necessary to access the DB
        url = "jdbc:mysql://localhost:3306/appdata";
        user = "root";
        password = "Password";

        try{
            //access the DB using parameters above
            gameConn = DriverManager.getConnection(url, user, password);
            myStatement = gameConn.createStatement();
            returned = myStatement.executeQuery("select * from appdata.scores");    //standard SQL query that grabs all the data from the table

            //iterate the DB file and creates new score instances
            while (returned.next()){
               allScores.add(new Score(returned.getString("username"), returned.getInt("score"),        //score class holds 1 row per instance
                       returned.getInt("timeailve"), returned.getInt("shipsdestroyed")));

            }
            gameConn.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }


        return allScores;
    }

    //pre: new score has been recorded in game
    //post: adds the score into the DB as well as the ArrayList holding it
    static void add(Score s) throws SQLException {
        allScores.add(s);

        gameConn = DriverManager.getConnection(url, user, password);

        String statement = "INSERT INTO scores" + " (score, username, timeailve, shipsdestroyed)"
                +" VALUES ("+ s.getScore() +", '" + s.getPlayerName()
                + "', " + s.getTime() +", " + s.getShipsKilled() +")";                                      //this string is a statement that adds a row into the table

        Statement add = gameConn.createStatement();

        add.executeUpdate(statement);

        gameConn.close();
    }

    //pre: the program is done using the DB
    //post: the connection to the DB is closed
    static void close() throws SQLException {
        gameConn.close();
    }
}