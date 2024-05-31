
public class ScoreManager {
    private int score;


    public ScoreManager() {
        score = 0;
    }

    /**
     * Increments the score by 1.
     */
    public void increment() {
        score++;
    }

    /**
     * Returns the current score.
     *
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Resets the score to 0.
     */
    public void reset() {
        score = 0;
    }
}