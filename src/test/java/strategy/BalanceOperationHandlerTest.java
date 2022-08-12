package strategy;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandler(storageDao);
        Storage.storageMap.put("apple", 400);
    }

    @Test
    public void process_productExist_ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
        FruitTransaction fruitTransaction = new FruitTransaction(operation, "apple", 150);
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals(Integer.valueOf(150), Storage.storageMap.get("apple"));
    }

    @After
    public void tearDown() {
        storageDao.getStorage().clear();
    }
}
