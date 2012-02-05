import domain.BrandsOfSoda;
import domain.Coins;
import domain.Commands;
import domain.IAmAVendingMachine;
import domain.SodaMachineSpecifications;
import domain.impl.CommandImpl;
import exceptions.InvalidArgumentException;
import exceptions.InvalidMoneyException;
import exceptions.InvalidStateException;
import factory.SodaMachineFactory;
import managers.IManageInventory;
import managers.IManageVendingMachineBalance;
import managers.impl.ManageInventory;
import managers.impl.ManageVendingMachineBalance;

import java.io.Console;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/4/12
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */

public final class VendingMachineConsole {
    private static final String fNEW_LINE = System.getProperty("line.separator");
    public static final String HELP = "help";
    public static final String REFUND = "refund";
    public static final String COIN_RETURN = "coin_return";

    public static final void main(String... aArgs) {
        Console console = System.console();

        String createDefaultVendingMachineResponse = console.readLine("Create a default vending machine? Please enter YES or NO: ");
        console.printf(fNEW_LINE);
        String numberOfSelectionButtons = null;
        String capacityNumber = null;
        List<BrandsOfSoda> userSelectedEntries = new ArrayList<BrandsOfSoda>();
        List<String> brandsOfSodaStringValues;

        while (!createDefaultVendingMachineResponse.equalsIgnoreCase("yes") && !createDefaultVendingMachineResponse.equalsIgnoreCase("no")) {
            createDefaultVendingMachineResponse = console.readLine("I do not understand the response, please enter YES or NO: ");
            console.printf(fNEW_LINE);
        }

        if (createDefaultVendingMachineResponse.equalsIgnoreCase("yes")) {
            console.printf("OK creating a default machine with 4 selection buttons and stocking it with 25 cans each of Coke, " +
                    "Diet Coke, Root Beer and Iced Tea. ");
            console.printf(fNEW_LINE);

            numberOfSelectionButtons = "4";
            capacityNumber = "100";
            userSelectedEntries.add(BrandsOfSoda.COKE);
            userSelectedEntries.add(BrandsOfSoda.DIET_COKE);
            userSelectedEntries.add(BrandsOfSoda.ROOT_BEER);
            userSelectedEntries.add(BrandsOfSoda.ICED_TEA);

            brandsOfSodaStringValues = new ArrayList<String>();

            for (BrandsOfSoda soda : userSelectedEntries) {
                brandsOfSodaStringValues.add(soda.toString());
            }
        } else {
            console.printf("OK, we are going to go through the steps of creating a soda vending machine. You will answer a " +
                    "series of questions to build the machine out. ");
            console.printf(fNEW_LINE);

            numberOfSelectionButtons = console.readLine("How many selection buttons do you want on the machine? Please " +
                    "enter a number between 1 and 4. ");
            console.printf(fNEW_LINE);

            while (!isNumeric(numberOfSelectionButtons)) {
                numberOfSelectionButtons = console.readLine("That is an invalid response.  Please enter a number between 1 and 4. ");
                console.printf(fNEW_LINE);
            }

            while (!isValidNumberForRangeProvided(numberOfSelectionButtons, 1, 4)) {
                numberOfSelectionButtons = console.readLine("That is an invalid response.  Please enter a number between 1 and 4. ");
                console.printf(fNEW_LINE);
            }

            capacityNumber = console.readLine("What is the maximum capacity of the machine Please enter a number larger or " +
                    "equal to the number of selection buttons and that is between " + numberOfSelectionButtons + " and 100. ");
            console.printf(fNEW_LINE);

            while (!isNumeric(capacityNumber)) {
                capacityNumber = console.readLine("That is an invalid response.  Please enter a number between "
                        + numberOfSelectionButtons + " and 100. ");
                console.printf(fNEW_LINE);
            }

            while (!isValidNumberForRangeProvided(capacityNumber, Integer.parseInt(numberOfSelectionButtons), 100)) {
                capacityNumber = console.readLine("That is an invalid response.  Please enter a number between " +
                        numberOfSelectionButtons + " and 100. ");
                console.printf(fNEW_LINE);
            }

            console.printf("Thus far we have a machine with a capacity of " + capacityNumber + " with " + numberOfSelectionButtons + " selection buttons.");
            console.printf(fNEW_LINE);

            console.printf("Now it is time to select the drink types.");
            console.printf(fNEW_LINE);

            console.printf("Choose " + numberOfSelectionButtons + " soda brands from the following list.");
            console.printf(fNEW_LINE);

            console.printf("You must enter the brands exactly as stated or it will not be accepted.");
            console.printf(fNEW_LINE);

            BrandsOfSoda[] sodaBrands = BrandsOfSoda.values();

            brandsOfSodaStringValues = new ArrayList<String>();

            for (BrandsOfSoda soda : sodaBrands) {
                brandsOfSodaStringValues.add(soda.toString());
                console.printf(soda.toString());
                console.printf(fNEW_LINE);
            }

            String sodaBrandSelection = console.readLine("Please enter a soda type: ");
            console.printf(fNEW_LINE);

            while (userSelectedEntries.size() < Integer.parseInt(numberOfSelectionButtons)) {
                while (!brandsOfSodaStringValues.contains(sodaBrandSelection.toUpperCase())) {
                    sodaBrandSelection = console.readLine("The entered soda is not a valid selection.  Please enter the brands as exactly stated in the list: ");
                    console.printf(fNEW_LINE);
                }
                userSelectedEntries.add(BrandsOfSoda.valueOf(sodaBrandSelection.toUpperCase()));
                if (userSelectedEntries.size() < Integer.parseInt(numberOfSelectionButtons)) {
                    console.printf("You have added " + sodaBrandSelection + " for a total of " + userSelectedEntries.size() + " entered so far.");
                    sodaBrandSelection = console.readLine("Please enter another soda type: ");
                } else {
                    console.printf("You have completed your selection. You have chosen the following:");
                    console.printf(fNEW_LINE);
                    for (BrandsOfSoda soda : userSelectedEntries) {
                        console.printf(soda.toString());
                        console.printf(fNEW_LINE);
                    }
                }
            }
        }

        SodaMachineSpecifications specifications = null;
        int capacity = Integer.parseInt(capacityNumber);
        int buttons = Integer.parseInt(numberOfSelectionButtons);

        try {
            specifications = new SodaMachineSpecifications(capacity, buttons, new BigDecimal("0.50"));
        } catch (InvalidArgumentException e) {
            console.printf(e.getMessage());
        }

        IManageInventory manageInventory = new ManageInventory(specifications, userSelectedEntries);
        IManageVendingMachineBalance manageBalance = new ManageVendingMachineBalance();

        Commands cmds = new CommandImpl(userSelectedEntries);

        IAmAVendingMachine vendingMachine = SodaMachineFactory.INSTANCE.createSodaMachine(manageInventory, manageBalance, cmds);
        console.printf("Creating the vending machine.");
        console.printf(fNEW_LINE);

        try {
            vendingMachine.restockAllSelections();
        } catch (InvalidStateException e) {
            console.printf("Failed to initially stock the vending machine: " + e.getMessage());
        }

        console.printf("The vending machine has a capacity of " + vendingMachine.getMaximumNumberOfCansAllowedInMachine());
        console.printf(fNEW_LINE);
        console.printf("The vending machine has " + vendingMachine.getNumberOfSelectionButtons() + " selection buttons");
        console.printf(fNEW_LINE);
        console.printf("The vending machine is stocking " + vendingMachine.calculateTheMaximumAmountOfProductPerSelection() +
                " cans per selection");
        console.printf(fNEW_LINE);
        console.printf("The vending machine has the following types of soda: ");
        console.printf(fNEW_LINE);

        List<String> brandList = vendingMachine.getTheBrandsOfSodaInTheMachine();

        for (String brand : brandList) {
            console.printf(brand + " inventory: " + manageInventory.getCurrentInventoryForAParticularSelection(BrandsOfSoda.valueOf(brand)));
            console.printf(fNEW_LINE);
        }

        console.printf("Please enter one of the following commands:");
        console.printf(fNEW_LINE);
        console.printf("Insert money by typing amount in the following format: 0.25, 0.10, 0.05 this machine only accepts " +
                "quarters, dimes,and nickels only.");
        console.printf(fNEW_LINE);
        console.printf("Type \"HELP\" to see the different selections available.");
        console.printf(fNEW_LINE);
        console.printf("Type the soda selection as it is spelled out in the menu");
        console.printf(fNEW_LINE);
        console.printf("Type \"coin_return\" to get money back");
        console.printf(fNEW_LINE);
        console.printf("Hit keys Control - C to end the application");
        console.printf(fNEW_LINE);
        String command = console.readLine("Please enter a command: ");
        console.printf(fNEW_LINE);

        List<String> commands = vendingMachine.getCommands();

        while (true) {
            command = command.toUpperCase();

            if (COIN_RETURN.equalsIgnoreCase(command)) {
                String coinReturnMsg = vendingMachine.coinReturn();
                console.printf("The following coins have been returned to you: " +
                        (coinReturnMsg.trim().equalsIgnoreCase("") ? "NOTHING" : coinReturnMsg));
                console.printf(fNEW_LINE);
            } else if (command.equalsIgnoreCase(HELP)) {
                console.printf("These are the valid commands: ");
                console.printf(fNEW_LINE);

                for (String cmd : commands) {
                    console.printf(cmd);
                    console.printf(fNEW_LINE);
                }
            } else if (command.equalsIgnoreCase(Coins.TWENTY_FIVE_CENTS.getStringRepresentation()) ||
                    command.equalsIgnoreCase(Coins.TEN_CENTS.getStringRepresentation()) ||
                    command.equalsIgnoreCase(Coins.FIVE_CENTS.getStringRepresentation())) {
                try {
                    console.printf("You have a total of: " + vendingMachine.insertCoin(command) + " deposited");
                    console.printf(fNEW_LINE);
                } catch (InvalidMoneyException e) {
                    console.printf("Error with coin deposit!!!!");
                    console.printf(fNEW_LINE);
                }
            } else if (brandList.contains(command)) {
                console.printf(vendingMachine.dispenseSoda(BrandsOfSoda.valueOf(command)));
                console.printf(fNEW_LINE);
            }else if ("stock".equalsIgnoreCase(command)){
                try {
                    console.printf("Restocking the whole machine");
                    console.printf(fNEW_LINE);
                    vendingMachine.restockAllSelections();
                } catch (InvalidStateException e) {
                    console.printf("Restocking the soda machine has failed!");
                    console.printf(fNEW_LINE);
                }
            } else if (!commands.contains(command)) {
                console.printf(command + " is an invalid command, if you forgot the list of valid commands please type \"help\"");
                console.printf(fNEW_LINE);
                console.printf(fNEW_LINE);
            }

            command = console.readLine("Please enter another command: ");
        }
    }

    private static boolean isValidNumberForRangeProvided(String str, int minimum, int maximum) {
        if (!isNumeric(str)) return false;

        int number = Integer.parseInt(str);

        if (number < minimum || number > maximum) {
            return false;
        }

        return true;
    }

    private static boolean isNumeric(String str) {
        if (str.isEmpty()) return false;

        for (int i = 0; i < str.length(); i++) {

            //If we find a non-digit character we return false.
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }

        return true;
    }

}


