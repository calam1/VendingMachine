package test.domain;

import domain.BrandsOfSoda;
import domain.IAmAVendingMachine;
import domain.SodaMachineSpecifications;
import domain.impl.VendingMachine;
import exceptions.InvalidArgumentException;
import managers.IManageInventory;
import managers.impl.ManageInventory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SodaVendingMachine {

    private static final int MAXIMUM_NUMBER_OF_SODAS_THIS_MACHINE_CAN_HOLD_100 = 100;
    private static final int NUMBER_OF_SELECTIONS_FOR_A_MACHINE_4 = 4;
    private static final BigDecimal EXPECTED_COST = new BigDecimal("0.50");

    private SodaMachineSpecifications specifications;
    private IManageInventory inventoryManager;
    private List<BrandsOfSoda> listOfSodas;

    @Before
    public void setUp() {
        try {
            specifications =
                    new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_THIS_MACHINE_CAN_HOLD_100, NUMBER_OF_SELECTIONS_FOR_A_MACHINE_4, EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        listOfSodas = new ArrayList<BrandsOfSoda>();
        listOfSodas.add(BrandsOfSoda.COKE);
        listOfSodas.add(BrandsOfSoda.DIET_COKE);

        inventoryManager = new ManageInventory(specifications, listOfSodas);
    }

    @Test
    public void howManySelectButtonsAreThereOnTheMachine() {
        IAmAVendingMachine vendingMachine = null;

        vendingMachine = new VendingMachine(inventoryManager, null, null, specifications);

        int numberOfSelections = vendingMachine.getNumberOfSelectionButtons();

        assertEquals(4, numberOfSelections);
    }
}
