package hu.fenyvesvolgyimate.storeregister;

public class SlimStoreRegister implements StoreRegister {
    @Override
    public void setPersistanceType(StorePesristenceType inMemory) {

    }

    @Override
    public int sellProductItem(String productName, int amount) {
        return 0;
    }

    @Override
    public void buyProductItem(String productName, int amount) {

    }

    @Override
    public void createProduct(String productName) {

    }
}
