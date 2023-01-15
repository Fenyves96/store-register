package hu.fenyvesvolgyimate.storeregister.dao;

import hu.fenyvesvolgyimate.storeregister.StorePersistenceType;

public class StoreItemRepositoryFactory {
    public StoreItemRepository create(StorePersistenceType storePersistenceType) {
        return switch (storePersistenceType) {
            case InMemory -> new InMemoryStoreItemRepository();
            default -> null;
        };
    }
}
