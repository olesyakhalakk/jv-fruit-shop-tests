package service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReaderImplTest {
    private static ReaderImpl reader = new ReaderImpl();
    private static final Path project = Path.of("").toAbsolutePath();
    private static final Path resources =
            Paths.get(project.toString(), "src", "test", "resources");
    private final Path fileName = resources.resolve("temporary-file.txt");

    private static void createResourceDirectory() {
        try {
            if (!Files.exists(resources)) {
                Files.createDirectories(resources);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error create directory" + resources, e);
        }
    }

    private void createTestFile(String data) {
        try {
            Files.write(fileName, data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error write data to file" + fileName, e);
        }
    }

    private void deleteTestFile() {
        if (!Files.exists(fileName)) {
            return;
        }
        try {
            Files.delete(fileName);
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Error remove file '%s'", fileName), e);
        }
    }

    @BeforeClass
    public static void beforeClass() {
        createResourceDirectory();
    }

    @Test
    public void read_notEmptyFile_ok() {
        String textForTest = "Be great in act, as you have been in thought";
        createTestFile(textForTest);
        List<String> expected = List.of(textForTest);
        List<String> actual = reader.read(String.valueOf(fileName));
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void read_emptyFile_ok() {
        createTestFile("");
        List<String> expected = new ArrayList<>();
        List<String> actual = reader.read(String.valueOf(fileName));
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void read_notExistedFile_notOk() {
        reader.read(String.valueOf(fileName));
    }

    @After
    public void tearDown() {
        deleteTestFile();
    }
}
