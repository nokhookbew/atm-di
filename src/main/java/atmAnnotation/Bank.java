package atmAnnotation;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A bank contains customers with bank accounts.
 */
@Data

@Component
@Primary
public class Bank {

   private Map<Integer, Customer> customers;
   private DataSourceDB dataSourceDB;

   /**
    * Constructs a bank with no customers.
    */
   @Autowired
   public Bank(DataSourceDB dataSourceDB) {
      this.dataSourceDB = dataSourceDB;
      customers = new HashMap<Integer,Customer>();
   }

   public void initializeCustomers() {
      customers = dataSourceDB.readCustomers();
   }
   /**
    * Adds a customer to the bank.
    * @param c the customer to add
    */
   public void addCustomer(Customer c) {
      customers.put(c.getCustomerNumber(), c);
   }
   
   /** 
    * Finds a customer in the bank.
    * @param number a customer number
    * @return the matching customer, or null if no customer
    * matches
    */
   public Customer findCustomer(int number) {
	  return customers.get(number);
   }
}
