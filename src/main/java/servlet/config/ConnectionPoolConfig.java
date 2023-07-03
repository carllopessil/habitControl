package servlet.config;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ConnectionPoolConfig {

    private static DataSource dataSource;

    private ConnectionPoolConfig() {
        initializeDataSource();
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            initializeDataSource();
        }
        return dataSource;
    }

    private static synchronized void initializeDataSource() {
        if (dataSource == null) {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl("jdbc:h2:~/test");
            basicDataSource.setUsername("sa");
            basicDataSource.setPassword("sa");
            basicDataSource.setMinIdle(5);
            basicDataSource.setMaxIdle(10);
            basicDataSource.setMaxTotal(50);
            dataSource = basicDataSource;
            System.out.println("New connection pool created successfully");
        }
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            try {
                ((BasicDataSource) dataSource).close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
