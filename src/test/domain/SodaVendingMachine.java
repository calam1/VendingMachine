package test.domain;

import domain.IAmAVendingMachine;
import domain.SodaMachineSpecifications;
import domain.impl.VendingMachine;
import exceptions.InvalidArgumentException;
import managers.IManageInventory;
import managers.impl.ManageInventory;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

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

    @Before
    public void setUp() {
        try {
            specifications =
                    new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_THIS_MACHINE_CAN_HOLD_100, NUMBER_OF_SELECTIONS_FOR_A_MACHINE_4, EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        inventoryManager = new ManageInventory(specifications);
    }

    @Test
    public void howManySelectButtonsAreThereOnTheMachine() {
        IAmAVendingMachine IAmAVendingMachine = null;

        IAmAVendingMachine = new VendingMachine(inventoryManager, null);

        int numberOfSelections = IAmAVendingMachine.getNumberOfSelectionButtons();

        assertEquals(4, numberOfSelections);
    }

}
