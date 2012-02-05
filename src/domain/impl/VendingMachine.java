package domain.impl;

import domain.BrandsOfSoda;
import domain.Coins;
import domain.IAmAVendingMachine;
import exceptions.InvalidStateException;
import managers.IManageInventory;
import managers.IManageVendingMachineBalance;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class VendingMachine implements IAmAVendingMachine {
    private static final String PREFIX_COIN_RETURN_MESSAGE = "We returned ";
    public static final String SPACER = " ";
    private final IManageInventory manageInventory;
    private IManageVendingMachineBalance balance;

    public VendingMachine(IManageInventory manageInventory, IManageVendingMachineBalance balance) {
        this.manageInventory = manageInventory;
        this.balance = balance;
    }

    public int getNumberOfSelectionButtons() {
        return manageInventory.getNumberOfSelectionButtons();
    }

    public int getMaximumNumberOfCansAllowedInMachine() {
        return manageInventory.getMaximumCapacityOfSodasForMachine();
    }

    public int calculateTheMaximumAmountOfProductPerSelection() {
        return manageInventory.calculateTheMaximumCapacityOfSodaPerSelection();
    }

    public void restockSpecificSelection(BrandsOfSoda brandsOfSoda) {
        manageInventory.restockSpecificBrandOfSodaToMaximumCapacity(brandsOfSoda);
    }

    public void restockAllSelections() throws InvalidStateException {
        manageInventory.restockAllSodaToMaxCount();
    }

    public void restockSpecificSelectionWithAmount(BrandsOfSoda brandsOfSoda, int amount) {
        manageInventory.restockSpecificBrandOfSoda(brandsOfSoda, amount);
    }

    public String dispenseSoda(BrandsOfSoda soda) {
        BigDecimal costOfSoda = manageInventory.getCostOfSoda();
        BigDecimal workingBalance = balance.getWorkingBalance();

        int compare = workingBalance.compareTo(costOfSoda);

        String messageFromAccounting = balance.calculateDepositedAmountAgainstCostOfSoda(costOfSoda, soda, compare);
        String messagesFromInventory = manageInventory.dispenseSoda(soda, compare);

        return messageFromAccounting + messagesFromInventory;
    }

    public String coinReturn() {
        Map<String, Integer> returnedCoins = balance.returnMoney();
        Set<String> coinDenominations = returnedCoins.keySet();
        StringBuilder coinReturnMessage = new StringBuilder();
        coinReturnMessage.append(PREFIX_COIN_RETURN_MESSAGE);

          for (String coin : coinDenominations) {
            if (coin.equals(Coins.TWENTY_FIVE_CENTS.getStringRepresentation())) {
                Integer count = returnedCoins.get(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
                coinReturnMessage.append(count + SPACER + Coins.TWENTY_FIVE_CENTS.getDescriptions() + SPACER);
            }

            if (coin.equals(Coins.TEN_CENTS.getStringRepresentation())) {
                Integer count = returnedCoins.get(Coins.TEN_CENTS.getStringRepresentation());
                coinReturnMessage.append(count + SPACER + Coins.TEN_CENTS.getDescriptions() + SPACER);
            }

            if (coin.equals(Coins.FIVE_CENTS.getStringRepresentation())) {
                Integer count = returnedCoins.get(Coins.FIVE_CENTS.getStringRepresentation());
                coinReturnMessage.append(count + SPACER + Coins.FIVE_CENTS.getDescriptions() + SPACER);
            }
        }

        return coinReturnMessage.toString();
    }

}
