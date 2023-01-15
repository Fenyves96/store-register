package hu.fenyvesvolgyimate.storeregister;

public interface StoreRegister {
    void setPersistanceType(StorePersistenceType storePersistenceType);

    void createProduct(String productName);

    void buyProductItem(String productName, int numberOfProduct);

    int sellProductItem(String productName, int numberOfProduct);

}
