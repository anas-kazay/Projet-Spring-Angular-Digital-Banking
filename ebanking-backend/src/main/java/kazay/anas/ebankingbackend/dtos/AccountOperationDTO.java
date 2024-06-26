package kazay.anas.ebankingbackend.dtos;

import jakarta.persistence.*;
import kazay.anas.ebankingbackend.entities.BankAccount;
import kazay.anas.ebankingbackend.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
