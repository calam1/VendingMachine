package managers.impl;

import domain.BrandsOfSoda;
import domain.SodaMachineSpecifications;
import exceptions.InvalidStateException;
import managers.IManageInventory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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
    public static final String EMPTY_STRING = "";
    public static final int ZERO = 0;

    private HashMap<BrandsOfSoda, Integer> inventory = new HashMap<BrandsOfSoda, Integer>();
    private SodaMachineSpecifications specifications;
    private final List<BrandsOfSoda> brandsOfSodas;

    public ManageInventory(SodaMachineSpecifications specifications, List<BrandsOfSoda> brandsOfSodas) {
        this.specifications = specifications;
        this.brandsOfSodas = brandsOfSodas;
    }

    public List<BrandsOfSoda> getBrandsOfSodas() {
        return brandsOfSodas;
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
        } else if (compared == 1) {
            decrementInventory(soda);
        }

        return EMPTY_STRING;
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
        if (brandsOfSodas == null || brandsOfSodas.size() == ZERO) {
            throw new InvalidStateException(INITIAL_STOCKING_SODA_ERROR);
        }

        if (brandsOfSodas.size() > specifications.getNumberOfSelectionButtons()) {
            throw new InvalidStateException(TOO_MANY_SODA_TYPES_FOR_AMOUNT_OF_SELECTION_BUTTONS);
        }

        for (BrandsOfSoda soda : brandsOfSodas) {
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
