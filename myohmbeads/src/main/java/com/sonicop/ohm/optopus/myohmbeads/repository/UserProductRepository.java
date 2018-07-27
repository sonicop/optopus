package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.UserProduct;
import com.sonicop.ohm.optopus.myohmbeads.model.UserProductPK;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserProductProjection.class)
public interface UserProductRepository extends CrudRepository<UserProduct, UserProductPK> {

  List<UserProduct > findAllByUserUserId(@Param("userId") int userId);

}
