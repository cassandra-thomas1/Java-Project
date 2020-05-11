package app;

public class Score {
    private String playerName;
    private int score;
    private int time;
    private int shipsKilled;
    public Score(String playerName, int score, int time, int shipsKilled){
        setPlayerName(playerName);
        setScore(score);
        setTime(time);
        setShipsKilled(shipsKilled);
    }
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    public void setScore(int score){
        this.playerName = playerName;
    }
    public void setTime(int time){
        this.time = time;
    }
    public void setShipsKilled(int shipsKilled){
        this.shipsKilled = shipsKilled;
    }
}
