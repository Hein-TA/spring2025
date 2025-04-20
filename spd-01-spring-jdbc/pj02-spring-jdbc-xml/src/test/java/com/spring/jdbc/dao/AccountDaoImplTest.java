package com.spring.jdbc.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.spring.jdbc.dto.AccountForm;

@SpringJUnitConfig(locations = {"classpath:/application.xml"})
@TestMethodOrder(value = OrderAnnotation.class)
public class AccountDaoImplTest {

	@Autowired
	AccountDao dao;
	
	@Order(1)
	@ParameterizedTest
	@CsvSource({ 
		"Aung Aung, 091111122221, 1", 
		"MG MG, 091111122222, 2", 
		"KG KG, 091111122223, 3"
		})
	void test_insert(String name, String phone, int expectedId) {
		var form = new AccountForm(name, phone);
		var pk = dao.create(form);
		assertEquals(expectedId, pk);
	}

	@Order(2)
	@Test
	void test_select_count() {
		var result = dao.count();
		assertEquals(3, result);
	}

	@Order(3)
	@ParameterizedTest
	@CsvSource({ 
		"Aung Aung, 091111122221, 1", 
		"MG MG, 091111122222, 2", 
		"KG KG, 091111122223, 3" 
		})
	void test_find_by_id(String name, String phone, int id) {
		var account = dao.findById(id);
		assertEquals(name, account.name());
		assertEquals(phone, account.phone());
	}
	
	@Order(4)
	@ParameterizedTest
	@ValueSource(ints = {0, 4, 5})
	void test_find_by_id_not_found(int id) {
		var dto = dao.findById(id);
		assertNull(dto);
	}
}
