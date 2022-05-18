package com.codecool.shop.dao.implementation.jdbc;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDaoJdbcTest {
    private static ProductDao productDao;
    private static void initDatabase() throws SQLException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Products( " +
                    "    id SERIAL NOT NULL PRIMARY KEY, " +
                    "    name varchar NOT NULL, " +
                    "    price decimal NOT NULL, " +
                    "    currency varchar NOT NULL, " +
                    "    description varchar, " +
                    "    category_id int NOT NULL, " +
                    "    supplier_id int NOT NULL " +
                    ")");
            connection.commit();
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Chinpokomon: Myrrh', 29.9, 'EUR', 'he next addition of the famous Chinpokomon series. The Myrrh edition contains the legendary chinpokomon Donkeytron.', 1, 2);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Chinpokomon: Platinum', 29.9, 'EUR', 'The next addition of the famous Chinpokomon series. The Platinum edition contains the legendary chinpokomon Lambtron.', 1, 2);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Muckman', 15, 'EUR', 'The Original first addition of the Muckman video game series', 3, 3);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Sanic', 26.9, 'EUR', 'How fast? Sanic fast. Be faster than light with Sanic the hedgehog', 2, 4);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Supra Mayro', 59.99, 'EUR', 'The best seller 90s super classic, Supra Mayro is finally avaliable on Smoke', 2, 1);");
            statement.executeUpdate("INSERT INTO Products(name, price, currency, description, category_id, supplier_id) VALUES ('Somari', 30, 'EUR', 'The fastest man alive is here. Test your reflexes in a fast paced environment with Somari on Smoke', 2, 1);");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TestProductCategoryDao implements ProductCategoryDao{

        @Override
        public void add(ProductCategory category) {

        }

        @Override
        public ProductCategory find(int id) {
            return new ProductCategory("testCategory", "test", "test");
        }

        @Override
        public void remove(int id) {

        }

        @Override
        public List<ProductCategory> getAll() {
            return null;
        }

        @Override
        public List<ProductCategory> getMultipleById(String ids) {
            return null;
        }
    }

    private static class TestSupplierDao implements SupplierDao {

        @Override
        public void add(Supplier supplier) {

        }

        @Override
        public Supplier find(int id) {
            return new Supplier("testSupplier", "test");
        }

        @Override
        public void remove(int id) {

        }

        @Override
        public List<Supplier> getAll() {
            return null;
        }

        @Override
        public List<Supplier> getMultipleById(String ids) {
            return null;
        }
    }

    @BeforeAll
    public static void init() throws SQLException, ClassNotFoundException, IOException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        // initialize database
        initDatabase();
        productDao = new ProductDaoJdbc(JdbcTestUtil.getSource(), new TestProductCategoryDao(), new TestSupplierDao());
    }

    @AfterAll
    public static void destroy() throws SQLException, ClassNotFoundException, IOException {
        DataSource dataSource = JdbcTestUtil.getSource();
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement();) {
            statement.executeUpdate("DROP TABLE Products");
            connection.commit();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void getProductByIndex() {
        Product product = productDao.find(1);
        assertEquals("Chinpokomon: Myrrh", product.getName());
        assertEquals(new BigDecimal("29.9").setScale(2, RoundingMode.HALF_UP), product.getDefaultPrice());
        assertEquals("he next addition of the famous Chinpokomon series. The Myrrh edition contains the legendary chinpokomon Donkeytron.", product.getDescription());
    }

    @Test
    @Order(2)
    void addProduct() {
        Product productMock = mock(Product.class);
        when(productMock.getProductCategoryId()).thenReturn(1);
        when(productMock.getSupplierId()).thenReturn(1);
        when(productMock.getName()).thenReturn("testName");
        when(productMock.getDescription()).thenReturn("testDesc");
        when(productMock.getDefaultCurrency()).thenReturn(Currency.getInstance("EUR"));
        when(productMock.getDefaultPrice()).thenReturn(new BigDecimal("29.90"));

        productDao.add(productMock);
        Product testProduct = productDao.find(7);

        assertEquals("testName", testProduct.getName());
        assertEquals("testDesc", testProduct.getDescription());
    }

    @Test
    @Order(3)
    void removeProductById() {
        productDao.remove(3);

        assertNull(productDao.find(3));
    }

    @Test
    @Order(4)
    void gettingAllProductsMatchNumberOfThem() {
        List<Product> products = productDao.getAll();

        assertEquals(6, products.size());
    }

    @Test
    @Order(5)
    void getProductsBySupplierMatchNumberOfThem() {
        Supplier supplierMock = mock(Supplier.class);
        when(supplierMock.getId()).thenReturn(2);
        List<Product> products = productDao.getBy(supplierMock);

        assertEquals(2, products.size());
    }

    @Test
    @Order(6)
    void getProductsByCategoryMatchNumberOfThem() {
        ProductCategory productCategoryMock = mock(ProductCategory.class);
        when(productCategoryMock.getId()).thenReturn(2);
        List<Product> products = productDao.getBy(productCategoryMock);

        assertEquals(3, products.size());
    }

    @Test
    @Order(7)
    void getMultipleByCategories() {
        ProductCategory productCategoryMock1 = mock(ProductCategory.class);
        when(productCategoryMock1.getId()).thenReturn(2);
        ProductCategory productCategoryMock2 = mock(ProductCategory.class);
        when(productCategoryMock2.getId()).thenReturn(3);
        List<Product> products = productDao.getMultipleByCategories(new ArrayList<ProductCategory>(
                Arrays.asList(productCategoryMock1, productCategoryMock2)));

        assertEquals(4, products.size());
    }

    @Test
    @Order(8)
    void getMultipleBySuppliers() {
        Supplier supplierMock1 = mock(Supplier.class);
        when(supplierMock1.getId()).thenReturn(2);
        Supplier supplierMock2 = mock(Supplier.class);
        when(supplierMock2.getId()).thenReturn(3);
        List<Product> products = productDao.getMultipleBySuppliers(new ArrayList<Supplier>(
                Arrays.asList(supplierMock1, supplierMock2)));

        assertEquals(3, products.size());
    }

    @Test
    @Order(9)
    void getMultipleByCategoriesSuppliers() {
    }

    @Test
    @Order(10)
    void getMultipleById() {
    }
}