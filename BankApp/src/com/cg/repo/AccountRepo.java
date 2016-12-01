package com.cg.repo;

import com.cg.model.Account;

public interface AccountRepo {
	
	boolean save(Account acc);
	Account search(int accNum);

}