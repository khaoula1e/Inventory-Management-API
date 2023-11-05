package ma.nemo.assignment.serviceImpl;

import lombok.RequiredArgsConstructor;
import ma.nemo.assignment.domain.TransactionHistory;
import ma.nemo.assignment.dto.TransactionHistoryDto;
import ma.nemo.assignment.mapper.TransactionHistoryMapper;
import ma.nemo.assignment.repository.TransactionHistoryRepository;
import ma.nemo.assignment.service.TransactionHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionHistoryServiceImpl implements TransactionHistoryService{
    private final TransactionHistoryMapper transactionMapper;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistoryDto addTransaction(TransactionHistoryDto transactionHistoryDTO) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setTransactionType(transactionHistoryDTO.getTransactionType());
        transactionHistory.setUser(transactionHistoryDTO.getUser());
        transactionHistory.setProduct(transactionHistoryDTO.getProduct());
        transactionHistory.setQuantity(transactionHistoryDTO.getQuantity());

        transactionHistoryRepository.save(transactionHistory);


        return transactionMapper.toDTO(transactionHistory);


    }
}
