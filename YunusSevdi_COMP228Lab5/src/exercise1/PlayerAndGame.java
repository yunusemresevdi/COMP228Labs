package exercise1;

public class PlayerAndGame {
    private int playerGameId;
    private int playerId;
    private int gameId;
    private String playingDate;
    private int score;

    public PlayerAndGame(int playerGameId, int playerId, int gameId, String playingDate, int score) {
        this.playerGameId = playerGameId;
        this.playerId = playerId;
        this.gameId = gameId;
        this.playingDate = playingDate;
        this.score = score;
    }

    // Getters and Setters
    public int getPlayerGameId() {
        return playerGameId;
    }

    public void setPlayerGameId(int playerGameId) {
        this.playerGameId = playerGameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getPlayingDate() {
        return playingDate;
    }

    public void setPlayingDate(String playingDate) {
        this.playingDate = playingDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
