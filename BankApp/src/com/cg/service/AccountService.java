package com.cg.service;

import com.cg.exception.InsufficientBalException;
import com.cg.exception.InvalidAccountNumberException;
import com.cg.exception.InvalidAmountException;
import com.cg.model.Account;

public interface AccountService {
	
	Account createAccount(int accNum,int amount) throws InsufficientBalException;

	int depositAmount(int accNum,int amount) throws InvalidAmountException;
	
	int withdrawAmount(int accNum,int amount) throws InsufficientBalException, InvalidAmountException;

	boolean fundsTransfer(int acc1,int acc2,int amount) throws InvalidAccountNumberException, InsufficientBalException;

	int showBalance(int accNum) throws InvalidAccountNumberException;
}