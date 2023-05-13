package com.frank.dto;

import com.frank.enums.AccountStatus;
import com.frank.enums.AccountType;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    @NotNull
    @Positive
    private BigDecimal balance;
    private Date creationDate;
    @NotNull
    private Long userId;
    @NotNull
    private AccountType accountType;
    private AccountStatus accountStatus;
}
