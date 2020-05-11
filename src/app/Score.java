package app;

public class Score implements Comparable<Score>{
    private String playerName;
    private long score;
    private long time;
    private long shipsKilled;
    public Score(){
        playerName = "";
        score = 0;
        time = 0;
        shipsKilled = 0;
    }
    public Score(String playerName, long score, long time, long shipsKilled){
        setPlayerName(playerName);
        setScore(score);
        setTime(time);
        setShipsKilled(shipsKilled);
    }
    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
    public void setScore(long score){
        this.score = score;
    }
    public void setTime(long time){
        this.time = time;
    }
    public void setShipsKilled(long shipsKilled){
        this.shipsKilled = shipsKilled;
    }
    public String getPlayerName(){
        return playerName;
    }
    public long getScore(){ return score; }
    public long getTime(){
        return time;
    }
    public long getShipsKilled(){
        return shipsKilled;
    }

    @Override
    public int compareTo(Score scoreIn) {
        return Math.toIntExact(scoreIn.getScore() - score);
    }
}