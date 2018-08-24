package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Seller;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SellerRepository extends CrudRepository<Seller, String> {
  
}
