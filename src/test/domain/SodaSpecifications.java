package test.domain;

import domain.SodaMachineSpecifications;
import exceptions.InvalidArgumentException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 11:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SodaSpecifications {

    private static final BigDecimal EXPECTED_COST = new BigDecimal("0.50");
    private static final int MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_NEGATIVE_1 = -1;
    private static final int MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_0 = 0;
    private static final int MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_3 = 3;
    private static final int MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_10 = 10;
    private static final int NUMBER_OF_SELECTION_BUTTONS_0 = 0;
    private static final int NUMBER_OF_SELECTION_BUTTONS_2 = 2;
    private static final int NUMBER_OF_SELECTION_BUTTONS_4 = 4;

    @Test(expected = InvalidArgumentException.class)
    public void tryingToCreateASodaSpecificationWithZeroArgumentForMaximumSodasForMachineThrowInvalidArgumentException()
            throws exceptions.InvalidArgumentException {
        SodaMachineSpecifications specifications =
                new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_0, NUMBER_OF_SELECTION_BUTTONS_4, EXPECTED_COST);
    }

    @Test(expected = InvalidArgumentException.class)
    public void tryingToCreateAVendingMachineWithNegativeArgumentForMaximumSodasForMachineThrowInvalidArgumentException()
            throws exceptions.InvalidArgumentException {
        SodaMachineSpecifications specifications =
                new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_NEGATIVE_1, NUMBER_OF_SELECTION_BUTTONS_4, EXPECTED_COST);
    }

    @Test(expected = InvalidArgumentException.class)
    public void tryingToCreateASodaSpecificationWithZeroArgumentForNumbersOfButtonsThrowInvalidArgumentException()
            throws exceptions.InvalidArgumentException {
        SodaMachineSpecifications specifications =
                new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_10, NUMBER_OF_SELECTION_BUTTONS_0, EXPECTED_COST);
    }

    @Test(expected = InvalidArgumentException.class)
    public void tryingToCreateAVendingMachineWithNegativeArgumentForNumberOfButtonsThrowInvalidArgumentException()
            throws exceptions.InvalidArgumentException {
        SodaMachineSpecifications specifications =
                new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_NEGATIVE_1, NUMBER_OF_SELECTION_BUTTONS_4, EXPECTED_COST);
    }

    @Test(expected = InvalidArgumentException.class)
    public void whenSelectionIsLargerThanTheMaximumAmountOfSodasAMachineCanHoldThrowInvalidArgumentException()
            throws InvalidArgumentException {
        SodaMachineSpecifications specifications =
                new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_3, NUMBER_OF_SELECTION_BUTTONS_4, EXPECTED_COST);
    }

    @Test
    public void tryingToCreateASodaSpecification()
            throws exceptions.InvalidArgumentException {
        SodaMachineSpecifications specifications =
                new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_10, NUMBER_OF_SELECTION_BUTTONS_2, EXPECTED_COST);

        assertEquals(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_10, specifications.getMaximumAmountOfSodasForMachine());
        assertEquals(NUMBER_OF_SELECTION_BUTTONS_2, specifications.getNumberOfSelectionButtons());
    }

    @Test
    public void getCostOfSoda() {
        SodaMachineSpecifications specifications = null;

        try {
            specifications =
                    new SodaMachineSpecifications(MAXIMUM_NUMBER_OF_SODAS_FOR_MACHINE_10, NUMBER_OF_SELECTION_BUTTONS_2, EXPECTED_COST);
        } catch (InvalidArgumentException e) {
            fail();
        }

        BigDecimal cost = specifications.getCostOfSoda();
        assertEquals(EXPECTED_COST, cost);
    }
}
