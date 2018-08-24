package com.sonicop.ohm.optopus.myohmbeads.controller;

import com.sonicop.ohm.optopus.myohmbeads.dto.PurchaseTransaction;
import com.sonicop.ohm.optopus.myohmbeads.model.Currency;
import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import com.sonicop.ohm.optopus.myohmbeads.model.User;
import com.sonicop.ohm.optopus.myohmbeads.model.UserProduct;
import com.sonicop.ohm.optopus.myohmbeads.repository.UserProductRepository;
import com.sonicop.ohm.optopus.myohmbeads.repository.UserRepository;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyOhmBeadsController {
  
	@Autowired
	private UserProductRepository userProductRepository;
  
  @Autowired
  private UserRepository userRepository;
  
	@PostMapping(value = "/purchaseTransactions")
	public ResponseEntity saveTransaction(@RequestBody PurchaseTransaction transaction, Principal principal) {
    UserProduct userProduct = new UserProduct();
    userProduct.setProduct(new Product(transaction.getSku()));
    userProduct.setUser(new User(UUID.fromString(transaction.getUserId())));
    userProduct.setCurrency(new Currency(transaction.getCurrencyCode()));
    userProduct.setPurchasePrice(new BigDecimal(transaction.getPurchasePrice()));
    userProduct.setPurchaseFrom(transaction.getPurchaseFrom());
    if (!StringUtils.isEmpty(transaction.getPurchaseDate())) {
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
      Date purchaseDate;
      try {
        purchaseDate = sf.parse(transaction.getPurchaseDate());
        userProduct.setPurchaseDate(purchaseDate);
      } catch (ParseException ex) {
        throw new IllegalArgumentException(ex);
      }
    }
    userProduct.setNote(transaction.getNote());
    userProduct.setCreateTime(new Date());
    if (principal != null) {
      User user = userRepository.findOneByUsername(principal.getName());
      userProduct.setCreatedBy(user.getUserId());
    }
    userProductRepository.save(userProduct);
		return new ResponseEntity(transaction, HttpStatus.OK);
	}
  
  
  
  @RequestMapping(value = "/purchaseTransactions/{transactionId}", method = RequestMethod.PUT)
	public ResponseEntity updateTransaction(@RequestBody PurchaseTransaction transaction, @PathVariable String transactionId, Principal principal) {
    UserProduct userProduct = userProductRepository.findById(UUID.fromString(transactionId)).orElse(null);
    userProduct.setCurrency(new Currency(transaction.getCurrencyCode()));
    userProduct.setPurchasePrice(new BigDecimal(transaction.getPurchasePrice()));
    userProduct.setPurchaseFrom(transaction.getPurchaseFrom());
    if (!StringUtils.isEmpty(transaction.getPurchaseDate())) {
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
      Date purchaseDate;
      try {
        purchaseDate = sf.parse(transaction.getPurchaseDate());
        userProduct.setPurchaseDate(purchaseDate);
      } catch (ParseException ex) {
        throw new IllegalArgumentException(ex);
      }
    }
    userProduct.setNote(transaction.getNote());
    userProduct.setUpdatedTime(new Date());
    if (principal != null) {
      User user = userRepository.findOneByUsername(principal.getName());
      userProduct.setUpdatedBy(user.getUserId());
    }
    userProductRepository.save(userProduct);
		return new ResponseEntity(transaction, HttpStatus.OK);
	}
  
  
  
  @RequestMapping(value = "/purchaseTransactions/{transactionId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteTransaction(@PathVariable String transactionId, Principal principal) {
    UserProduct userProduct = userProductRepository.findById(UUID.fromString(transactionId)).orElse(null);
    if (userProduct != null) {
      userProduct.setDeletedTime(new Date());
      if (principal != null) {
        User user = userRepository.findOneByUsername(principal.getName());
        userProduct.setDeletedBy(user.getUserId());
      }
      userProductRepository.save(userProduct);
    }
	}



  @GetMapping(value = "/purchaseTransactions")
  // TODO: Security - use principal instead of passing in userId
  public List<PurchaseTransaction> getTransactionsByUserId(@RequestParam("userId") String userId) {
    List<UserProduct> userProductList = userProductRepository.findAllByUserUserIdAndDeletedTimeIsNullOrderByCreateTimeDesc(UUID.fromString(userId));
    List<PurchaseTransaction> resultList  = null;
    if (userProductList != null && !userProductList.isEmpty()) {
      resultList = new ArrayList(userProductList.size());
      for (UserProduct userProduct: userProductList) {
        PurchaseTransaction transaction = new PurchaseTransaction();
        transaction.setTransactionId(userProduct.getTransactionId().toString());
        transaction.setBrandId(userProduct.getProduct().getBrandId().getBrandId().toString());
        transaction.setSku(userProduct.getProduct().getSku());
        transaction.setProductName(userProduct.getProduct().getName());
        transaction.setCreateTime(userProduct.getCreateTime());
        transaction.setCurrencyCode(userProduct.getCurrency().getCurrencyCode());
        transaction.setPurchasePrice(userProduct.getPurchasePrice().toString());
        transaction.setPurchaseFrom(userProduct.getPurchaseFrom());
        if (!StringUtils.isEmpty(userProduct.getPurchaseDate())) {
          SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
          transaction.setPurchaseDate(sf.format(userProduct.getPurchaseDate()));
        }
        transaction.setNote(userProduct.getNote());
        if (userProduct.getProduct().getImageList() != null && userProduct.getProduct().getImageList().size() > 0) {
          transaction.setImageReference(userProduct.getProduct().getImageList().get(0).getReference());
        }
        resultList.add(transaction);
      }
    }
    return resultList;
  }
  
  
  
  @GetMapping(value = "/purchaseTransactions/{transactionId}")
  public PurchaseTransaction getTransactionById(@PathVariable String transactionId) {
    UserProduct userProduct = userProductRepository.findById(UUID.fromString(transactionId)).orElse(null);
    PurchaseTransaction transaction = null;
    if (userProduct!= null) {
      transaction = new PurchaseTransaction();
      transaction.setBrandId(userProduct.getProduct().getBrandId().getBrandId().toString());
      transaction.setSku(userProduct.getProduct().getSku());
      transaction.setCurrencyCode(userProduct.getCurrency().getCurrencyCode());
      transaction.setPurchasePrice(userProduct.getPurchasePrice().toString());
      transaction.setPurchaseFrom(userProduct.getPurchaseFrom());
      if (!StringUtils.isEmpty(userProduct.getPurchaseDate())) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        transaction.setPurchaseDate(sf.format(userProduct.getPurchaseDate()));
      }
      transaction.setNote(userProduct.getNote());
    }
    return transaction;
  }  
  
}
