package com.github.luccassantos4.service;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.entity.ProductEntity;
import com.github.luccassantos4.repository.ProductRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public List<ProductDTO> getAllProducts(){

        List<ProductDTO> products = new ArrayList<>();

        productRepository.findAll().stream().forEach(item -> {
            products.add(mapProductsEntityToDTO(item));
        });

        return products;
    }

    public void createNewProduct(ProductDTO productDTO){
        productRepository.persist(mapProductsDtoToEntity(productDTO));
    }

    public void updateProduct(Long id, ProductDTO productDto){

        ProductEntity productEntity = productRepository.findById(id);

        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setCategory(productDto.getCategory());
        productEntity.setModel(productDto.getModel());
        productEntity.setPrice(productDto.getPrice());


        productRepository.persist(productEntity);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


    private ProductDTO mapProductsEntityToDTO(ProductEntity productEntity){

        ProductDTO product = new ProductDTO();

        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setCategory(productEntity.getCategory());
        product.setModel(productEntity.getModel());
        product.setPrice(productEntity.getPrice());

        return product;
    }

    private ProductEntity mapProductsDtoToEntity(ProductDTO productDTO){

        ProductEntity product = new ProductEntity();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setModel(productDTO.getModel());
        product.setPrice(productDTO.getPrice());

        return product;
    }
}
