package com.github.luccassantos4.interfaces;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "cdi")
public interface IProductMappear {

    @Mapping(target="id", ignore=true)
    ProductEntity toEntity(ProductDTO productDTO);

    ProductDTO toDto(ProductEntity productEntity);

    void updateProductFromDto(ProductDTO productDTO, @MappingTarget ProductEntity productEntity);
}
