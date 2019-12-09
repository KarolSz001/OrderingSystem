package com.app.model.dto;


import com.app.model.Category;
import com.app.model.Country;
import com.app.model.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String productName;
    private BigDecimal price;
    private String categoryName;
    private String producerName;
    private String countryName;

}
