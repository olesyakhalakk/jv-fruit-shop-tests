package dao;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProductDaoImplTest {
    private static StorageDaoImpl storageDao;
    private static Map<String, Integer> testStorage;

    @BeforeClass
    public static void beforeClass() {
        testStorage = new HashMap<>();
        storageDao = new StorageDaoImpl();
    }

    @Before
    public void setUp() {
        testStorage.put("banana", 100);
        testStorage.put("apple", 150);
        testStorage.put("mango", 200);
        storageDao.updateData("banana", 100);
        storageDao.updateData("apple", 150);
        storageDao.updateData("mango", 200);
    }

    @Test
    public void updateData_ok() {
        Assert.assertEquals(testStorage, Storage.storageMap);
    }

    @Test
    public void getRemainFruit_ok() {
        Assert.assertEquals(Integer.valueOf(100),
                storageDao.getRemainFruit("banana"));
        Assert.assertEquals(Integer.valueOf(150),
                storageDao.getRemainFruit("apple"));
        Assert.assertEquals(Integer.valueOf(200),
                storageDao.getRemainFruit("mango"));
    }

    @Test
    public void getStorage_ok() {
        Assert.assertEquals(testStorage, storageDao.getStorage());
    }

    @After
    public void tearDown() {
        testStorage.clear();
        Storage.storageMap.clear();
    }
}
