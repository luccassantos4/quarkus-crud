package com.github.luccassantos4.controller;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.entity.ProductEntity;
import com.github.luccassantos4.repository.ProductRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
@QuarkusTest
@TestHTTPEndpoint(ProductController.class)
class ProductControllerTest {

    @Test
    @DisplayName("Retornar lista de produtos cadastrados")
    void findAllProducts() {

        given()
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Cadastro de produtos")
    void saveProduct() {

        var productRequest = new ProductDTO();
        productRequest.setDescription("MEMORIA DE 250G");
        productRequest.setName("PS4 DESBLOQUEADO");
        productRequest.setCategory("NOVO");
        productRequest.setModel("SLIM");
        productRequest.setPrice(BigDecimal.valueOf(3000.00));

        given()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .when()
                .post()
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Cadastro de produtos sem o corpo")
    void saveProductSemBody() {

        var productRequest = new ProductDTO();

        given()
                .contentType(ContentType.JSON)
                .body(productRequest)
                .when()
                .post()
                .then()
                .statusCode(400);
    }
}