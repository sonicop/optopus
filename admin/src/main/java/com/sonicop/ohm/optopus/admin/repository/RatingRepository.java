package com.sonicop.ohm.optopus.admin.repository;

import com.sonicop.ohm.optopus.admin.model.Rating;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RatingRepository extends PagingAndSortingRepository<Rating, Integer> {
  
}
