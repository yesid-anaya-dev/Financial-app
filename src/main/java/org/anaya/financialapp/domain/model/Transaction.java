package org.anaya.financialapp.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.anaya.financialapp.domain.enums.TransactionType;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction ")
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "receiver_account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account receiverAccount;

    @JoinColumn(name = "sender_account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account senderAccount;

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
