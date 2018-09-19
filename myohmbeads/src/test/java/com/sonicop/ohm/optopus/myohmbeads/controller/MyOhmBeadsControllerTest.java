package com.sonicop.ohm.optopus.myohmbeads.controller;

import com.sonicop.ohm.optopus.myohmbeads.dto.PurchaseTransaction;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyOhmBeadsControllerTest {
  
  @Autowired
  MyOhmBeadsController myOhmBeadsController;
  
  @Test
  public void testSomeMethod() {
    PurchaseTransaction transaction = new PurchaseTransaction();
    transaction.setSku("AAA001");
    transaction.setCurrencyCode("ANCB");
    ResponseEntity entity = myOhmBeadsController.saveTransaction(transaction, null);
    System.out.println("Entity: " + entity);
  }
}
