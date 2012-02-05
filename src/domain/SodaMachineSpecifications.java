package domain;

import exceptions.InvalidArgumentException;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 9:35 PM
 * To change this template use File | Settings | File Templates.
 */
public final class SodaMachineSpecifications {

    public static final int ZERO = 0;
    private final String SELECTION_MAXIMUM_ERROR_MESSAGE = "Zero or a negative number is not a valid input for" +
            " the creation of a vending machine";
    private final String INVALID_COMBINATION_ERROR = "You cannot have more number of selection buttons than the maximum " +
            "number of sodas the machine can hold";
    private final int maximumAmountOfSodasForMachine;
    private final int numberOfSelectionButtons;
    private final BigDecimal costOfSoda;

    public SodaMachineSpecifications(int maximumAmountOfSodasForMachine, int numberOfSelectionButtons, BigDecimal costOfSoda)
            throws InvalidArgumentException {
        if (maximumAmountOfSodasForMachine <= ZERO || numberOfSelectionButtons <= ZERO) {
            throw new InvalidArgumentException(SELECTION_MAXIMUM_ERROR_MESSAGE);
        }

        if (maximumAmountOfSodasForMachine < numberOfSelectionButtons) {
            throw new InvalidArgumentException(INVALID_COMBINATION_ERROR);
        }
        this.maximumAmountOfSodasForMachine = maximumAmountOfSodasForMachine;
        this.numberOfSelectionButtons = numberOfSelectionButtons;
        this.costOfSoda = costOfSoda;
    }

    public final int getMaximumAmountOfSodasForMachine() {
        return maximumAmountOfSodasForMachine;
    }

    public final int getNumberOfSelectionButtons() {
        return numberOfSelectionButtons;
    }

    public BigDecimal getCostOfSoda() {
        return costOfSoda;
    }

}
