package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Image;
import java.util.List;
import java.util.UUID;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, UUID> {
  
  List<Image> findAllByProductSkuAndUsedInTransactionIdOrderBySortNumber(@Param("sku") String sku, @Param("usedInTransactionId") UUID usedInTransactionId);
  
  @Cacheable(value = "ImageRepository.findLastByProductSkuAndUsedInTransactionIdIsNull", key = "#sku")
  List<Image> findLastByProductSkuAndUsedInTransactionIdIsNull(@Param("sku") String sku);  
}
