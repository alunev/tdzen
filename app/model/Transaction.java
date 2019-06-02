package model;

import com.google.common.base.MoreObjects;

/**
 * @author red
 * @since 0.0.1
 */

public class Transaction {
    private final String date;

    private final String shop;

    private final String amount;

    public Transaction(String date, String shop, String amount) {
        this.date = date;
        this.shop = shop;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getShop() {
        return shop;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("date", date)
                .add("shop", shop)
                .add("amount", amount)
                .toString();
    }
}
