package com.spring.jdbc.dao;

import com.spring.jdbc.dto.AccountDto;
import com.spring.jdbc.dto.AccountForm;

public interface AccountDao {

	int create(AccountForm form);

	long count();

	AccountDto findById(int id);

}
