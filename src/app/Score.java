package app;

public class Score implements Comparable<Score>{//implements Comparable so that we can use Collections.sort on the player class
    private String playerName;
    private long score;
    private long time;
    private long shipsKilled;
    //constructor initializes the a score object which contains the player's name, score, time played, and ships killed
    public Score(String playerName, long score, long time, long shipsKilled){
        setPlayerName(playerName);
        setScore(score);
        setTime(time);
        setShipsKilled(shipsKilled);
    }
    //precondition: none
    //postcondition: sets the player name
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    //precondition: none
    //postcondition: sets the player score
    public void setScore(long score){
        this.score = score;
    }
    //precondition: none
    //postcondition: sets the player time played
    public void setTime(long time){
        this.time = time;
    }
    //precondition: none
    //postcondition: sets the player number of ships killed
    public void setShipsKilled(long shipsKilled){
        this.shipsKilled = shipsKilled;
    }
    //precondition: none
    //postcondition: returns the player number of ships killed
    public String getPlayerName(){
        return playerName;
    }
    //precondition: none
    //postcondition: returns the player number of ships killed
    public long getScore(){ return score; }
    //precondition: none
    //postcondition: returns the player number of ships killed
    public long getTime(){
        return time;
    }
    //precondition: none
    //postcondition: returns the player number of ships killed
    public long getShipsKilled(){
        return shipsKilled;
    }

    @Override
    //precondition: none
    //postcondition: returns a negative integer if the passed in score is less than this score
    //  positive integer if the passed in score is greater, 0 if they are the same
    public int compareTo(Score scoreIn) {
        return Math.toIntExact(scoreIn.getScore() - score);
    }
}