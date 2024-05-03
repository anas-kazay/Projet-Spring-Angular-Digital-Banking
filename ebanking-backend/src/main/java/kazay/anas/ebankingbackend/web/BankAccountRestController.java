package kazay.anas.ebankingbackend.web;

import kazay.anas.ebankingbackend.dtos.AccountHistoryDTO;
import kazay.anas.ebankingbackend.dtos.AccountOperationDTO;
import kazay.anas.ebankingbackend.dtos.BankAccountDTO;
import kazay.anas.ebankingbackend.entities.AccountOperation;
import kazay.anas.ebankingbackend.exceptions.BankAccountNotFoundException;
import kazay.anas.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        BankAccountDTO bankAccountDTO = bankAccountService.getBankAccount(accountId);
        return bankAccountDTO;
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name ="page",defaultValue = "0") int page,
                                               @RequestParam(name ="size",defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page, size);
    }

}
