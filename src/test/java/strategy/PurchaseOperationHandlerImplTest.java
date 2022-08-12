package strategy;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction.Operation operation;

    @BeforeClass
    public static void beforeClass() {
        operation = FruitTransaction.Operation.PURCHASE;
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandlerImpl(storageDao);
        Storage.storageMap.put("mango", 200);
    }

    @Test
    public void process_productExist_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(operation, "mango", 50);
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals(Integer.valueOf(150), Storage.storageMap.get("mango"));
    }

    @Test(expected = RuntimeException.class)
    public void process_negativeResult_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(operation, "mango", 300);
        operationHandler.handle(fruitTransaction);
    }

    @After
    public void tearDown() {
        storageDao.getStorage().clear();
    }
}
