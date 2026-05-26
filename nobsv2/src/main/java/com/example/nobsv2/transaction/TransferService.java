package com.example.nobsv2.transaction;

import com.example.nobsv2.Command;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferService implements Command<TransferDTO, String> {
    private final BankAccountRepository bankAccountRepository;

    public TransferService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> execute(TransferDTO transferDTO) {
        Optional<BankAccount> fromAccount = bankAccountRepository.findById(transferDTO.getFromUser());
        Optional<BankAccount> toAccount = bankAccountRepository.findById(transferDTO.getToUser());

        if(fromAccount.isEmpty() || toAccount.isEmpty()) {
            throw new RuntimeException("One or both of the accounts don't exist!");
        }

        BankAccount from = fromAccount.get();
        BankAccount to = toAccount.get();

        //add & deduct
        deduct(from, transferDTO.getAmount());
        add(to, transferDTO.getAmount());

        return ResponseEntity.ok("Transfer Successful");
    }

    private void deduct(BankAccount bankAccount, double amount) {
        if(bankAccount.getBalance() < amount) {
            throw new RuntimeException("You cannot deduct more than you have bozo");
        }
        bankAccount.setBalance(bankAccount.getBalance() - amount);
    }

    private void add(BankAccount bankAccount, double amount) {
        bankAccount.setBalance(bankAccount.getBalance() + amount);
    }
}
