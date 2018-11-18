/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Image;
import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import com.sonicop.ohm.optopus.myohmbeads.model.User;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageRepositoryTest {
  
  @Autowired
  ImageRepository imageRepository;
  
  @Test
  public void testFindLastByProductSkuAndUsedInTransactionIdIsNull() {
    List<Image> imageList = imageRepository.findLastByProductSkuAndUsedInTransactionIdIsNull("AAA061");
    assertNotNull(imageList);
  }
  
  @Test
  public void test() {
    List<Image> imageList = imageRepository.findAllByProductSkuAndCreatedByOrderBySortNumber("AAA074", UUID.fromString("6c7acae9-9941-11e8-ab1c-080027d981a5"));
    assertNotNull(imageList);
  }
  
}
