package managers.impl;

import domain.BrandsOfSoda;
import domain.Coins;
import exceptions.InvalidMoneyException;
import managers.IManageVendingMachineBalance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageVendingMachineBalance implements IManageVendingMachineBalance {

    public static final String MESSAGE_NEED_TO_INSERT_MORE_MONEY_PREFIX = "Please enter at least ";
    public static final String MESSAGE_DISPENSED_PREFIX = "Here is your ";
    public static final String MESSAGE_DISPENSED_OWE_CHANGE_SUFFIX = " This is your balance ";
    public static final String MESSAGE_PLEASE_INSERT_PROPER_AMOUNT_PREFIX = "Please enter ";
    public static final String CENTS = " cents";
    public static final String COIN_RETURN_MESSAGE = " If you want the balance please enter \"coin_return\".";
    private static final String INVALID_CURRENCY_MESSAGE = "Currency inserted is invalid";
    private static final BigDecimal BIG_DECIMAL_0_DOLLARS = new BigDecimal("0.00");
    private static final String INCORRECT_CURRENCY_INSERTED_MESSAGE = "Please enter only \"0.05\", \"0.10\", or \"0.25\"";
    private static final String MONEY_MESSAGE_FILLER = " money: ";
    private static final String ZERO_DOLLARS = "0.00";
    private static final String EMPTY_STRING = "";
    public static final BigDecimal BIG_DECIMAL_100 = new BigDecimal("100.00");

    private BigDecimal workingBalance;
    private BigDecimal machineBalance;
    private Set<String> validCoins;

    public ManageVendingMachineBalance() {
        validCoins = new HashSet<String>();
        validCoins.add(Coins.FIVE_CENTS.getStringRepresentation());
        validCoins.add(Coins.TEN_CENTS.getStringRepresentation());
        validCoins.add(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());

        workingBalance = BIG_DECIMAL_0_DOLLARS;
        machineBalance = BIG_DECIMAL_0_DOLLARS;
    }

    public void resetWorkingBalance() {
        this.workingBalance = BIG_DECIMAL_0_DOLLARS;
    }

    public void setWorkingBalance(BigDecimal amount) {
        workingBalance = amount;
    }

    public String acceptInsertedMoney(String money) throws InvalidMoneyException {
        if (money == null) throw new InvalidMoneyException(INVALID_CURRENCY_MESSAGE + MONEY_MESSAGE_FILLER + money);
        if (!validCoins.contains(money)) throw new InvalidMoneyException(INCORRECT_CURRENCY_INSERTED_MESSAGE);

        BigDecimal bigDecimalMoney = new BigDecimal(money);
        workingBalance = workingBalance.add(bigDecimalMoney);
        machineBalance = machineBalance.add(bigDecimalMoney);

        return workingBalance.toString();
    }

    public String calculateDepositedAmountAgainstCostOfSoda(BigDecimal costOfSoda, BrandsOfSoda soda, int compared, boolean hasStock) {
        if (workingBalance.equals(new BigDecimal(ZERO_DOLLARS)))
            return MESSAGE_PLEASE_INSERT_PROPER_AMOUNT_PREFIX + costOfSoda.toString() + CENTS;

        if (compared == 0) {
            if (hasStock) {
                resetWorkingBalance();
            }
        } else if (compared == -1) {
            BigDecimal differenceInPriceAndMoneyInserted = costOfSoda.subtract(workingBalance);

            if (hasStock) {
                return MESSAGE_NEED_TO_INSERT_MORE_MONEY_PREFIX + differenceInPriceAndMoneyInserted + CENTS;
            }
        } else {
            String msg = "";
            if (hasStock) {
                BigDecimal overage = workingBalance.subtract(costOfSoda);
                setWorkingBalance(overage);
                msg = MESSAGE_DISPENSED_PREFIX + soda.toString() + MESSAGE_DISPENSED_OWE_CHANGE_SUFFIX + overage.toString() +
                        CENTS + COIN_RETURN_MESSAGE;
            }

            return msg;
        }

        return EMPTY_STRING;
    }

    public Map<String, Integer> returnMoney() {
        int workingBal = workingBalance.multiply(BIG_DECIMAL_100).intValue();
        int quarterCount = workingBal / Coins.TWENTY_FIVE_CENTS.getIntegerRepresentation();

        int moduloDime = workingBal % Coins.TWENTY_FIVE_CENTS.getIntegerRepresentation();
        int dimeCount = moduloDime / Coins.TEN_CENTS.getIntegerRepresentation();

        int moduloNickel = moduloDime % Coins.TEN_CENTS.getIntegerRepresentation();
        int nickelCount = moduloNickel / Coins.FIVE_CENTS.getIntegerRepresentation();

        Map<String, Integer> mapOfReturnedMoney = new HashMap<String, Integer>();

        if (quarterCount > 0) {
            mapOfReturnedMoney.put(Coins.TWENTY_FIVE_CENTS.getStringRepresentation(), quarterCount);
        }

        if (dimeCount > 0) {
            mapOfReturnedMoney.put(Coins.TEN_CENTS.getStringRepresentation(), dimeCount);
        }

        if (nickelCount > 0) {
            mapOfReturnedMoney.put(Coins.FIVE_CENTS.getStringRepresentation(), nickelCount);
        }

        correctAllBalances(quarterCount, dimeCount, nickelCount);

        return mapOfReturnedMoney;
    }

    private void correctAllBalances(int quarterCount, int dimeCount, int nickelCount) {
        BigDecimal quarter = Coins.TWENTY_FIVE_CENTS.getBigDecimalRepresentation();
        BigDecimal dime = Coins.TEN_CENTS.getBigDecimalRepresentation();
        BigDecimal nickel = Coins.FIVE_CENTS.getBigDecimalRepresentation();

        BigDecimal quarterAmount = quarter.multiply(new BigDecimal(quarterCount));
        BigDecimal dimeAmount = dime.multiply(new BigDecimal(dimeCount));
        BigDecimal nickelAmount = nickel.multiply(new BigDecimal(nickelCount));

        machineBalance = machineBalance.subtract(quarterAmount).subtract(dimeAmount).subtract(nickelAmount);
        workingBalance = BIG_DECIMAL_0_DOLLARS;
    }

    public BigDecimal getMachineBalance() {
        return machineBalance;
    }

    public BigDecimal getWorkingBalance() {
        return workingBalance;
    }

}
