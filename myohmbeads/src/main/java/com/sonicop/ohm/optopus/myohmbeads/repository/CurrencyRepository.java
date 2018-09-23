package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Currency;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CurrencyRepository extends CrudRepository<Currency, String> {
  @Query(value ="SELECT * FROM currencies WHERE UPPER(CONCAT(currency_code,' ',name)) REGEXP UPPER(:keyword) ORDER BY UPPER(name) LIMIT 10", nativeQuery = true)
  @Cacheable(value = "CurrencyRepository.findByKeyword", key = "#keyword.toUpperCase()")
  public List<Currency> findByKeyword(@Param("keyword") String keyword);

  
}
