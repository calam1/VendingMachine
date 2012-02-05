package test.managers;

import domain.BrandsOfSoda;
import domain.Coins;
import exceptions.InvalidMoneyException;
import managers.IManageVendingMachineBalance;
import managers.impl.ManageVendingMachineBalance;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 11:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountManager {

    private static final BigDecimal EXPECTED_BALANCE_0 = new BigDecimal("0.00");
    private static final BigDecimal EXPECTED_BALANCE_50_CENTS = new BigDecimal("0.50");
    private static final BigDecimal EXPECTED_BALANCE_65_CENTS = new BigDecimal("0.65");
    //    private static final String FIVE_CENTS = "0.05";
//    private static final String TEN_CENTS = "0.10";
//    private static final String TWENTY_FIVE_CENTS = "0.25";
    private static final String FIFTY_CENTS = "0.50";
    private static final String SIX_CENTS = "0.06";
    private static final String SIXTY_CENTS = "0.60";
    private static final String SIXTY_FIVE_CENTS = "0.65";
    private static final String EMPTY_SPACE = "";
    private static final String EMPTY_SPACES = " ";
    private static final String ALPHA = "ABC ";
    private static final String SYMBOLS = "##$%";
    private static final String ZERO_STRING = "0";
    private static final BigDecimal COST_OF_SODA = new BigDecimal("0.50");
    private static final String FIFTEEN_CENTS = "0.15";
    private static final int ZERO = 0;
    private static final int NEGATIVE_ONE = -1;
    private static final int ONE = 1;

    IManageVendingMachineBalance manageVendingMachineBalance;

    @Before
    public void setup() {
        manageVendingMachineBalance = new ManageVendingMachineBalance();
    }

    @Test(expected = InvalidMoneyException.class)
    public void whenNullIsPassedInAsMoneyThrowInvalidCurrencyException() throws InvalidMoneyException {
        String money = null;
        manageVendingMachineBalance.acceptInsertedMoney(money);
    }

    @Test(expected = InvalidMoneyException.class)
    public void whenZeroIsPassedInAsMoneyInvalidMoneyExceptionWillBeThrown() throws InvalidMoneyException {
        String money = ZERO_STRING;
        manageVendingMachineBalance.acceptInsertedMoney(money);
    }

    @Test
    public void when50CentsIsPassedInAsMoneyBalanceWillShow50Cents() throws InvalidMoneyException {
        String balanceFirstDrop = manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());

        assertEquals(Coins.TWENTY_FIVE_CENTS.getStringRepresentation(), balanceFirstDrop);

        String balanceSecondDrop = manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        assertEquals(FIFTY_CENTS, balanceSecondDrop);

        BigDecimal balance = manageVendingMachineBalance.getMachineBalance();
        assertEquals(EXPECTED_BALANCE_50_CENTS, balance);

        String balanceThirdDrop = manageVendingMachineBalance.acceptInsertedMoney(Coins.TEN_CENTS.getStringRepresentation());
        assertEquals(SIXTY_CENTS, balanceThirdDrop);

        String balanceFourthDrop = manageVendingMachineBalance.acceptInsertedMoney(Coins.FIVE_CENTS.getStringRepresentation());
        assertEquals(SIXTY_FIVE_CENTS, balanceFourthDrop);

        BigDecimal balanceFinal = manageVendingMachineBalance.getMachineBalance();
        assertEquals(EXPECTED_BALANCE_65_CENTS, balanceFinal);
    }

    @Test
    public void whenTheCorrectMoneyIsInsertedReturnTrue() {
        try {
            manageVendingMachineBalance.acceptInsertedMoney(Coins.FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TEN_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        } catch (InvalidMoneyException e) {
            fail();
        }
    }

    @Test(expected = InvalidMoneyException.class)
    public void whenTheIncorrectMoneyIsInsertedThrowInvalidMoneyException() throws InvalidMoneyException {
        manageVendingMachineBalance.acceptInsertedMoney(SIX_CENTS);
    }

    @Test(expected = InvalidMoneyException.class)
    public void whenEmptySpaceIsInsertedThrowInvalidMoneyException() throws InvalidMoneyException {
        manageVendingMachineBalance.acceptInsertedMoney(EMPTY_SPACE);
    }

    @Test(expected = InvalidMoneyException.class)
    public void whenSpaceIsInsertedThrowInvalidMoneyException() throws InvalidMoneyException {
        manageVendingMachineBalance.acceptInsertedMoney(EMPTY_SPACES);
    }

    @Test(expected = InvalidMoneyException.class)
    public void whenAlphaIsInsertedThrowInvalidMoneyException() throws InvalidMoneyException {
        manageVendingMachineBalance.acceptInsertedMoney(ALPHA);
    }

    @Test(expected = InvalidMoneyException.class)
    public void whenSymbolsIsInsertedThrowInvalidMoneyException() throws InvalidMoneyException {
        manageVendingMachineBalance.acceptInsertedMoney(SYMBOLS);
    }

    @Test
    public void noMoneyDepositedShouldAskFor50Cents() {
        IManageVendingMachineBalance manageVendingMachineBalance = new ManageVendingMachineBalance();
        String message = manageVendingMachineBalance.calculateDepositedAmountAgainstCostOfSoda(COST_OF_SODA, BrandsOfSoda.COKE, -1);

        assertEquals(ManageVendingMachineBalance.MESSAGE_PLEASE_INSERT_PROPER_AMOUNT_PREFIX + COST_OF_SODA +
                ManageVendingMachineBalance.CENTS, message);
    }

    @Test
    public void exactChangeDepositedShouldGetNoMessages() {
        IManageVendingMachineBalance manageVendingMachineBalance = new ManageVendingMachineBalance();

        try {
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageVendingMachineBalance.calculateDepositedAmountAgainstCostOfSoda(COST_OF_SODA, BrandsOfSoda.COKE, ZERO);

        assertEquals("", message);
    }

    @Test
    public void notEnoughMoneyDepositedShouldGetMessageForMoreMoney() {
        IManageVendingMachineBalance manageVendingMachineBalance = new ManageVendingMachineBalance();

        try {
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TEN_CENTS.getStringRepresentation());
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageVendingMachineBalance.calculateDepositedAmountAgainstCostOfSoda(COST_OF_SODA, BrandsOfSoda.COKE, NEGATIVE_ONE);

        assertEquals(ManageVendingMachineBalance.MESSAGE_NEED_TO_INSERT_MORE_MONEY_PREFIX + FIFTEEN_CENTS +
                ManageVendingMachineBalance.CENTS, message);
    }

    @Test
    public void paidTooMuchForSodaShouldGetChangeBackPaid60CentsGetsBack10Cents() {
        IManageVendingMachineBalance manageVendingMachineBalance = new ManageVendingMachineBalance();

        try {
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TEN_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageVendingMachineBalance.calculateDepositedAmountAgainstCostOfSoda(COST_OF_SODA, BrandsOfSoda.COKE, ONE);

        assertEquals(ManageVendingMachineBalance.MESSAGE_DISPENSED_PREFIX + BrandsOfSoda.COKE.toString() +
                ManageVendingMachineBalance.MESSAGE_DISPENSED_OWE_CHANGE_SUFFIX + Coins.TEN_CENTS.getStringRepresentation() +
                ManageVendingMachineBalance.CENTS, message);
    }

    @Test
    public void exactChangeDepositedShouldGetNoMessagesAndWorkingBalanceShouldBeZero() {
        IManageVendingMachineBalance manageVendingMachineBalance = new ManageVendingMachineBalance();

        try {
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageVendingMachineBalance.calculateDepositedAmountAgainstCostOfSoda(COST_OF_SODA, BrandsOfSoda.COKE, ZERO);

        assertEquals("", message);

        BigDecimal workingBalance = manageVendingMachineBalance.getWorkingBalance();

        assertEquals(EXPECTED_BALANCE_0, workingBalance);
    }

    @Test
    public void paidTooMuchForSodaShouldGetChangeBackPaid60CentsGetsBack10CentsAndMachineBalanceShouldBeFiftyCents() {
        IManageVendingMachineBalance manageVendingMachineBalance = new ManageVendingMachineBalance();

        try {
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TEN_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageVendingMachineBalance.calculateDepositedAmountAgainstCostOfSoda(COST_OF_SODA, BrandsOfSoda.COKE, ONE);

        assertEquals(ManageVendingMachineBalance.MESSAGE_DISPENSED_PREFIX + BrandsOfSoda.COKE.toString() +
                ManageVendingMachineBalance.MESSAGE_DISPENSED_OWE_CHANGE_SUFFIX + Coins.TEN_CENTS.getStringRepresentation() +
                ManageVendingMachineBalance.CENTS, message);

        BigDecimal machineBalance = manageVendingMachineBalance.getMachineBalance();

        assertEquals(EXPECTED_BALANCE_50_CENTS, machineBalance);
    }

    @Test
    public void returnMoneyWhenCoinReturnIsEnteredReturn65Cents() {
        IManageVendingMachineBalance manageVendingMachineBalance = new ManageVendingMachineBalance();

        try {
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TEN_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.FIVE_CENTS.getStringRepresentation());
            manageVendingMachineBalance.acceptInsertedMoney(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        } catch (InvalidMoneyException e) {
            fail();
        }

        Map<String, Integer> returnedCoins = manageVendingMachineBalance.returnMoney();

        Set<String> coinDenominations = returnedCoins.keySet();
        for (String coin : coinDenominations) {
            if (coin.equals(Coins.TWENTY_FIVE_CENTS.getStringRepresentation())) {
                Integer count = returnedCoins.get(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
                assertEquals(2, count.intValue());
            }

            if (coin.equals(Coins.TEN_CENTS.getStringRepresentation())) {
                Integer count = returnedCoins.get(Coins.TEN_CENTS.getStringRepresentation());
                assertEquals(1, count.intValue());
            }

            if (coin.equals(Coins.FIVE_CENTS.getStringRepresentation())) {
                Integer count = returnedCoins.get(Coins.FIVE_CENTS.getStringRepresentation());
                assertEquals(1, count.intValue());
            }
        }
    }

}