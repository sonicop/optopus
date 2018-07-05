package com.sonicop.ohm.optopus.admin.repository;

import com.sonicop.ohm.optopus.admin.model.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RatingRepository extends CrudRepository<Rating, Integer> {
  
}
