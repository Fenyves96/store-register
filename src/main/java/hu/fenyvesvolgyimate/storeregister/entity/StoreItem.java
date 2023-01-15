package hu.fenyvesvolgyimate.storeregister.entity;

public class StoreItem {
    String productName;
    int amount;

    public StoreItem(String productName, int amount) {
        this.productName = productName;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }
}
