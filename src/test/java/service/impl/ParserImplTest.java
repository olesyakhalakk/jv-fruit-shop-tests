package service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserImplTest {
    private static ParserImpl parser;
    private List<String> inputGoodDataWithoutHeader;
    private List<FruitTransaction> expectedForInputGoodData;

    {

        inputGoodDataWithoutHeader = List.of(
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");

        expectedForInputGoodData = new LinkedList<>();
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100));
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 13));
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10));
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20));
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5));
        expectedForInputGoodData.add(new

                FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50));

    }

    @BeforeClass
    public static void beforeClass() {
        parser = new ParserImpl();
    }

    @Test
    public void parse_notEmptyCorrectDataWithHeader_ok() {
        List<String> inputData = new ArrayList<>();
        inputData.add("type,fruit,quantity");
        inputData.addAll(inputGoodDataWithoutHeader);
        List<FruitTransaction> actual = parser.parseData(inputData);
        Assert.assertEquals(expectedForInputGoodData, actual);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongHeader_notOk() {
        List<String> inputData = List.of("wrong,header");
        parser.parseData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongDelimiter_notOk() {
        List<String> inputData = List.of("b|banana|20");
        parser.parseData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongOperation_notOk() {
        List<String> inputData = List.of("o,banana,20");
        parser.parseData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_wrongQuantity_notOk() {
        List<String> inputData = List.of("b,banana,20a");
        parser.parseData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_emptyProduct_notOk() {
        List<String> inputData = List.of("b,,20");
        parser.parseData(inputData);
    }

    @Test(expected = RuntimeException.class)
    public void parse_negativeQuantity_notOk() {
        List<String> inputData = List.of("b,banana,-20");
        parser.parseData(inputData);
    }
}
