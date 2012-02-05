package factory;

import domain.IAmAVendingMachine;
import domain.impl.VendingMachine;
import managers.IManageInventory;
import managers.IManageVendingMachineBalance;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/4/12
 * Time: 6:58 AM
 * To change this template use File | Settings | File Templates.
 */
public enum SodaMachineFactory {
    INSTANCE;

    public IAmAVendingMachine createSodaMachine(IManageInventory manageInventory, IManageVendingMachineBalance balance) {
        return new VendingMachine(manageInventory, balance);
    }
}
