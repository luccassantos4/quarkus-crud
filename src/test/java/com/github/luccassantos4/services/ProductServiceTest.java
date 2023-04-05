package com.github.luccassantos4.services;

import com.github.luccassantos4.dto.ProductDTO;
import com.github.luccassantos4.entity.ProductEntity;
import com.github.luccassantos4.repository.ProductRepository;
import com.github.luccassantos4.service.ProductService;
import io.quarkus.panache.common.Page;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;
    private Long productId;

    @BeforeEach
    @Transactional
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 1L;

        ProductEntity produto1 = new ProductEntity(null, "Produto 1", "PRODUTO DE GRANDE PORTE 260KG", "ELETRONICO", "MADEIRA", BigDecimal.valueOf(3000.00));
        productRepository.persist(produto1);
        productId = produto1.getId();
    }


    @Test
    @Transactional
    public void deleteShouldDeleteResourceWhenIdExists() {

        Mockito.when(productRepository.deleteById(1L)).thenReturn(true);
        Response response = service.deleteProduct(existingId);

        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        assertNull(response.getEntity());
        Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
    }
}
