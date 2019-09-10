package atmAnnotation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * An ATM that accesses a bank.
 */
@Data

@Component
@Primary
public class ATM {
	public static final int START = 1;
	public static final int TRANSACT = 2;

	private int state;
	private int customerNumber;
	private Customer currentCustomer;
	private Account currentAccount;
	private Bank bank;
	private DataSourceDB dataSourceDB;

	/**
     * Constructs an ATM for a bank.
	 */
	@Autowired
	public ATM(Bank bank, DataSourceDB dataSourceDB) {
		this.bank = bank;
		this.customerNumber = -1;
		this.currentAccount = null;
		this.state = START;
		this.dataSourceDB = dataSourceDB;
	}

	void init() {
		bank.initializeCustomers();
	}

	/**
     * Resets the ATM to the initial state.
	 */
	void reset() {
		customerNumber = -1;
		currentAccount = null;
		state = START;
	}

	/**
     * Finds customer in bank.
     * If found sets state to ACCOUNT, else to START.
     * (Precondition: state is PIN)
	 * @param customerNum current customer number
     * @param pin pin being inputted
	 */
	void validateCustomer(int customerNum, int pin) {
		assert state == START;

		if (bank.findCustomer(customerNum) != null &&
				bank.findCustomer(customerNum).match(pin)) {
			customerNumber = customerNum;
			currentCustomer = bank.findCustomer(customerNumber);
			currentAccount = currentCustomer.getAccount();
			state = TRANSACT;
		}
	}

	/**
     * Withdraws amount from current account.
     * (Precondition: state is TRANSACT)
     * @param value the amount to withdraw
	 */
	void withdraw(double value) {
		assert state == TRANSACT;
		currentAccount.withdraw(value);
		dataSourceDB.updateDB(currentCustomer.getCustomerNumber(), currentAccount.getBalance());
	}

	/**
     * Deposits amount to current account.
     * (Precondition: state is TRANSACT)
     * @param value the amount to deposit
	 */
	void deposit(double value) {
		assert state == TRANSACT;
		currentAccount.deposit(value);
		dataSourceDB.updateDB(currentCustomer.getCustomerNumber(), currentAccount.getBalance());
	}

	/**
     * Gets the balance of the current account.
     * (Precondition: state is TRANSACT)
     * @return the balance
	 */
	public double getBalance() {
		assert state == TRANSACT;
		return currentAccount.getBalance();
	}

	/**
     * Transfer from current customer to the customer with
	 * customer number in the parameter
	 * @param customerNum receiver customer
	 * @param amount amount to be transferred
	 */
	void transfer(int customerNum, double amount) {
		assert state == TRANSACT;
		Customer receivingCustomer = bank.findCustomer(customerNum);
		Account receivingAccount = receivingCustomer.getAccount();
		currentAccount.withdraw(amount);
		receivingAccount.deposit(amount);
		dataSourceDB.updateDB(currentCustomer.getCustomerNumber(), currentAccount.getBalance());
		dataSourceDB.updateDB(receivingCustomer.getCustomerNumber(), receivingAccount.getBalance());
	}

	/**
     * Gets the current state of this ATM.
     * @return the current state
	 */
	int getState() {
		return state;
	}
}
