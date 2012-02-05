package test.managers;

import domain.BrandsOfSoda;
import domain.SodaMachineSpecifications;
import exceptions.InvalidArgumentException;
import exceptions.InvalidMoneyException;
import exceptions.InvalidStateException;
import managers.IManageInventory;
import managers.IManageVendingMachineBalance;
import managers.impl.ManageInventory;
import managers.impl.ManageVendingMachineBalance;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryManager {

    private static final int MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_4 = 4;
    private static final int MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100 = 100;
    private static final int MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_200 = 200;
    private static final int NUMBER_OF_SELECTION_BUTTONS_2 = 2;
    private static final int NUMBER_OF_SELECTION_BUTTONS_3 = 3;
    private static final int NUMBER_OF_SELECTION_BUTTONS_4 = 4;

    private static final BigDecimal EXPECTED_COST = new BigDecimal("0.50");
    private static final String SPACE = "";
    private static final int COMPARED_GREATER_THAN = 1;
    private static final int COMPARED_LESS_THAN = -1;
    private static final int COMPARED_EQUAL = 0;
    private List<BrandsOfSoda> standardListOfSodas;

    @Before
    public void setup() {
        standardListOfSodas = new ArrayList<BrandsOfSoda>();
        standardListOfSodas.add(BrandsOfSoda.ROOT_BEER);
        standardListOfSodas.add(BrandsOfSoda.COKE);
        standardListOfSodas.add(BrandsOfSoda.DIET_COKE);
    }

    @Test
    public void remove10SodasThereShouldBe23Left() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_3,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory inventoryManager = new ManageInventory(specifications);

        try {
            inventoryManager.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        inventoryManager.removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda.ROOT_BEER, 10);
        int numberOfProductsAvailableForSelection = inventoryManager.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);

        final int EXPECTED = 23;

        assertEquals(EXPECTED, numberOfProductsAvailableForSelection);
    }

    @Test
    public void remove32SodasThereShouldBe1Left() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_3,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory inventoryManager = new ManageInventory(specifications);

        try {
            inventoryManager.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        inventoryManager.removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda.ROOT_BEER, 32);
        int numberOfProductsAvailableForSelection = inventoryManager.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);

        final int EXPECTED = COMPARED_GREATER_THAN;

        assertEquals(EXPECTED, numberOfProductsAvailableForSelection);
    }

    @Test
    public void remove30SodasThereShouldBe3Left() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_3,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory inventoryManager = new ManageInventory(specifications);

        try {
            inventoryManager.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        inventoryManager.removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda.ROOT_BEER, 30);
        int numberOfProductsAvailableForSelection = inventoryManager.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);

        final int EXPECTED = NUMBER_OF_SELECTION_BUTTONS_3;

        assertEquals(EXPECTED, numberOfProductsAvailableForSelection);
    }

    @Test
    public void calculateTheMaximumAmountOfSodaPerSelectionWhenMaxIs100AndSelectionIs4() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory inventoryManager = new ManageInventory(specifications);

        final int EXPECTED = 25;
        int maximumNumberOfSodasForTheMachine =
                inventoryManager.calculateTheMaximumCapacityOfSodaPerSelection();

        assertEquals(EXPECTED, maximumNumberOfSodasForTheMachine);
    }

    @Test
    public void calculateTheMaximumAmountOfSodaPerSelectionWhenMaxIs200AndSelectionIs4() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_200, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory inventoryManager = new ManageInventory(specifications);

        final int EXPECTED = 50;
        int maximumNumberOfSodasForTheMachine =
                inventoryManager.calculateTheMaximumCapacityOfSodaPerSelection();

        assertEquals(EXPECTED, maximumNumberOfSodasForTheMachine);
    }

    @Test
    public void calculateTheMaximumAmountOfSodaPerSelectionWhenMaxIs99AndSelectionIs4() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(97, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory inventoryManager = new ManageInventory(specifications);

        final int EXPECTED = 24;
        int maximumNumberOfSodasForTheMachine =
                inventoryManager.calculateTheMaximumCapacityOfSodaPerSelection();

        assertEquals(EXPECTED, maximumNumberOfSodasForTheMachine);
    }

    @Test(expected = InvalidArgumentException.class)
    public void whenYouAttemptToStockSodaWithNullListThrowInvalidArgumentException() throws InvalidArgumentException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);
        List<BrandsOfSoda> brandsOfSodaList = null;

        manageInventory.initialStockingOfMachine(brandsOfSodaList);
    }

    @Test(expected = InvalidArgumentException.class)
    public void whenYouAttemptToStockSodaWithNoSelectionButtonsThrowInvalidArgumentException() throws InvalidArgumentException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);
        List<BrandsOfSoda> brandsOfSodaList = new ArrayList<BrandsOfSoda>();

        manageInventory.initialStockingOfMachine(brandsOfSodaList);
    }

    @Test(expected = InvalidArgumentException.class)
    public void whenYouAttemptToStockSodaWithNegativeCountOFSelectionButtonsThrowInvalidArgumentException() throws InvalidArgumentException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);
        List<BrandsOfSoda> brandsOfSodaList = new ArrayList<BrandsOfSoda>();

        manageInventory.initialStockingOfMachine(brandsOfSodaList);
    }

    @Test(expected = InvalidArgumentException.class)
    public void whenYouStockMoreTypesOfSodaThanThereAreSelectionButtonsThrowInvalidArgumentException() throws InvalidArgumentException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_2,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        manageInventory.initialStockingOfMachine(standardListOfSodas);
    }

    @Test
    public void whenIHaveAMaxOf100SodasForAMachineAnd3TypesOfSodasIWillHave33SodasPerType() throws InvalidArgumentException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_3,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        manageInventory.initialStockingOfMachine(standardListOfSodas);

        int numberOfRootBeers = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);
        int numberOfCokes = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.COKE);
        int numberOfDietCokes = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.DIET_COKE);

        final int EXPECTED = 33;

        assertEquals(EXPECTED, numberOfRootBeers);
        assertEquals(EXPECTED, numberOfCokes);
        assertEquals(EXPECTED, numberOfDietCokes);
    }

    @Test
    public void whenIRestockASpecificSodaTheCountWillBeTheMaxPossibleForThatBrand() throws InvalidArgumentException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_3,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        manageInventory.initialStockingOfMachine(standardListOfSodas);

        int numberOfRootBeers = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);

        final int EXPECTED_ORIGINAL_STOCKING = 33;
        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfRootBeers);

        final int NUMBER_OF_SODAS_TO_REMOVE = 5;
        manageInventory.removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda.ROOT_BEER, NUMBER_OF_SODAS_TO_REMOVE);
        int numberOfRootBeersAfterReduction = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);

        final int EXPECTED_AFTER_REDUCTION = 28;
        assertEquals(EXPECTED_AFTER_REDUCTION, numberOfRootBeersAfterReduction);

        manageInventory.restockSpecificBrandOfSodaToMaximumCapacity(BrandsOfSoda.ROOT_BEER);
        int numberOfRootBeersAfterRestocking = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);

        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfRootBeersAfterRestocking);
    }

    @Test
    public void whenIRestockAllSodaTheCountWillBeTheMaxPossibleForThatBrand() throws InvalidArgumentException, InvalidStateException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_3,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        manageInventory.initialStockingOfMachine(standardListOfSodas);

        int numberOfRootBeers = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);
        int numberOfCokes = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.COKE);
        int numberOfDietCokes = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.DIET_COKE);

        final int EXPECTED_ORIGINAL_STOCKING = 33;
        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfRootBeers);
        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfCokes);
        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfDietCokes);

        manageInventory.removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda.ROOT_BEER, 5);
        manageInventory.removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda.COKE, 6);
        manageInventory.removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda.DIET_COKE, 7);

        int numberOfRootBeersAfterReduction = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);
        int numberOfCokesAfterReduction = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.COKE);
        int numberOfDietCokesAfterReduction = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.DIET_COKE);

        final int EXPECTED_AFTER_REDUCTION_ROOT_BEER = 28;
        final int EXPECTED_AFTER_REDUCTION_COKE = 27;
        final int EXPECTED_AFTER_REDUCTION_DIET_COKE = 26;

        assertEquals(EXPECTED_AFTER_REDUCTION_ROOT_BEER, numberOfRootBeersAfterReduction);
        assertEquals(EXPECTED_AFTER_REDUCTION_COKE, numberOfCokesAfterReduction);
        assertEquals(EXPECTED_AFTER_REDUCTION_DIET_COKE, numberOfDietCokesAfterReduction);

        manageInventory.restockAllSodaToMaxCount();

        int numberOfRootBeersAfterRestocking = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.ROOT_BEER);
        int numberOfCokesAfterRestocking = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.COKE);
        int numberOfDietCokesAfterRestocking = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.DIET_COKE);

        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfRootBeersAfterRestocking);
        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfCokesAfterRestocking);
        assertEquals(EXPECTED_ORIGINAL_STOCKING, numberOfDietCokesAfterRestocking);
    }

    @Test(expected = InvalidStateException.class)
    public void whenRestockingAllAndThereAreNoSodaTypesThrowInvalidStateException() throws InvalidStateException {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);
        manageInventory.restockAllSodaToMaxCount();
    }

    @Test
    public void decrementOneSodaFromInventoryOf25ResultingInventoryShouldBe24() {
        SodaMachineSpecifications specifications = null;
        IManageInventory manageInventory = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
            manageInventory = new ManageInventory(specifications);
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        manageInventory.decrementInventory(BrandsOfSoda.COKE);

        int inventoryForCoke = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.COKE);

        assertEquals(24, inventoryForCoke);
    }

    @Test
    public void checkIfInventoryIsGreaterThanZeroShouldBeTrue() {
        SodaMachineSpecifications specifications = null;
        IManageInventory manageInventory = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
            manageInventory = new ManageInventory(specifications);
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        boolean hasStock = manageInventory.checkIfSodaInventoryIsGreaterThanZero(BrandsOfSoda.COKE);

        assertTrue(hasStock);
    }


    @Test
    public void checkIfInventoryIsGreaterThanZeroShouldBeFalse() {
        SodaMachineSpecifications specifications = null;
        IManageInventory manageInventory = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_4, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
            manageInventory = new ManageInventory(specifications);
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        manageInventory.decrementInventory(BrandsOfSoda.COKE);

        int inventoryForCoke = manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.COKE);

        assertEquals(COMPARED_EQUAL, inventoryForCoke);

        boolean hasStock = manageInventory.checkIfSodaInventoryIsGreaterThanZero(BrandsOfSoda.COKE);

        assertFalse(hasStock);
    }

    @Test
    public void dispenseSodaEnteredNullValue() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        try {
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageVendingMachineBalance balance = new ManageVendingMachineBalance();

        String message = manageInventory.dispenseSoda(null, COMPARED_EQUAL);

        assertEquals(ManageInventory.MESSAGE_PLEASE_MAKE_A_SELECTION, message);
    }

    @Test
    public void dispenseSodaWhenIEnterTheBrand() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        try {
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageVendingMachineBalance balance = new ManageVendingMachineBalance();

        String message = manageInventory.dispenseSoda(BrandsOfSoda.COKE, -COMPARED_GREATER_THAN);

        assertEquals(SPACE, message);
    }

    @Test
    public void dispenseSodaWhenIEnterTheMoneyThenTheBrand() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        try {
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageVendingMachineBalance balance = new ManageVendingMachineBalance();
        try {
            balance.acceptInsertedMoney("0.25");
            balance.acceptInsertedMoney("0.25");
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageInventory.dispenseSoda(BrandsOfSoda.COKE, COMPARED_EQUAL);

        assertEquals(ManageInventory.MESSAGE_DISPENSED_PREFIX + BrandsOfSoda.COKE.toString(), message);
    }

    @Test
    public void dispenseSodaWhenIEnterTooLittleMoneyThenTheBrand() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        try {
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageVendingMachineBalance balance = new ManageVendingMachineBalance();
        try {
            balance.acceptInsertedMoney("0.25");
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageInventory.dispenseSoda(BrandsOfSoda.COKE, -COMPARED_GREATER_THAN);

        assertEquals(SPACE, message);
    }

    @Test
    public void dispenseSodaWhenIEnterTooLittleMoneyThenTheBrandThenEnterTheExactAmountOwed() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        try {
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageVendingMachineBalance balance = new ManageVendingMachineBalance();
        try {
            balance.acceptInsertedMoney("0.25");
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageInventory.dispenseSoda(BrandsOfSoda.COKE, -COMPARED_GREATER_THAN);

        assertEquals(SPACE, message);

        try {
            balance.acceptInsertedMoney("0.25");
        } catch (InvalidMoneyException e) {
            fail();
        }

        String messageLast = manageInventory.dispenseSoda(BrandsOfSoda.COKE, COMPARED_EQUAL);

        assertEquals(ManageInventory.MESSAGE_DISPENSED_PREFIX + BrandsOfSoda.COKE.toString(), messageLast);
    }

    @Test
    public void dispenseSodaWhenIEnterTooLittleMoneyThenTheBrandThenEnterMoreThanTheAmountOwed() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications = new SodaMachineSpecifications(MAXIMUM_AMOUNT_OF_SODAS_FOR_MACHINE_100, NUMBER_OF_SELECTION_BUTTONS_4,
                    EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageInventory manageInventory = new ManageInventory(specifications);

        try {
            manageInventory.initialStockingOfMachine(standardListOfSodas);
        } catch (InvalidArgumentException e) {
            fail();
        }

        IManageVendingMachineBalance balance = new ManageVendingMachineBalance();
        try {
            balance.acceptInsertedMoney("0.25");
        } catch (InvalidMoneyException e) {
            fail();
        }

        String message = manageInventory.dispenseSoda(BrandsOfSoda.COKE, COMPARED_LESS_THAN);

        assertEquals(SPACE, message);

        try {
            balance.acceptInsertedMoney("0.10");
            balance.acceptInsertedMoney("0.25");
        } catch (InvalidMoneyException e) {
            fail();
        }

        String messageLast = manageInventory.dispenseSoda(BrandsOfSoda.COKE, COMPARED_GREATER_THAN);

        assertEquals(SPACE, messageLast);
    }

}
