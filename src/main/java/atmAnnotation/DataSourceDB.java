package atmAnnotation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class DataSourceDB {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:atm-di.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void closeDB() throws SQLException {
        this.connect().close();
    }

    public void updateDB(int customerNum, double balance){
        String sql = "UPDATE users SET balance = ?  "
                + "WHERE customerNumber = ?";

        try {
            PreparedStatement pstmt = this.connect().prepareStatement(sql);
            // set the corresponding param
            pstmt.setDouble(1, balance);
            pstmt.setInt(2, customerNum);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<Integer, Customer> readCustomers() {
        Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
        String query = "Select * from users";
        try {
            Statement statement = this.connect().createStatement();
            ResultSet result = statement.executeQuery(query);
            // loop through the result set
            while (result.next()) {
                int customerNumber = result.getInt(1);
                int pin = result.getInt(2);
                double balance = result.getDouble(3);
                Customer c = new Customer(customerNumber, pin, balance);
                customers.put(c.getCustomerNumber(), c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }
}

