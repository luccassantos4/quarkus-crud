package com.github.luccassantos4.service;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.entity.ProductEntity;
import com.github.luccassantos4.interfaces.IProductMappear;
import com.github.luccassantos4.repository.ProductRepository;
import com.github.luccassantos4.service.exceptions.ResourceNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    @Inject
    IProductMappear productMappear;

    public List<ProductDTO> getAllProducts(){

        List<ProductDTO> products = new ArrayList<>();

        /* methods 1 */
        productRepository.findAll().stream().forEach(item -> {
            products.add(productMappear.toDto(item));
        });

        return products;

        /* methods 2 */
//        return	productRepository.findAll().stream().
//                map(item-> mapper.toDto(item)).collect(Collectors.toList());
    }

    public ProductDTO getProductsById(Long id){

        ProductEntity entity = productRepository.findById(id);
        return new ProductDTO(productMappear.toDto(entity));

//        Optional<ProductEntity> obj = Optional.ofNullable(productRepository.findById(id));
//        ProductEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    public void createNewProduct(ProductDTO productDTO){
        productRepository.persist(productMappear.toEntity(productDTO));
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


    public void updateProduct(Long id, ProductDTO productDto){

        ProductEntity productEntity = productRepository.findById(id);
        productMappear.updateProductFromDto(productDto, productEntity);

        productRepository.persist(productEntity);

    /*    productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setCategory(productDto.getCategory());
        productEntity.setModel(productDto.getModel());
        productEntity.setPrice(productDto.getPrice());*/
    }


//    private ProductDTO mapProductsEntityToDTO(ProductEntity productEntity){
//
//        ProductDTO productDTO = new ProductDTO();
//
//        productDTO.setName(productEntity.getName());
//        productDTO.setDescription(productEntity.getDescription());
//        productDTO.setCategory(productEntity.getCategory());
//        productDTO.setModel(productEntity.getModel());
//        productDTO.setPrice(productEntity.getPrice());
//
//        return productDTO;
//    }

//    private ProductEntity mapProductsDtoToEntity(ProductDTO productDTO){
//
//        ProductEntity product = new ProductEntity();
//
//        product.setName(productDTO.getName());
//        product.setDescription(productDTO.getDescription());
//        product.setCategory(productDTO.getCategory());
//        product.setModel(productDTO.getModel());
//        product.setPrice(productDTO.getPrice());
//
//        return product;
//    }
}
