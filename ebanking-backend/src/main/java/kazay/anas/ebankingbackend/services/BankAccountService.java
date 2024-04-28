package kazay.anas.ebankingbackend.services;

import kazay.anas.ebankingbackend.entities.BankAccount;
import kazay.anas.ebankingbackend.entities.CurrentAccount;
import kazay.anas.ebankingbackend.entities.Customer;
import kazay.anas.ebankingbackend.entities.SavingAccount;
import kazay.anas.ebankingbackend.exceptions.BalanceNotSufficientException;
import kazay.anas.ebankingbackend.exceptions.BankAccountNotFoundException;
import kazay.anas.ebankingbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    public Customer saveCustomer(Customer customer);
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String descirption) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;


    List<BankAccount> bankAccountList();
}
