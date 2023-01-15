package hu.fenyvesvolgyimate.storeregister.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import hu.fenyvesvolgyimate.storeregister.entity.StoreItem;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileStoreItemRepository implements StoreItemRepository {

    private final int PRODUCT_NAME_POSITION = 0;
    private final int AMOUNT_POSITION = 1;
    private final String csvFileName = "./product_items.csv";

    FileStoreItemRepository() {
        initFile();
    }

    @Override
    public StoreItem loadItem(String productName) {
        return getItemByProductName(productName);
    }

    @Override
    public void saveItem(StoreItem storeItem) {
        StoreItem item = getItemByProductName(storeItem.getProductName());
        if (item == null)
            createNewStoreItem(storeItem);
        else
            updateStoreItem(storeItem);
    }

    private void updateStoreItem(StoreItem storeItem) {
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFileName));
            reader.readNext(); //skip header
            String[] nextLine;
            int lineNumber = 0;
            int searchedItemRowNumber;
            while ((nextLine = reader.readNext()) != null) {
                lineNumber++;
                if (nextLine[PRODUCT_NAME_POSITION].equals(storeItem.getProductName())) {
                    searchedItemRowNumber = lineNumber;
                    updateStoreItemAmountByRowNumber(storeItem.getAmount(), searchedItemRowNumber);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateStoreItemAmountByRowNumber(int amount, int searchedItemRowNumber) {

        File inputFile = new File(csvFileName);
        try {
            CSVReader reader;
            reader = new CSVReader(new FileReader(inputFile));
            List<String[]> csvBody;
            csvBody = reader.readAll();

            csvBody.get(searchedItemRowNumber)[AMOUNT_POSITION] = String.valueOf(amount);
            reader.close();

            CSVWriter writer;
            writer = new CSVWriter(new FileWriter(inputFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
        } catch (IOException | CsvException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createNewStoreItem(StoreItem storeItem) {
        appendToCSv(new String[]{storeItem.getProductName(), Integer.toString(storeItem.getAmount())});
    }

    private void appendToCSv(String[] row) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName, true))) {
            writer.writeNext(row);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StoreItem getItemByProductName(String productName) {
        StoreItem result = null;
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFileName));
            reader.readNext(); //skip header
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine[PRODUCT_NAME_POSITION].equals(productName)) {
                    result = new StoreItem(productName, Integer.parseInt(nextLine[1]));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private String[] getHeader() {
        return new String[]{"product_name", "amount"};
    }

    private void initFile() {
        String[] csvData = getHeader();
        createCsv(csvData);
    }

    private void createCsv(String[] csvData) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            writer.writeNext(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
