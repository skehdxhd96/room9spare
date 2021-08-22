package com.goomoong.room9backend.domain.payment;

import com.goomoong.room9backend.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class payment extends BaseEntity { // BaseEntity.CreatedAt = 결제일시

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_Id")
    private Long Id;



    private String payMethod;
    private Integer totalPrice;
    private paymentStatus payStatus;
}
