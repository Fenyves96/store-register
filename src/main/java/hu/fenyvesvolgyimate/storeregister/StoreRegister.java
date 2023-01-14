package hu.fenyvesvolgyimate.storeregister;

public interface StoreRegister {
    void setPersistanceType(StorePesristenceType inMemory);

    int sellProductItem(String productName, int amount);

    void buyProductItem(String productName, int amount);

    void createProduct(String productName);
}
