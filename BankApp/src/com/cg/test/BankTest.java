package com.cg.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.exception.InsufficientBalException;
import com.cg.exception.InvalidAccountNumberException;
import com.cg.exception.InvalidAmountException;
import com.cg.model.Account;
import com.cg.repo.AccountRepo;
import com.cg.service.AccountService;
import com.cg.service.AccountServiceImpl;

public class BankTest {
	
	@Mock
	AccountRepo accRepo;
	AccountService accService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accService = new AccountServiceImpl(accRepo);
	}


	/* create account
	 * 1. when the amount is less than 500, system should throw exception
	 * 2. when the valid info is passed account should be created successfully.
	 * 
	 */

	@Test(expected=com.cg.exception.InsufficientBalException.class)
	public void whenTheAmountIsLessThan500SystemThrowException () throws InsufficientBalException {
		accService.createAccount(101, 400);
		
	}
	
	@Test
	public void whenTheDetailsAreValidAccountIsCreated() throws InsufficientBalException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.save(acc)).thenReturn(true);
		
		assertEquals(acc,accService.createAccount(101, 5000));
		
	}
	
	/*	deposit Amount
	 * 1. when the amount is less negative or zero throw exception
	 * 2. when the amount is correct the system adds the amount to the current balance and returns the new balance
	 */
	
	@Test(expected=InvalidAmountException.class)
	public void whenTheAmtIsIncorrectTheSystemThrowsException() throws InvalidAmountException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc);
		accService.depositAmount(101, 0);
	}

	@Test
	public void whenTheAmtIsCorrectThenTheSystemReturnsTheNewBalance() throws InvalidAmountException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc);
		assertEquals(6000, accService.depositAmount(101, 1000));
	}
	
	/*	withdraw Amount
	 * 1. when the amount is less negative or zero throw exception
	 * 2. when the amount after transaction is less than 500 the system throws exception
	 * 3. when the amount is appropriate the transaction takes place and returns the new balance
	 */
	
	@Test(expected=InvalidAmountException.class)
	public void whenTheAmtIsIncorrectThenTheSystemThrowsAnException() throws InsufficientBalException, InvalidAmountException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc);
		accService.withdrawAmount(101, 0);
		}
	
	@Test(expected=InsufficientBalException.class)
	public void whenTheNewAmountIsLessThan500ThenTheSystemThrowsAnException() throws InsufficientBalException, InvalidAmountException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc);
		accService.withdrawAmount(101, 4600);
		}
	
	@Test
	public void whenTheAmountsAreCorrectThenTheSystemReturnsNewBalance() throws InsufficientBalException, InvalidAmountException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc);
		assertEquals(1000, accService.withdrawAmount(101, 4000));
		}
	
	
	
	/*
	 * funds transfer
	 * 1. when the account number is invalid then throw exception
	 * 2. when the transfer leaves balance less than 500 the system throws exception
	 * 3. when the transaction occurs successfully the system returns boolean
	 */
	
	
	@Test(expected=InvalidAccountNumberException.class)
	public void whenTheAccountNumIsInvalidTheSystemThrowsException() throws InvalidAccountNumberException, InsufficientBalException{
		accService.fundsTransfer(100, 10, 100);
	}
	

	@Test(expected=InsufficientBalException.class)
	public void whenTheRemainingAmountIsLessThan500TheSystemThrowsException() throws InvalidAccountNumberException, InsufficientBalException{
		Account acc1= new Account();
		acc1.setAccNumber(101);
		acc1.setAmount(5000);
		Account acc2= new Account();
		acc2.setAccNumber(102);
		acc2.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc1);
		when(accRepo.search(102)).thenReturn(acc2);
		accService.fundsTransfer(101, 102, 4600);
		
	}
	
	@Test
	public void whenTheAmountIsCorrectTheSystemReturnsBoolean() throws InvalidAccountNumberException, InsufficientBalException{
		Account acc1= new Account();
		acc1.setAccNumber(101);
		acc1.setAmount(5000);
		Account acc2= new Account();
		acc2.setAccNumber(102);
		acc2.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc1);
		when(accRepo.search(102)).thenReturn(acc2);
		assertTrue(accService.fundsTransfer(101, 102, 4000));
		
	}
	
	/*
	 * Show Balance
	 * 1. when the account number is incorrect throw exception
	 * 2. when the account number is correct returns the amount
	 */
	@Test(expected=InvalidAccountNumberException.class)
	public void whenTheAccountNumberIsInvalidThrowException() throws InvalidAccountNumberException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc);
		assertEquals(5000, accService.showBalance(10));
	}
	
	@Test
	public void whenTheAccountNumberIsCorrect() throws InvalidAccountNumberException{
		Account acc= new Account();
		acc.setAccNumber(101);
		acc.setAmount(5000);
		when(accRepo.search(101)).thenReturn(acc);
		assertEquals(5000, accService.showBalance(101));
	}
}
