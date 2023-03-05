package com.github.luccassantos4.controller;
import com.github.luccassantos4.entity.ProductEntity;
import com.github.luccassantos4.repository.ProductRepository;
import io.quarkus.test.junit.QuarkusTest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
public class ProdutoResourceTest {

    @Inject
    ProductRepository entityManager;

    Long productId1;

    @BeforeEach
    @Transactional
    void setUp() {
        ProductEntity produto1 = new ProductEntity(null, "Produto 1", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(3000.00));
        ProductEntity produto2 = new ProductEntity(null, "Produto 2", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(3000.00));
        ProductEntity produto3 = new ProductEntity(null, "Produto 3", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(3000.00));

        entityManager.persist(produto1);
        entityManager.persist(produto2);
        entityManager.persist(produto3);

        productId1 = produto1.getId();
    }

    @Test
    @Transactional
    @DisplayName("Cadastrar produto")
    public void criarProduto() {
        ProductEntity produto = new ProductEntity();
        produto.setName("Produto 4");
        produto.setDescription("PRODUTO DE GRANDE PORTE 260KG");
        produto.setCategory("ELETRONICO");
        produto.setModel("MADEIRA");
        produto.setPrice(BigDecimal.valueOf(3000.00));

        given()
                .contentType("application/json")
                .body(produto)
                .when()
                .post("/api/products")
                .then()
                .statusCode(200);
    }

    @Test
    @Transactional
    @DisplayName("Atualizar Produto")
    public void atualizarProduto() {

        ProductEntity produto4 = new ProductEntity(null, "Produto 4", "PRODUTO DE GRANDE PORTE 260KG","ELETRONICO","MADEIRA",BigDecimal.valueOf(4000.00));

        given()
                .contentType("application/json")
                .body(produto4)
                .when()
                .put("/api/products/{id}", productId1)
                .then()
                .statusCode(200);

        ProductEntity produtoAtualizado = entityManager.findById(productId1);
        assertThat(produtoAtualizado.getName(), is("Produto 4"));
    }

    @Test
    @Transactional
    @DisplayName("Listar Produtos")
    public void listarProdutos() {

        given()
                .contentType("application/json")
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(3));
    }

    @Test
    @Transactional
    @DisplayName("Buscar Produtos por ID")
    public void testarBuscarPorId() {

        given()
                .contentType("application/json")
                .when()
                .get("/api/products/{id}", productId1)
                .then()
                .statusCode(200)
                .body("name", is("Produto 1"));
    }

    @Test
    @Transactional
    @DisplayName("Excluir Produto")
    public void removerProduto() {

        given()
                .contentType("application/json")
                .when()
                .delete("/api/products/{id}", productId1)
                .then()
                .statusCode(200);

        ProductEntity produtoDeletado = entityManager.findById(productId1);
        assertThat(produtoDeletado, nullValue());
    }
}
