package com.frank.model;

import com.frank.enums.AccountStatus;
import com.frank.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID id;
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
