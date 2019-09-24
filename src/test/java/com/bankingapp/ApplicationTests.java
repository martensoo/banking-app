package com.bankingapp;

import com.bankingapp.exception.NotEnoughMoneyException;
import com.bankingapp.model.AccountDTO;
import com.bankingapp.model.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port+"/api/v1/";
	}

	@Test
	public void contextLoads() {
	}


	@Test
	@Sql({"/import_data.sql"})
	public void testBalance() {
		AccountDTO accountDTO = restTemplate.getForObject(getRootUrl() + "account/10001" , AccountDTO.class);
		Assert.assertEquals(new Long(10000L), accountDTO.getBalanceInCents());
	}

	@Test
	@Sql({"/import_data.sql"})
	public void testSuccessfulTransaction() {
		Transaction transaction = restTemplate.postForObject(getRootUrl() + "/transaction/10001/10002/3000", null, Transaction.class);
		AccountDTO accountDTO = new AccountDTO();
		accountDTO = restTemplate.getForObject(getRootUrl() + "account/10001" , AccountDTO.class);
		Assert.assertEquals(new Long(7000L), accountDTO.getBalanceInCents());
		accountDTO = restTemplate.getForObject(getRootUrl() + "account/10002" , AccountDTO.class);
		Assert.assertEquals(new Long(13000L), accountDTO.getBalanceInCents());
	}

	//TODO
	/*
	@Test(expected=NotEnoughMoneyException.class)
	@Sql({"/import_data.sql"})
	public void testLimitExceeded() {
		Transaction transaction = restTemplate.postForObject(getRootUrl() + "/transaction/10001/10002/10001", null, Transaction.class);
	}
	*/


}
