public class ScoreManager {
    private int score;

    public ScoreManager() {
        score = 0;
    }

    public void increment() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
