package strategy;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction.Operation operation;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new SupplyOperationHandlerImpl(storageDao);
        operation = FruitTransaction.Operation.SUPPLY;
        Storage.storageMap.put("apple", 800);
    }

    @Test
    public void process_productExist_ok() {
        fruitTransaction = new FruitTransaction(operation, "apple", 150);
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals(Integer.valueOf(950), Storage.storageMap.get("apple"));
    }

    @After
    public void tearDown() {
        storageDao.getStorage().clear();
    }
}