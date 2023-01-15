package hu.fenyvesvolgyimate.storeregister;

import hu.fenyvesvolgyimate.storeregister.dao.StoreItemRepository;
import hu.fenyvesvolgyimate.storeregister.dao.StoreItemRepositoryFactory;
import hu.fenyvesvolgyimate.storeregister.entity.StoreItem;
import hu.fenyvesvolgyimate.storeregister.exception.ItemNotAvailableException;

public class SlimStoreRegister implements StoreRegister {
    StoreItemRepository storeItemRepository;

    @Override
    public void setPersistanceType(StorePersistenceType storePersistenceType) {
        StoreItemRepositoryFactory storeItemRepositoryFactory = new StoreItemRepositoryFactory();
        storeItemRepository = storeItemRepositoryFactory.create(storePersistenceType);
    }

    @Override
    public int sellProductItem(String itemName, int amount) {
        StoreItem item = storeItemRepository.loadItem(itemName);
        if (item == null)
            throw new ItemNotAvailableException();
        int soldAmount;
        if (item.getAmount() > amount) {
            item.setAmount(item.getAmount() - amount);
            soldAmount = amount;
        } else {
            soldAmount = item.getAmount();
            item.setAmount(0);
        }
        storeItemRepository.saveItem(item);
        return soldAmount;
    }

    @Override
    public void buyProductItem(String productName, int amount) {
        StoreItem storeItem = storeItemRepository.loadItem(productName);
        storeItem.setAmount(storeItem.getAmount() + amount);
        storeItemRepository.saveItem(storeItem);
    }

    @Override
    public void createProduct(String productName) {
        StoreItem storeItem = new StoreItem(productName, 0);
        storeItemRepository.saveItem(storeItem);
    }
}
