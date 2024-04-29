package kazay.anas.ebankingbackend;

import kazay.anas.ebankingbackend.dtos.BankAccountDTO;
import kazay.anas.ebankingbackend.dtos.CurrentAccountDTO;
import kazay.anas.ebankingbackend.dtos.CustomerDTO;
import kazay.anas.ebankingbackend.dtos.SavingAccountDTO;
import kazay.anas.ebankingbackend.entities.*;
import kazay.anas.ebankingbackend.enums.AccountStatus;
import kazay.anas.ebankingbackend.enums.OperationType;
import kazay.anas.ebankingbackend.exceptions.BalanceNotSufficientException;
import kazay.anas.ebankingbackend.exceptions.BankAccountNotFoundException;
import kazay.anas.ebankingbackend.exceptions.CustomerNotFoundException;
import kazay.anas.ebankingbackend.repositories.AccountOperationRepository;
import kazay.anas.ebankingbackend.repositories.BankAccountRepository;
import kazay.anas.ebankingbackend.repositories.CustomerRepository;
import kazay.anas.ebankingbackend.services.BankAccountService;
import kazay.anas.ebankingbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan","Anas","Oussama").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*90000,4.5,customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            for (BankAccountDTO account : bankAccountService.bankAccountList()) {
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(account instanceof SavingAccountDTO){
                        accountId=((SavingAccountDTO) account).getId();
                    }else{
                        accountId = ((CurrentAccountDTO) account).getId();
                    }
                    bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"debit");
                }
            }
        };
    }


    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassin","Aicha").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            bankAccountRepository.findAll().forEach(acc->{
                for(int i=0;i<10;i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*12000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
