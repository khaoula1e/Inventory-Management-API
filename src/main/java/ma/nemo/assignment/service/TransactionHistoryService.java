package ma.nemo.assignment.service;

import ma.nemo.assignment.dto.TransactionHistoryDto;

public interface TransactionHistoryService {
    TransactionHistoryDto addTransaction(TransactionHistoryDto transactionHistoryDTO);

}
