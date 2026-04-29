package com.personal.financemanager.controller;

import com.personal.financemanager.repository.TransactionRepo;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.personal.financemanager.dtos.TransactionRequest;
import com.personal.financemanager.entity.Expense;
import com.personal.financemanager.entity.PaymentRequest;
import com.personal.financemanager.entity.Transaction;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@Tag(name = "Transcation Controller",description = "ငွေပေးချေမှုနှင့် မှတ်တမ်းများ စီမံခန့်ခွဲသည့် API များ")
public class TransactionController {
    private final TransactionRepo transactionRepo;
    private final TransactionService transactionService;

    @PostMapping("/add")
    public ResponseEntity <Transaction> createTransaction(@RequestBody @Valid TransactionRequest request,@AuthenticationPrincipal User currentUser) {
        Transaction savedTransaction = transactionService.createTransaction(request,currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    } 
    
    @GetMapping
    public ResponseEntity <List<Transaction>> getAllTransaction(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(transactionRepo.findByUserId(currentUser.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id){
        Transaction transaction = transactionService.getTransactionById(id).orElseThrow(() -> new RuntimeException("Transaction not found!"));
        return ResponseEntity.ok(transaction);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @RequestBody @Valid TransactionRequest request) {
        return ResponseEntity.ok(transactionService.updateTransaction(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "ငွေပေးချေမှု ပြုလုပ်ခြင်:",description = "Token ပါရမည့်အပြင်၊ အတည်ပြုရန်အတွက် Password ကိုပါ ထပ်မံစစ်ဆေးပါသည်။")
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - JWT missing/invalid"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Wrong password")
    })
    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestBody @Valid PaymentRequest request,@AuthenticationPrincipal User currentUser) {
        transactionService.processPayment(currentUser.getEmail(),request);
        Expense expense = new Expense(null,request.amount(),request.category(),"Payment Processed",LocalDate.now(),"Online Payment");
        transactionService.processExpense(expense);
        return ResponseEntity.ok("Payment Successful and Budget updated.");
    }

    @GetMapping("/history/paged")
    public ResponseEntity<Page<Transaction>> getPagedHistory(
        @AuthenticationPrincipal User currentUser,
        @RequestParam(defaultValue = "0" )int page,
        @RequestParam(defaultValue = "10")int size
    ){
        PageRequest pageable = PageRequest.of(page,size,Sort.by("transactionDate").descending());
        Page<Transaction> transactions = transactionRepo.findByUserId(currentUser.getId(),pageable);
        return ResponseEntity.ok(transactions);
    }
}
