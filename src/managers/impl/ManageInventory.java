package managers.impl;

import domain.BrandsOfSoda;
import domain.SodaMachineSpecifications;
import exceptions.InvalidArgumentException;
import exceptions.InvalidStateException;
import managers.IManageInventory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManageInventory implements IManageInventory {

    public static final String MESSAGE_DISPENSED_PREFIX = "Here is your ";
    public static final String MESSAGE_OUT_OF_STOCK = "Sorry the machine is out of ";
    public static final String MESSAGE_PLEASE_MAKE_A_SELECTION = "Please make a selection.";

    private static final String TOO_MANY_SODA_TYPES_FOR_AMOUNT_OF_SELECTION_BUTTONS = "You have selected more varieties of" +
            "sodas than there are available buttons";
    private static final String INITIAL_STOCKING_SODA_ERROR = "You must provide at least one variety" +
            "of soda";
    public static final String MESSAGE_ERROR = "Management of inventory has gone incredibly wrong, " +
            "application is in an invalid state.";
    public static final String EMPTY_STRING = "";
    public static final int ZERO = 0;

    private HashMap<BrandsOfSoda, Integer> inventory = new HashMap<BrandsOfSoda, Integer>();
    private SodaMachineSpecifications specifications;

    public ManageInventory(SodaMachineSpecifications specifications) {
        this.specifications = specifications;
    }

    public int getMaximumCapacityOfSodasForMachine() {
        return specifications.getMaximumAmountOfSodasForMachine();
    }

    public int getNumberOfSelectionButtons() {
        return specifications.getNumberOfSelectionButtons();
    }

    public int getCurrentInventoryForAParticularSelection(BrandsOfSoda brandsOfSoda) {
        return inventory.get(brandsOfSoda);
    }

    public int restockSpecificBrandOfSoda(BrandsOfSoda brandsOfSoda, int amountOfSelection) {
        int amount = inventory.get(brandsOfSoda);
        int amountToStock;
        int sumOfRequestedAmountAndCurrentInventory = amountOfSelection + amount;
        int maxAmount = calculateTheMaximumCapacityOfSodaPerSelection();

        if (sumOfRequestedAmountAndCurrentInventory > maxAmount) {
            amountToStock = maxAmount;
        } else {
            amountToStock = sumOfRequestedAmountAndCurrentInventory;
        }

        inventory.put(brandsOfSoda, amountToStock);

        return amountToStock;
    }

    public int calculateTheMaximumCapacityOfSodaPerSelection() {
        return specifications.getMaximumAmountOfSodasForMachine() / specifications.getNumberOfSelectionButtons();
    }

    public String dispenseSoda(BrandsOfSoda soda, int compared) {
        if (soda == null) return MESSAGE_PLEASE_MAKE_A_SELECTION;
        if (!checkIfSodaInventoryIsGreaterThanZero(soda)) return MESSAGE_OUT_OF_STOCK + soda.toString();

        String sodaDescription = soda.toString();

        if (compared == ZERO) {
            decrementInventory(soda);
            return MESSAGE_DISPENSED_PREFIX + sodaDescription;
        } else if (compared == 1){
            decrementInventory(soda);
        }

        return EMPTY_STRING;
    }

    public void initialStockingOfMachine(List<BrandsOfSoda> brandsOfSodaList)
            throws InvalidArgumentException {

        if (brandsOfSodaList == null || brandsOfSodaList.size() == ZERO)
            throw new InvalidArgumentException(INITIAL_STOCKING_SODA_ERROR);

        if (brandsOfSodaList.size() > specifications.getNumberOfSelectionButtons())
            throw new InvalidArgumentException(TOO_MANY_SODA_TYPES_FOR_AMOUNT_OF_SELECTION_BUTTONS);

        for (BrandsOfSoda soda : brandsOfSodaList) {
            inventory.put(soda, calculateTheMaximumCapacityOfSodaPerSelection());
        }
    }

    public void decrementInventory(BrandsOfSoda soda) {
        int count = inventory.get(soda);
        if (count > ZERO) {
            inventory.put(soda, --count);
        }
    }

    public boolean checkIfSodaInventoryIsGreaterThanZero(BrandsOfSoda soda) {
        int count = inventory.get(soda);
        if (count > ZERO) return true;

        return false;
    }

    public BigDecimal getCostOfSoda() {
        return specifications.getCostOfSoda();
    }

    public int restockSpecificBrandOfSodaToMaximumCapacity(BrandsOfSoda soda) {
        return restockSpecificBrandOfSoda(soda, calculateTheMaximumCapacityOfSodaPerSelection());
    }

    public void restockAllSodaToMaxCount() throws InvalidStateException {
        Set<BrandsOfSoda> sodas = inventory.keySet();

        if (sodas.isEmpty()) throw new InvalidStateException(MESSAGE_ERROR);

        for (BrandsOfSoda soda : sodas) {
            inventory.put(soda, calculateTheMaximumCapacityOfSodaPerSelection());
        }
    }

    public void removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda soda, int amountToRemove) {
        int currentAmountOfSodaType = inventory.get(soda);
        int newInventoryAmount = ZERO;

        if (amountToRemove < currentAmountOfSodaType) {
            newInventoryAmount = currentAmountOfSodaType - amountToRemove;
        }

        inventory.put(soda, newInventoryAmount);
    }

}
