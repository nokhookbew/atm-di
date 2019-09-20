package atmSpringJDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class DataSourceDB {
    @Value("${urlDB}") private String url;
    @Value("${query}") private String query;
    @Value("${update}") private String update;

    @Autowired
    JdbcTemplate jdbcTemplate;

    void updateDB(int customerNum, double balance){
        jdbcTemplate.update(update, balance, customerNum);
    }

    Map<Integer, Customer> readCustomers() {
        Map<Integer, Customer> customers = new HashMap<Integer, Customer>();
        List<Customer> customerList = jdbcTemplate.query(query, new CustomerRowMapper());
        for (Customer customer : customerList) {
            customers.put(customer.getCustomerNumber(), customer);
        }
        return customers;
    }
}

