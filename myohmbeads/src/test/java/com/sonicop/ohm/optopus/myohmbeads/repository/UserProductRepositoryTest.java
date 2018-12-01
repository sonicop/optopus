/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import com.sonicop.ohm.optopus.myohmbeads.model.User;
import com.sonicop.ohm.optopus.myohmbeads.model.UserProduct;
import java.util.UUID;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserProductRepositoryTest {
  
	@Autowired
	private UserProductRepository userProductRepository;
  
  @Test
  public void testSomeMethod() {
    UserProduct userProduct = new UserProduct();
    userProduct.setProduct(new Product("AAA004"));
    userProduct.setUser(new User(UUID.fromString("6c7acae9-9941-11e8-ab1c-080027d981a5")));
    //userProduct.setCreatedBy(new  User(UUID.fromString("6c7acae9-9941-11e8-ab1c-080027d981a5")));
    userProductRepository.save(userProduct);
  }
  
}
