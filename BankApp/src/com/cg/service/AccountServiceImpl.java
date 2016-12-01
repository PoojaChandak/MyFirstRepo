package com.cg.service;

import com.cg.exception.InsufficientBalException;
import com.cg.exception.InvalidAccountNumberException;
import com.cg.exception.InvalidAmountException;
import com.cg.model.Account;
import com.cg.repo.AccountRepo;

public class AccountServiceImpl implements AccountService {

	AccountRepo accountRepo;

	public AccountServiceImpl(AccountRepo accRepo) {
		this.accountRepo = accRepo;
	}

	@Override
	public Account createAccount(int accNum, int amount)
			throws InsufficientBalException {
		if (amount < 500) {
			throw new InsufficientBalException();
		}
		Account acc = new Account();
		acc.setAccNumber(accNum);
		acc.setAmount(amount);

		if (accountRepo.save(acc)) {
			return acc;
		}
		return null;
	}

	@Override
	public int depositAmount(int accNum, int amount)
			throws InvalidAmountException {
		Account account = accountRepo.search(accNum);

		if (amount <= 0) {
			throw new InvalidAmountException();
		}
		account.setAmount(account.getAmount() + amount);
		accountRepo.save(account);
		return account.getAmount();
	}

	@Override
	public int withdrawAmount(int accNum, int amount) throws InsufficientBalException, InvalidAmountException {
		Account account = accountRepo.search(accNum);
		if (amount <= 0) {
			throw new InvalidAmountException();
		}
		
		if (account.getAmount() - amount < 500) {
			throw new InsufficientBalException();
		}
		account.setAmount(account.getAmount() - amount);
		accountRepo.save(account);
		return account.getAmount();
	}

	@Override
	public boolean fundsTransfer(int acc1, int acc2, int amount) throws InvalidAccountNumberException, InsufficientBalException {
		if(Integer.toString(acc1).length()<3 || Integer.toString(acc2).length()<3  ){
			throw new InvalidAccountNumberException();
		}
		Account account1=accountRepo.search(acc1);
		Account account2=accountRepo.search(acc2);
		
		if(account1.getAmount()-amount < 500){
			throw new InsufficientBalException();
		}
		
		//if((account1.getAmount()+account2.getAmount())==(account1.getAmount()-amount)+(account2.getAmount()+amount))
			account1.setAmount(account1.getAmount()-amount);
			account2.setAmount(account2.getAmount()+amount);
			accountRepo.save(account1);
			accountRepo.save(account2);
			return true;
	}

	@Override
	public int showBalance(int accNum) throws InvalidAccountNumberException {
		if(Integer.toString(accNum).length()<3 ){
			throw new InvalidAccountNumberException();
		}
		Account acc=accountRepo.search(accNum);
		return acc.getAmount();
	}
	
	

}
