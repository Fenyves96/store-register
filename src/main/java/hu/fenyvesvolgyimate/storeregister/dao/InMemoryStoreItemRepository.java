package hu.fenyvesvolgyimate.storeregister.dao;

import hu.fenyvesvolgyimate.storeregister.entity.StoreItem;

import java.sql.*;

public class InMemoryStoreItemRepository implements StoreItemRepository {
    private final String jdbcURL = "jdbc:h2:~/test";
    private final String jdbcUsername = "sa";
    private final String jdbcPassword = "";

    private final String createTableSQL = """
            DROP TABLE IF EXISTS STORE_ITEMS;
            CREATE TABLE STORE_ITEMS(
                       id IDENTITY NOT NULL PRIMARY KEY,
                       product_name varchar(30) NOT NULL,
                       amount int default 0
            );""";

    InMemoryStoreItemRepository() {
        executeSql(createTableSQL);
    }

    @Override
    public StoreItem loadItem(String productName) {
        return getStoreItemByProductName(productName);
    }

    @Override
    public void saveItem(StoreItem storeItem) {
        String sql;
        if (isStoreItemNotExists(storeItem)) {
            sql = """
                    INSERT INTO STORE_ITEMS(product_name, amount) VALUES('%s', %d)
                    """.formatted(storeItem.getProductName(), storeItem.getAmount());
        } else {
            sql = """
                    UPDATE STORE_ITEMS
                    SET amount = %d
                    WHERE product_name = '%s'
                    """.formatted(storeItem.getAmount(), storeItem.getProductName());
        }
        executeSql(sql);
    }

    private boolean isStoreItemNotExists(StoreItem storeItem) {
        return getStoreItemByProductName(storeItem.getProductName()) == null;
    }

    private void executeSql(String sql) {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    StoreItem getStoreItemByProductName(String productName) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                     SELECT product_name, amount FROM STORE_ITEMS
                     WHERE product_name = ?
                     """)) {
            preparedStatement.setString(1, productName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int amountOfItem = rs.getInt("amount");
                return new StoreItem(productName, amountOfItem);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return null;
    }
}
