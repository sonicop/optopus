package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends CrudRepository<Product, String> {
  
}
