package strategy;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction.Operation operation = FruitTransaction.Operation.RETURN;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
        operationHandler = new ReturnOperationHandlerImpl(storageDao);
        Storage.storageMap.put("apple", 350);
    }

    @Test
    public void process_productExist_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(operation, "apple", 150);
        operationHandler.handle(fruitTransaction);
        Assert.assertEquals(Integer.valueOf(500), Storage.storageMap.get("apple"));
    }

    @After
    public void tearDown() {
        storageDao.getStorage().clear();
    }
}
