package com.spring.jdbc.dao;

import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.spring.jdbc.dto.AccountDto;
import com.spring.jdbc.dto.AccountForm;

public class AccountDaoImpl implements AccountDao {
	
	private JdbcTemplate template;
	private RowMapper<AccountDto> rowMapper;
	
	public AccountDaoImpl(DataSource dataSource) {
		template = new JdbcTemplate(dataSource);
		rowMapper = new DataClassRowMapper<>(AccountDto.class);
	}

	@Override
	public int create(AccountForm form) {
		
		var keyHolder = new GeneratedKeyHolder();
		
		template.update(con -> {
			
			var stmt = con.prepareStatement("insert into ACCOUNT (name, phone) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, form.name());
			stmt.setString(2, form.phone());
			return stmt;
			
		}, keyHolder);
		
		return keyHolder.getKey().intValue();
	}

	@Override
	public long count() {
		return template.queryForObject("select count(id) from ACCOUNT", Long.class);
	}

	@Override
	public AccountDto findById(int id) {
		var list = template.query("select * from ACCOUNT where id = ?", rowMapper, id);
		
		if (list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

}
