import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }




    @Test
    void jump() {
        assertFalse(player.isJumping());
        player.jump();
        assertTrue(player.isJumping());
    }

    @Test
    void increaseJumpPower() {
        int initialJumpPowerMultiplier = player.getJumpPowerMultiplier();
        player.increaseJumpPower();
        assertEquals(initialJumpPowerMultiplier + 1, player.getJumpPowerMultiplier());
    }
}
