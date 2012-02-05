package managers;

import domain.BrandsOfSoda;
import exceptions.InvalidArgumentException;
import exceptions.InvalidStateException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 6:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IManageInventory {

    void removeSpecifiedAmountOfInventoryBySodaBrand(BrandsOfSoda rootBeer, int amountToRemove);

    void decrementInventory(BrandsOfSoda soda);

    void restockAllSodaToMaxCount() throws InvalidStateException;

    void initialStockingOfMachine(List<BrandsOfSoda> brandsOfSodaList) throws InvalidArgumentException;

    boolean checkIfSodaInventoryIsGreaterThanZero(BrandsOfSoda soda);

    int getMaximumCapacityOfSodasForMachine();

    int getNumberOfSelectionButtons();

    int getCurrentInventoryForAParticularSelection(BrandsOfSoda brandsOfSoda);

    int calculateTheMaximumCapacityOfSodaPerSelection();

    int restockSpecificBrandOfSoda(BrandsOfSoda brandsOfSoda, int amountOfSelection);

    int restockSpecificBrandOfSodaToMaximumCapacity(BrandsOfSoda soda);

    BigDecimal getCostOfSoda();

    String dispenseSoda(BrandsOfSoda soda, int compared);

}
