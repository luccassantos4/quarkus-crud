package com.github.luccassantos4.service;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.entity.ProductEntity;
import com.github.luccassantos4.interfaces.IProductMappear;
import com.github.luccassantos4.repository.ProductRepository;
import com.github.luccassantos4.service.exceptions.ResourceNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

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

    public Response deleteProduct(Long id) {

        boolean deleted;

        try {
            deleted = productRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }

        return deleted ? Response.noContent().build() : Response.status(NOT_FOUND).build();
    }


    public void updateProduct(Long id, ProductDTO productDto){
        try {
            ProductEntity productEntity = productRepository.findById(id);
            productMappear.updateProductFromDto(productDto, productEntity);
            productRepository.persist(productEntity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }
}
