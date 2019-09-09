package atmJavaBase.config;

import atmJavaBase.ATM;
import atmJavaBase.ATMSimulator;
import atmJavaBase.Bank;
import atmJavaBase.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtmConfig {

    @Bean
    public DataSource dataSource(){
        return new DataSource("customers.txt");
    }

    @Bean
    public Bank bank(){
        return new Bank(dataSource());
    }

    @Bean
    public ATM atm(){
        return new ATM(bank());
    }

    @Bean
    public ATMSimulator atmSimulator(){
        return new ATMSimulator(atm());
    }
}
