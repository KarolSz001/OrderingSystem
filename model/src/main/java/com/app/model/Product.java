package com.app.model;


import com.app.model.enums.GuaranteeComponents;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private BigDecimal price;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CustomerOrder> customerOrders;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Stock> stocks;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "guarantee_components",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @Column(name = "guarantee_component")
    @Enumerated(EnumType.STRING)
    private Set<GuaranteeComponents> components;


}
