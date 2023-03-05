package com.github.luccassantos4.controller;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.error.ResponseError;
import com.github.luccassantos4.service.ProductService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/api/products")
public class ProductController {

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
