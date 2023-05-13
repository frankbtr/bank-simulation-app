package com.frank.repository;

import com.frank.dto.AccountDTO;
import com.frank.entity.Account;
import com.frank.exceptions.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


}
