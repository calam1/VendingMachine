package domain;

import exceptions.InvalidMoneyException;
import exceptions.InvalidStateException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/4/12
 * Time: 7:40 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAmAVendingMachine {

    void restockSpecificSelection(BrandsOfSoda brandsOfSoda);

    void restockAllSelections() throws InvalidStateException;

    void restockSpecificSelectionWithAmount(BrandsOfSoda brandsOfSoda, int amount);

    int getNumberOfSelectionButtons();

    int getMaximumNumberOfCansAllowedInMachine();

    int calculateTheMaximumAmountOfProductPerSelection();

    String dispenseSoda(BrandsOfSoda soda);

    String insertCoin(String coin) throws InvalidMoneyException;

    String coinReturn();

    List<String> getTheBrandsOfSodaInTheMachine();

    List<String> getCommands();

}
