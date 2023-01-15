package hu.fenyvesvolgyimate.storeregister.dao;

import hu.fenyvesvolgyimate.storeregister.entity.StoreItem;

public interface StoreItemRepository {
    StoreItem loadItem(String productName);

    void saveItem(StoreItem storeItem);
}
