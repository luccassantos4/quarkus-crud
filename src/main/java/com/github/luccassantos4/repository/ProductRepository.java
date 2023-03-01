package com.github.luccassantos4.repository;

import com.github.luccassantos4.entity.ProductEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<ProductEntity> {

}
