package com.sonicop.ohm.optopus.myohmbeads.controller;

import com.sonicop.ohm.optopus.myohmbeads.dto.PurchaseTransaction;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
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



  @Test
  public void testSomeMethod2() {
    List<Map<String,String>> options = myOhmBeadsController.getProductsByKeyword("AAA004");
    assertNotNull(options);
    assertEquals(1, options.size());
    String textProperty = options.get(0).get("textProperty");
    int startPos = textProperty.lastIndexOf("(") + 1;
    int endPos = textProperty.lastIndexOf(")");
    String sku = textProperty.substring(startPos, endPos);
    System.out.println(sku);
  }
}
