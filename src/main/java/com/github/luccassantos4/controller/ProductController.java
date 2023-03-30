package com.github.luccassantos4.controller;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/products")
@Slf4j
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Inject
    ProductService productService;

    @Inject
    Validator validator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDTO> findAllProducts(){
        return productService.getAllProducts();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response listProductsById(@PathParam("id") Long id) {

        ProductDTO dto = productService.getProductsById(id);

        return Response.ok().entity(dto).build();
    }

    @POST
    @Transactional
    public Response saveProduct(@Valid ProductDTO productDTO){

        logger.info(String.valueOf(productDTO));

        try {
            productService.createNewProduct(productDTO);
            return Response.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateProduct(@PathParam("id") Long id, @Valid ProductDTO productDTO){

        try {
            productService.updateProduct(id, productDTO);
            return Response.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();

        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteProduct(@PathParam("id") Long id){

        try {
            productService.deleteProduct(id);
            return Response.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();

        }
    }


}
