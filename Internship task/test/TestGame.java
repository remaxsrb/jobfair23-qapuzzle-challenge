package test;

import game.Game;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import player.Player;
import player.Shuffler;
import utility.Utility;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGame {

    private Player player1;
    private Player player2;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    private void instantiatePlayers() {
        new Utility();
        player1 = new Player(20, Utility.generateCards());
        player1.populateDeck(player1.getShuffler().shuffleDeck(player1.getDeck()));

        player2 = new Player(20, Utility.generateCards());
        player2.populateDeck(player2.getShuffler().shuffleDeck(player2.getDeck()));
    }



    @Test
    public void testGameEndsWhenPlayerHealthDropsToZero() {

        instantiatePlayers();
        Game game = new Game(player1, player2);
        player1.takeDamage(20);
        game.startGame();
        assertEquals("Player 2 wins!".trim(), systemOutRule.getLog().trim());

        assertTrue(game.getGameEnded());

    }

    @Test
    public void testGameEndsWhenUserHasNoCardsInHandOrDeck() {

        instantiatePlayers();
        Game game = new Game(player1, player2);

        game.testCommandStartGameWithDeckAndHandSizeAtZero();
        assertEquals("Player 1's Turn\nYou lost all your cards... \r\n Player 2 wins!".trim(), systemOutRule.getLog().trim());

        assertTrue(game.getGameEnded());

    }


}
