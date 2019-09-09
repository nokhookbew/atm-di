package atmAnnotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("config-annotation.xml");
        ATMSimulator atmSimulator = context.getBean(ATMSimulator.class);
        try {
            atmSimulator.run();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
