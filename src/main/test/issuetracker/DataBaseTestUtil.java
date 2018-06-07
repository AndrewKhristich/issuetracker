package issuetracker;

import com.axmor.utils.DataBaseInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseTestUtil implements DataBaseInterface {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public Connection getConnection(){
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection("jdbc:h2:~/test1", DB_USER, DB_PASSWORD);
            System.out.println("Got connected");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("FAIL CONNECTION");
        }
        return connection;
    }

    public void createAllTables(String queryPath){
        try (Connection connection = getConnection();
             FileReader createQueryScript = new FileReader(queryPath);
             BufferedReader reader = new BufferedReader(createQueryScript)){
            StringBuilder query = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null){
                query.append(line);
            }
            connection.createStatement().executeUpdate(String.valueOf(query));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
