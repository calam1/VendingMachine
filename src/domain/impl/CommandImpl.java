package domain.impl;

import domain.BrandsOfSoda;
import domain.Coins;
import domain.Commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/5/12
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandImpl implements Commands {

    private static final String HELP = "help";
    public static final String COIN_RETURN = "coin_return";
    public static final String STOCK = "stock";
    private List<String> commands;

    public CommandImpl(List<BrandsOfSoda> sodas) {
        commands = new ArrayList<String>();

        commands.add(COIN_RETURN);
        commands.add(Coins.FIVE_CENTS.getStringRepresentation());
        commands.add(Coins.TEN_CENTS.getStringRepresentation());
        commands.add(Coins.TWENTY_FIVE_CENTS.getStringRepresentation());
        List<BrandsOfSoda> sodaBrands = sodas;
        for (BrandsOfSoda soda : sodaBrands) {
            commands.add(soda.toString());
        }
        commands.add(HELP);
        commands.add(STOCK);
    }

    public List<String> getCommands() {
        return commands;
    }
}
