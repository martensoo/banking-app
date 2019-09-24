/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.bankingapp.controller;

import com.bankingapp.exception.NotEnoughMoneyException;
import com.bankingapp.exception.ResourceNotFoundException;
import com.bankingapp.model.Account;
import com.bankingapp.model.AccountDTO;
import com.bankingapp.model.Transaction;
import com.bankingapp.repository.AccountRepository;
import com.bankingapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
  private final Long TRANSACTION_FEE = 0l;
  private final Long MIN_BALANCE = 0l;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private AccountRepository accountRepository;


  @ApiOperation("Transactions and balance info for the account")
  @GetMapping("/account/{account_id}")
  public ResponseEntity<AccountDTO> getTransactionsByAccountId(@PathVariable(value = "account_id") Long accountId)
      throws ResourceNotFoundException {
    Account account = accountRepository
            .findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));

    AccountDTO accountDTO = new AccountDTO();
    accountDTO.setId(account.getId());
    accountDTO.setCurrency(account.getCurrency());
    accountDTO.setBalanceInCents(account.getBalanceInCents());
    accountDTO.setIban(account.getIban());
    accountDTO.setOwner(account.getOwner());

    //TODO: Add list of the transactions
    //Transaction transaction =
    //        transactionRepository
    //        .findByAccountFromOrAccountTo(account, account)
    //        .orElseThrow(() -> new ResourceNotFoundException("Transaction for account not found: " + accountId));
    return ResponseEntity.ok().body(accountDTO);
  }

  @ApiOperation("Create transaction")
  @PostMapping("/transaction/{account_from_id}/{account_to_id}/{amount_in_cents}")
  public ResponseEntity<Transaction> createTransaction(
    @PathVariable(value = "account_from_id") Long accountFromId,
    @PathVariable(value = "account_to_id") Long accountToId,
    @PathVariable(value = "amount_in_cents") Long amountInCents) throws Exception {
    Account accountFrom = accountRepository.findById(accountFromId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountFromId));

    Account accountTo = accountRepository.findById(accountToId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountToId));

    if(Long.compare(accountFrom.getBalanceInCents(), amountInCents + TRANSACTION_FEE)<0){
      throw new NotEnoughMoneyException("Not enough money on account : " + accountFrom.getIban());
    }

    accountTo.setBalanceInCents(accountTo.getBalanceInCents() + amountInCents);
    accountFrom.setBalanceInCents(accountFrom.getBalanceInCents() -(amountInCents + TRANSACTION_FEE));

    accountFrom = accountRepository.save(accountFrom);
    accountTo = accountRepository.save(accountTo);
    Transaction transaction = new Transaction(accountFrom, accountTo, amountInCents);
    transaction = transactionRepository.save(transaction);
    return ResponseEntity.ok(transaction);
  }

}
