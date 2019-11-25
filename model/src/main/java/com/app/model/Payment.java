package com.app.model;

import com.app.model.enums.EPayment;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EPayment payment;

    @OneToMany(mappedBy = "payment")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CustomerOrder> customerOrders;

}
