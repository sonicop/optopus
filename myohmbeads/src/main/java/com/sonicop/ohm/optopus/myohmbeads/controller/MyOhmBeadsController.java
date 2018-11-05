package com.sonicop.ohm.optopus.myohmbeads.controller;

import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;
import com.sonicop.ohm.optopus.myohmbeads.config.AwsConfig;
import com.sonicop.ohm.optopus.myohmbeads.dto.ProductInfo;
import com.sonicop.ohm.optopus.myohmbeads.dto.PurchaseTransaction;
import com.sonicop.ohm.optopus.myohmbeads.model.Currency;
import com.sonicop.ohm.optopus.myohmbeads.model.Product;
import com.sonicop.ohm.optopus.myohmbeads.model.User;
import com.sonicop.ohm.optopus.myohmbeads.model.UserProduct;
import com.sonicop.ohm.optopus.myohmbeads.repository.ProductRepository;
import com.sonicop.ohm.optopus.myohmbeads.repository.UserProductRepository;
import com.sonicop.ohm.optopus.myohmbeads.repository.UserRepository;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
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
  private Environment environment;
  
	@Autowired
	private ProductRepository productRepository;
  
	@Autowired
	private UserProductRepository userProductRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  @Value("${testUserName}")
  private String testUserName;
  
  @Autowired
  AwsConfig awsConfig;
  
  
  @GetMapping(value = "/products/search/getByKeywords")
  public List<Map<String,String>> getProductsByKeyword(@RequestParam("keywords") String keywords) {
    String regexKeyWords = keywords.trim().replaceAll(" +", "|");
    List<Product> productList = productRepository.findByKeyword(regexKeyWords);
    List<Map<String,String>> result = new ArrayList(productList.size());
    for (Product product: productList) {
      Map<String, String> option = new HashMap();
      option.put("sku", product.getSku());
      option.put("name", product.getName());
      option.put("brandName", product.getBrandId().getName());
      option.put("tags", product.getTags());
      option.put("valueProperty", product.getBrandId().getName()  + " - " + product.getName() + " (" + product.getSku() + ")");
      option.put("textProperty", product.getBrandId().getName()  + " - " + product.getName() + " (" + product.getSku() + ")" + ((product.getTags() != null)? " " + product.getTags() :""));
//      if (product.getImageList() != null && product.getImageList().size() > 0) {
//        option.put("image", product.getImageList().get(0).getReference());
//      }
      result.add(option);
    }
    return result;
  }
  
  
  
	@GetMapping(value = "/productInfo/{sku}")
	public ProductInfo getProductInfo(@PathVariable String sku) {
    Product product = productRepository.findById(sku).orElse(null);
    ProductInfo productInfo = new ProductInfo();
    if (product != null) {
      productInfo.setSku(product.getSku());
      productInfo.setName(product.getName());
      if (product.getImageList() != null && product.getImageList().size() > 0) {
        productInfo.setImageReference(product.getImageList().get(0).getReference());
      }
    }
    return productInfo;
  }
  

  
	@PostMapping(value = "/purchaseTransactions")
	public ResponseEntity saveTransaction(@Valid @RequestBody PurchaseTransaction transaction, Principal principal) {
    UUID userId = getUser(principal).getUserId();
    UserProduct userProduct = new UserProduct();
    userProduct.setProduct(new Product(transaction.getSku()));
    userProduct.setUser(new User(userId));
    userProduct.setSerialNumber(transaction.getSerialNumber());
    if (!StringUtils.isEmpty(transaction.getCurrencyCode())) {
      userProduct.setCurrency(new Currency(transaction.getCurrencyCode()));
    }
    if (!StringUtils.isEmpty(transaction.getPurchasePrice())) {
      userProduct.setPurchasePrice(new BigDecimal(transaction.getPurchasePrice()));
    }
    if (!StringUtils.isEmpty(transaction.getPurchaseFrom())) {
      userProduct.setPurchaseFrom(transaction.getPurchaseFrom());
    }
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
    userProduct.setCreatedBy(userId);
    userProductRepository.save(userProduct);
		return new ResponseEntity(transaction, HttpStatus.OK);
	}
  
  
  
  @RequestMapping(value = "/purchaseTransactions/{transactionId}", method = RequestMethod.PUT)
	public ResponseEntity updateTransaction(@Valid @RequestBody PurchaseTransaction transaction, @PathVariable String transactionId, Principal principal) {
    UUID userId = getUser(principal).getUserId();
    UserProduct userProduct = userProductRepository.findById(UUID.fromString(transactionId)).orElse(null);
    userProduct.setSerialNumber(transaction.getSerialNumber());
    if (!StringUtils.isEmpty(transaction.getCurrencyCode())) {
      userProduct.setCurrency(new Currency(transaction.getCurrencyCode()));
    }
    if (!StringUtils.isEmpty(transaction.getPurchasePrice())) {
      userProduct.setPurchasePrice(new BigDecimal(transaction.getPurchasePrice()));
    }
    if (!StringUtils.isEmpty(transaction.getPurchaseFrom())) {
      userProduct.setPurchaseFrom(transaction.getPurchaseFrom());
    }
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
    userProduct.setUpdatedBy(userId);
    userProductRepository.save(userProduct);
		return new ResponseEntity(transaction, HttpStatus.OK);
	}
  
  
  
  @RequestMapping(value = "/purchaseTransactions/{transactionId}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteTransaction(@PathVariable String transactionId, Principal principal) {
    UUID userId = getUser(principal).getUserId();
    UserProduct userProduct = userProductRepository.findById(UUID.fromString(transactionId)).orElse(null);
    if (userProduct != null) {
      userProduct.setDeletedTime(new Date());
      userProduct.setDeletedBy(userId);
      userProductRepository.save(userProduct);
    }
	}



  @GetMapping(value = "/purchaseTransactions")
  public List<PurchaseTransaction> getTransactionsByUserId(Principal principal) {
    UUID userId = getUser(principal).getUserId();
    List<UserProduct> userProductList = userProductRepository.findAllByUserUserIdAndDeletedTimeIsNullOrderByCreateTimeDesc(userId);
    List<PurchaseTransaction> resultList  = null;
    if (userProductList != null && !userProductList.isEmpty()) {
      resultList = new ArrayList(userProductList.size());
      for (UserProduct userProduct: userProductList) {
        PurchaseTransaction transaction = new PurchaseTransaction();
        transaction.setTransactionId(userProduct.getTransactionId().toString());
        transaction.setSku(userProduct.getProduct().getSku());
        transaction.setProductName(userProduct.getProduct().getName());
        transaction.setBrandName(userProduct.getProduct().getBrandId().getName());
        transaction.setCreateTime(userProduct.getCreateTime());
        transaction.setSerialNumber(userProduct.getSerialNumber());
        if (userProduct.getCurrency() != null) {
          transaction.setCurrencyCode(userProduct.getCurrency().getCurrencyCode());
        }
        if (userProduct.getPurchasePrice() !=null) {
          transaction.setPurchasePrice(userProduct.getPurchasePrice().toString());
        }
        transaction.setPurchaseFrom(userProduct.getPurchaseFrom());
        if (userProduct.getPurchaseDate() != null) {
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
      String textProperty = userProduct.getProduct().getBrandId().getName()  + 
                            " - " + userProduct.getProduct().getName() + 
                             " (" + userProduct.getProduct().getSku() + ")";
      transaction = new PurchaseTransaction();
      transaction.setSku(textProperty);
      transaction.setSerialNumber(userProduct.getSerialNumber());
      if (userProduct.getCurrency() != null) {
        transaction.setCurrencyCode(userProduct.getCurrency().getCurrencyCode());
      }
      if (userProduct.getPurchasePrice() != null) {
        transaction.setPurchasePrice(userProduct.getPurchasePrice().toString());
      }
      transaction.setPurchaseFrom(userProduct.getPurchaseFrom());
      if (userProduct.getPurchaseDate() != null) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        transaction.setPurchaseDate(sf.format(userProduct.getPurchaseDate()));
      }
      transaction.setNote(userProduct.getNote());
    }
    return transaction;
  }
  
  
  
  @GetMapping(value = "/awsParameters")
  public Map<String, Object> getAwsParameters(Principal principal) {
    String username = getUser(principal).getUsername();
    GetSessionTokenRequest sessionTokenRequest = new GetSessionTokenRequest();
    sessionTokenRequest.setDurationSeconds(60000);
    GetSessionTokenResult sessionTokenResult = awsConfig.getStsClient().getSessionToken(sessionTokenRequest);
    Credentials sessionCreds = sessionTokenResult.getCredentials();
    Map<String, Object> params = new HashMap();
    params.put("accessKeyId", sessionCreds.getAccessKeyId());
    params.put("secretAccessKey", sessionCreds.getSecretAccessKey());
    params.put("sessionToken", sessionCreds.getSessionToken());
    params.put("region", awsConfig.getRegion());
    params.put("s3bucket", awsConfig.getS3Bucket());
    params.put("username", username);
    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
    params.put("creationTime", sf.format(new Date()));
    return params;
  }  

  
  
  private User getUser(Principal principal) {
    String userName = null;
    if (environment.getActiveProfiles() != null && Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
      userName = testUserName;
    } else {
      userName = principal.getName();
    }
    User currentUser = userRepository.findOneByUsername(userName);
    return currentUser;    
  }  
}
