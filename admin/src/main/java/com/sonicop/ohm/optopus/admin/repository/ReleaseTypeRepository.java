package com.sonicop.ohm.optopus.admin.repository;

import com.sonicop.ohm.optopus.admin.model.ReleaseType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReleaseTypeRepository extends PagingAndSortingRepository<ReleaseType, String> {
  
}
