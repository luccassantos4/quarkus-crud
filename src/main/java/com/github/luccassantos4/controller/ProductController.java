package com.github.luccassantos4.controller;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.service.ProductService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.util.List;

@Path("/api/products")
public class ProductController {

    @Inject
    ProductService productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductDTO> findAllProducts(){
        return productService.getAllProducts();
    }

    @POST
    @Transactional
    public Response saveProduct(ProductDTO productDTO){

        try {
            productService.createNewProduct(productDTO);
            return Response.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();

        }
    }

    @PUT
    @Transactional
    public Response saveProduct(@PathParam("id") Long id, ProductDTO productDTO){

        try {
            productService.updateProduct(id, productDTO);
            return Response.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();

        }
    }

    @DELETE
    @Transactional
    public Response saveProduct(@PathParam("id") Long id){

        try {
            productService.deleteProduct(id);
            return Response.ok().build();

        }catch (Exception e){
            e.printStackTrace();
            return Response.serverError().build();

        }
    }


}
