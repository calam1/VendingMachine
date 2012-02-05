package managers;

import domain.BrandsOfSoda;
import exceptions.InvalidMoneyException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/3/12
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IManageVendingMachineBalance {

    void resetWorkingBalance();

    void setWorkingBalance(BigDecimal amount);

    BigDecimal getMachineBalance();

    BigDecimal getWorkingBalance();

    String acceptInsertedMoney(String money) throws InvalidMoneyException;

    String calculateDepositedAmountAgainstCostOfSoda(BigDecimal costOfSoda, BrandsOfSoda soda, int compared, boolean hasStock);

    Map<String,Integer> returnMoney();

}
