package domain;

import exceptions.InvalidStateException;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/4/12
 * Time: 7:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAmAVendingMachine {
    int getNumberOfSelectionButtons();

    int getMaximumNumberOfCansAllowedInMachine();

    int calculateTheMaximumAmountOfProductPerSelection();

    void restockSpecificSelection(BrandsOfSoda brandsOfSoda);

    void restockAllSelections() throws InvalidStateException;

    void restockSpecificSelectionWithAmount(BrandsOfSoda brandsOfSoda, int amount);

    String dispenseSoda(BrandsOfSoda soda);

    String coinReturn();

}
