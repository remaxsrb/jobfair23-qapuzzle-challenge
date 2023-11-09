package test;

import cards.AttackCard;
import cards.Card;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import player.Player;
import player.Shuffler;
import utility.SpecificDeckStructures;
import utility.Utility;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TestPlayer {

    @Mock
    private Shuffler mockShuffler;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void testTakeDamage(){
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.takeDamage(1);
        assertEquals(19, player.getHealth());
    }

    @Test
    public void testDrawCard() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.drawInitialCards();
        player.drawCard();
        assertEquals(7, player.getHand().size());
    }

    @Test
    public void testResetDamage() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.setDamage(3);
        assertEquals(3,player.getDamage());
        player.resetDamage();
        assertEquals(0,player.getDamage());
    }

    @Test
    public void testInitialDeckSizeIs25() {
    new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        assertEquals(25, player.getNumberOfCardsInDeck());
    }

    @Test
    public void testInitialDeckSizeIsNotBeLessThan25() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        assertNotEquals(24, player.getNumberOfCardsInDeck());
    }

    @Test
    public void testInitialDeckSizeIsNotBeGreaterThan25() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        assertNotEquals(26, player.getNumberOfCardsInDeck());
    }

    @Test
    public void testInitialHandSizeIs6() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.drawInitialCards();
        assertEquals(6, player.getNumberOfCardsInHand());
    }

    @Test
    public void testInitialHandSizeShouldNotBeGreaterThan6() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.drawInitialCards();
        assertNotEquals(7, player.getNumberOfCardsInHand());
    }

    @Test
    public void testInitialHandSizeShouldNotBeLessThan6() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.drawInitialCards();
        assertNotEquals(5, player.getNumberOfCardsInHand());
    }

    @Test
    public void whenDeckIsShuffledOrderOfCardsShouldChange()
    {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));

        List<Card> oldOrder = new ArrayList<>(player.getDeck());
        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));

        assertNotSame(oldOrder, player.getDeck());

    }

    @Test
    public void testChoiceOfBoostCardIncreasesAttackDamage() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getBoostStructure());

        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        player.playCard(2);
        assertEquals(3, player.getDamage());

    }


    @Test
    public void choiceOfAttackCardWithIndex3IncreasesAttackDamageBy3() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getAttackStructure());

        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        player.playCard(3);
        assertEquals(3, player.getDamage());
    }

    //There is no need to test any other Attack values than 3 because
    //All attack cards use the same card method to apply their effect

    @Test
    public void ifAPlayerHasCardNumber7InHandFindNumberInHandReturnsTrue () {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getAttackStructure());
        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        assertTrue(player.findNumberInHand(7));

    }

    @Test
    public void ifAPlayerDoesNotHaveCardNumber7InHandFindNumberInHandReturnsFalse () {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getBoostStructure());

        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();

        assertFalse(player.findNumberInHand(7));

    }

    @Test
    public void testCheckForProtectionCardIfPlayerHasOneInHandAndAttackCardDoesNotMatchOpponentsCard()
    {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getDefenceStructure());
        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();

        assertTrue(player.checkForProtectionPossibilitiesInHand(new AttackCard(5)));

    }

    @Test
    public void testCheckForProtectionCardIfPlayerDoesNotHaveOneInHandAndAttackCardMatchesTheOpponents()
    {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getAttackStructure());
        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();

        assertTrue(player.checkForProtectionPossibilitiesInHand(new AttackCard(5)));

    }

    @Test
    public void testCheckForProtectionCardIfPlayerDoesNotHaveOneInHandAndAttackCardDoesNotMatchTheOpponents()
    {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getBalancedStructure());
        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        assertFalse(player.checkForProtectionPossibilitiesInHand(new AttackCard(3)));

    }

    @Test
    public void testCardVanishesAfterUsage()
    {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getBoostStructure());

        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        player.playCard(2);

        assertEquals(5, player.getNumberOfCardsInHand());
    }

    @Test
    public void testUserIsNotifiedIfCardOutsideOfHandIsTriedInAttack() {

        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getBoostStructure());

        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        player.playCard(7);


        assertEquals("Card is not in hand", systemOutRule.getLog().trim());
    }

    @Test
    public void testUserIsNotifiedIfCardOutsideOfHandIsTriedInDefense() {

        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getAttackStructure());

        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        player.playCardInDefense(1);


        assertEquals("You don't have this card in your hand...\r\n".trim(), systemOutRule.getLog().trim());
    }

    @Test
    public void testUserIsNotifiedIfSuccessfullyDefended() {

        new Utility();
        Player player = new Player(20, Utility.generateCards(), mockShuffler);
        Mockito.when(mockShuffler.shuffleDeck(player.getDeck())).thenReturn(SpecificDeckStructures.getBalancedStructure());

        player.populateDeck(player.getShuffler().shuffleDeck(player.getDeck()));
        player.drawInitialCards();
        player.playCardInDefense(6);


        assertEquals(String.format("You've defended yourself! You've been attacked for %d damage and you've used special ability of Attacking card %d to deflect the attack\r\n", 6, 6).trim(), systemOutRule.getLog().trim());
    }

    @Test
    public void testUserHealthDoesNotDecreaseBelowZero() {
        new Utility();
        Player player = new Player(20, Utility.generateCards(), new Shuffler());
        player.takeDamage(21);

        assertNotEquals(-1, player.getHealth());
    }

}
