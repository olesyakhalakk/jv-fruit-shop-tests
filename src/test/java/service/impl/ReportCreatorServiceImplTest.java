package service.impl;

import static org.junit.Assert.assertEquals;

import dao.StorageDao;
import dao.StorageDaoImpl;
import db.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ReportCreatorService;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportService;
    private final String reportHeader = "fruit,quantity";
    private final String newLine = "\n";

    @BeforeClass
    public static void beforeClass() {
        StorageDao storageDao = new StorageDaoImpl();
        reportService = new ReportCreatorServiceImpl(storageDao);
    }

    @Test
    public void create_withNotEmptyStorage_ok() {
        StringBuilder expected = new StringBuilder();
        expected.append(reportHeader).append(newLine);
        expected.append("banana,").append(100).append(newLine);
        expected.append("apple,").append(50).append(newLine);
        expected.append("mango,").append(150).append(newLine);

        Storage.storageMap.put("banana", 100);
        Storage.storageMap.put("apple", 50);
        Storage.storageMap.put("mango", 150);
        String actual = reportService.createReport();
        assertEquals(expected.toString(), actual);
    }

    @Test
    public void create_withEmptyStorage_ok() {
        String actual = reportService.createReport();
        assertEquals(reportHeader + newLine, actual);
    }

    @After
    public void tearDown() {
        Storage.storageMap.clear();
    }
}
