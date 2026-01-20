package money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class TMoney {

    public static void main(String[] args) {
        CurrencyUnit usd = Monetary.getCurrency("USD");
        MonetaryAmount fstAmtUSD = Monetary.getDefaultAmountFactory()
                .setCurrency(usd).setNumber(200).create();
//        Money moneyof = Money.of(12, usd);
//        FastMoney fastmoneyof = FastMoney.of(2, usd);
    }

}
