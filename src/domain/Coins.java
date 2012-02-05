package domain;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: christopherlam
 * Date: 2/4/12
 * Time: 10:35 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Coins {

    TWENTY_FIVE_CENTS ("0.25", 25, new BigDecimal("0.25"), "quarter(s)"),
    TEN_CENTS("0.10", 10, new BigDecimal("0.10"), "dime(s)"),
    FIVE_CENTS("0.05", 5, new BigDecimal("0.05"), "nickel(s)");

    private final String stringRepresentation;
    private final int integerRepresentation;
    private final BigDecimal bigDecimalRepresentation;
    private final String descriptions;

    Coins(String stringRepresentation, int integerRepresentation, BigDecimal bigDecimalRepresentation, String descriptions){
        this.stringRepresentation = stringRepresentation;
        this.integerRepresentation = integerRepresentation;
        this.bigDecimalRepresentation = bigDecimalRepresentation;
        this.descriptions = descriptions;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public int getIntegerRepresentation() {
        return integerRepresentation;
    }

    public BigDecimal getBigDecimalRepresentation() {
        return bigDecimalRepresentation;
    }

    public String getDescriptions() {
        return descriptions;
    }

}
