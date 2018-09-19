package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends CrudRepository<Product, String> {
  @Query(value ="SELECT * FROM products WHERE UPPER(CONCAT(sku,' ',name,' ',COALESCE(tags,''))) REGEXP UPPER(:keywords) ORDER BY UPPER(name) LIMIT 10", nativeQuery = true)
  @Cacheable(value = "ProductRepository.findByKeyword", key = "#keywords.toUpperCase()")
  public List<Product> findByKeyword(@Param("keywords") String keywords);
}
