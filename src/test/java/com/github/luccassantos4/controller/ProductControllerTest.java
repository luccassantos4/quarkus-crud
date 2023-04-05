package com.github.luccassantos4.controller;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.entity.ProductEntity;
import com.github.luccassantos4.repository.ProductRepository;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @Inject
    ProductRepository entityManager;

    @TestHTTPResource("/products")
    URL apiURL;

    Long productId;

    @BeforeEach
    @Transactional
    void setUp() {

        ProductEntity produto1 = new ProductEntity(null, "Produto 1", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(3000.00));
        ProductEntity produto2 = new ProductEntity(null, "Produto 2", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(3000.00));
        ProductEntity produto3 = new ProductEntity(null, "Produto 3", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(3000.00));

        entityManager.persist(produto1);
        entityManager.persist(produto2);
        entityManager.persist(produto3);

        productId = produto1.getId();
    }

    @Test
    @DisplayName("Retornar lista de produtos cadastrados") //100%
    @Order(1)
    public void findAllProducts() {

        given()
                .when()
                .get(apiURL)
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(3));
    }

    @Test
    @DisplayName("Buscar Produto por ID")
    @Order(2)
    public void findProductsById() {

        given()
                .pathParam("id", productId)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(200)
                .body("name", is("Produto 1"));
    }

    @Test
    @DisplayName("Cadastro de produtos")
    @Order(3)
    public void  createProduct() {

        var productRequest = new ProductDTO();
        productRequest.setDescription("MEMORIA DE 250G");
        productRequest.setName("PS5 2023");
        productRequest.setCategory("GAMES");
        productRequest.setModel("PRO");
        productRequest.setPrice(BigDecimal.valueOf(5000.00));

        given()
            .contentType(ContentType.JSON)
            .body(productRequest)
        .when()
            .post(apiURL)
        .then()
            .statusCode(200);
    }

    @Test
    @DisplayName("Atualizar de produtos")
    @Order(4)
    @Transactional
    public void  updateProduct() {

        ProductEntity produto4 = new ProductEntity(null, "Produto 4", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(4000.00));

        given()
                .contentType("application/json")
                .body(produto4)
                .when()
                .put("/products/{id}", productId)
                .then()
                .statusCode(200);

        ProductEntity produtoAtualizado = entityManager.findById(productId);
        assertThat(produtoAtualizado.getName(), is("Produto 4"));
    }

    @Test
    @DisplayName("Delete produto por id")
    @Order(5)
    public void  deleteProductById() {

        given()
                .pathParam("id", productId)
                .when()
                .delete("/products/{id}")
                .then()
                .statusCode(200);

        ProductEntity produtoDeletado = entityManager.findById(productId);
        assertThat(produtoDeletado, nullValue());
    }
}