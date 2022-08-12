package strategy;

import dao.StorageDao;
import dao.StorageDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void get_operationInMap_ok() {
        OperationHandler actual =
                operationStrategy.getOperationType(FruitTransaction.Operation.BALANCE);
        Assert.assertEquals(BalanceOperationHandler.class, actual.getClass());
    }
}
