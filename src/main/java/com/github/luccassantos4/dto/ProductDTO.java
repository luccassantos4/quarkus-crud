package com.github.luccassantos4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Jacksonized
public class ProductDTO {

    @NotBlank(message = "Name is Required")
    private String name;

    @NotBlank(message = "Description is Required")
    @Size(min = 10, max = 200, message
            = "Description must be between 10 and 200 characters")
    private String description;

    @NotBlank(message = "Category is Required")
    private String category;

    @NotBlank(message = "Model is Required")
    private String model;

    @NotNull(message = "Price is Required")
    private BigDecimal price;

    public ProductDTO(ProductDTO entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.category = entity.getCategory();
        this.model = entity.getModel();
        this.price = entity.getPrice();
    }
}
