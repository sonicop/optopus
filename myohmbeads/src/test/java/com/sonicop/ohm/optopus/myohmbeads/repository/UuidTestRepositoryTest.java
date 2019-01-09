package com.sonicop.ohm.optopus.myohmbeads.repository;

import com.sonicop.ohm.optopus.myohmbeads.model.UuidTest;
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
public class UuidTestRepositoryTest {
  
	@Autowired
	private UuidTestRepository uuidTestRepository;
  
  @Test
  public void testSomeMethod() {
    UuidTest record = new UuidTest();
    record.setName("Test #1");
    UuidTest updatedRecord = uuidTestRepository.save(record);
    System.out.println("Record: " + updatedRecord.getId());
    String id = updatedRecord.getId().toString();
    UuidTest matchingRecord = uuidTestRepository.findById(UUID.fromString(id)).get();
    System.out.println("Record: " + matchingRecord.getId());
  }
  
}
