package test;

import cards.BoostAttackCard;
import cards.ProtectCard;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class TestCards {
    @Test
    public void getBoostReturns3()
    {
        BoostAttackCard boostCard = new BoostAttackCard();
        assertEquals(3, boostCard.getBoost());
    }

    @Test
    public void getBoostNumberReturns2() {
        BoostAttackCard boostCard = new BoostAttackCard();
        assertEquals(2, boostCard.getNumber());
    }

    @Test
    public void getProtectNumberReturns1() {
        ProtectCard protectCard = new ProtectCard();
        assertEquals(1, protectCard.getNumber());
    }


}
