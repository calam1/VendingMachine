package domain.impl;

import domain.BrandsOfSoda;
import domain.Coins;
import domain.Commands;
import domain.IAmAVendingMachine;
import domain.SodaMachineSpecifications;
import exceptions.InvalidMoneyException;
import exceptions.InvalidStateException;
import managers.IManageInventory;
import managers.IManageVendingMachineBalance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
    private static final String SPACER = " ";
    private final IManageInventory manageInventory;
    private IManageVendingMachineBalance balance;
    private final Commands commands;
    private final SodaMachineSpecifications specifications;

    public VendingMachine(IManageInventory manageInventory, IManageVendingMachineBalance balance,
                          Commands commands, SodaMachineSpecifications specifications) {
        this.manageInventory = manageInventory;
        this.balance = balance;
        this.commands = commands;
        this.specifications = specifications;
    }

    public int getNumberOfSelectionButtons() {
        return specifications.getNumberOfSelectionButtons();
    }

    public int getMaximumNumberOfCansAllowedInMachine() {
        return specifications.getMaximumAmountOfSodasForMachine();
    }

    public List<String> getTheBrandsOfSodaInTheMachine() {
        List<BrandsOfSoda> enumBrands = manageInventory.getBrandsOfSodas();
        List<String> brands = new ArrayList<String>();

        for (BrandsOfSoda soda : enumBrands) {
            brands.add(soda.toString());
        }

        return brands;
    }

    public int calculateTheMaximumAmountOfProductPerSelection() {
        return manageInventory.calculateTheMaximumCapacityOfSodaPerSelection();
    }

    public List<String> getCommands(){
        return commands.getCommands();
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
        boolean hasStock = manageInventory.checkIfSodaInventoryIsGreaterThanZero(soda);

        String messagesFromInventory = manageInventory.dispenseSoda(soda, compare);
        String messageFromAccounting = balance.calculateDepositedAmountAgainstCostOfSoda(costOfSoda, soda, compare, hasStock);

        return messageFromAccounting + " \n " + messagesFromInventory;
    }

    public String insertCoin(String coin) throws InvalidMoneyException {
        return balance.acceptInsertedMoney(coin);
    }

    public String coinReturn() {
        Map<String, Integer> returnedCoins = balance.returnMoney();
        Set<String> coinDenominations = returnedCoins.keySet();
        StringBuilder coinReturnMessage = new StringBuilder();
        coinReturnMessage.append(PREFIX_COIN_RETURN_MESSAGE);

        for (String coin : coinDenominations) {
            if (coin.equals(Coins.TWENTY_FIVE_CENTS.getStringRepresentation())) {
                processReturningQuarters(returnedCoins, coinReturnMessage);
            }

            if (coin.equals(Coins.TEN_CENTS.getStringRepresentation())) {
                processReturningDimes(returnedCoins, coinReturnMessage);
            }

            if (coin.equals(Coins.FIVE_CENTS.getStringRepresentation())) {
                processReturningNickels(returnedCoins, coinReturnMessage);
            }
        }

        return coinReturnMessage.toString();
    }

    private void processReturningNickels(Map<String, Integer> returnedCoins, StringBuilder coinReturnMessage) {
        Integer count = returnedCoins.get(Coins.FIVE_CENTS.getStringRepresentation());
        coinReturnMessage.append(count + SPACER + Coins.FIVE_CENTS.getDescriptions() + SPACER);
    }

    private void processReturningDimes(Map<String, Integer> returnedCoins, StringBuilder coinReturnMessage) {
        Integer count = returnedCoins.get(Coins.TEN_CENTS.getStringRepresentation());
        coinReturnMessage.append(count + SPACER + Coins.TEN_CENTS.getDescriptions() + SPACER);
    }

    private void processReturningQuarters(Map<String, Integer> returnedCoins, StringBuilder coinReturnMessage) {
        Integer count = returnedCoins.get(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        coinReturnMessage.append(count + SPACER + Coins.TWENTY_FIVE_CENTS.getDescriptions() + SPACER);
    }

}
