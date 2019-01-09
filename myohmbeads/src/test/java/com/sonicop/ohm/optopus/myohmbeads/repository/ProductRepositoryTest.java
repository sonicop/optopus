package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
  
	@Autowired
	private ProductRepository productRepository;

  @Test
  public void testSomeMethod() {
    List<Product> list;
    list = productRepository.findByKeyword("aaa|atom");
    assertNotNull(list);
  }
  
}
