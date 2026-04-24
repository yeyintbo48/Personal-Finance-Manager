package com.personal.financemanager.controller;

import com.personal.financemanager.repository.TransactionRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.personal.financemanager.dtos.Category;
import com.personal.financemanager.dtos.TransactionRequest;
import com.personal.financemanager.entity.PaymentRequest;
import com.personal.financemanager.entity.Transaction;
import com.personal.financemanager.entity.User;
import com.personal.financemanager.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transcation Controller",description = "ငွေပေးချေမှုနှင့် မှတ်တမ်းများ စီမံခန့်ခွဲသည့် API များ")
public class TransactionController {
    private final TransactionRepo transactionRepo;
    @Autowired
    private TransactionService transactionService;

    TransactionController(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createTransaction(@RequestBody @Valid TransactionRequest request,@AuthenticationPrincipal User currentUser) {
        String userEmail = currentUser.getEmail();
        Long userId = currentUser.getId();
        return ResponseEntity.ok(userId + "Transcation added for user:" + userEmail);
    } 
    
    @GetMapping
    public ResponseEntity <List<Transaction>> getAllTransaction() {
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }

    @GetMapping("/{id}")
    public ResponseEntity <Optional<Transaction>>getTransactionById(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
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
        return ResponseEntity.ok("Payment Successful");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getHistory(@AuthenticationPrincipal User currentUser,@RequestParam(required = false)Category category) {
        List <Transaction> transactions;
        if(category != null){
            transactions = transactionRepo.findByUserIdAndCategory(currentUser.getId(),category);
        }else{
            transactions = transactionRepo.findByUserId(currentUser.getId());
        }
        return ResponseEntity.ok(transactions);
    }
}
