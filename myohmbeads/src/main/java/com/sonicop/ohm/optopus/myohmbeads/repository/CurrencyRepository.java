package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CurrencyRepository extends CrudRepository<Currency, String> {
  
}
