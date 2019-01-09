package com.sonicop.ohm.optopus.myohmbeads.config;

import com.sonicop.ohm.optopus.myohmbeads.model.User;
import com.sonicop.ohm.optopus.myohmbeads.repository.UserRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {
 
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public String execute(Connection<?> connection) {
        User user = new User();
        user.setCreateTime(new Date());
        user.setRoleType("USER");
        if ("facebook".equalsIgnoreCase(connection.getKey().getProviderId())) {
          Facebook facebook = (Facebook) connection.getApi();
          String [] fields = {"id","email","name"};
          FacebookUser fbUser = facebook.fetchObject("me", FacebookUser.class, fields);
          user.setEmail(fbUser.getEmail());
          user.setUserType(2);  
        } else {
          user.setUserType(1);
        }
        //user.setUsername(connection.getKey().getProviderUserId());
        //user.setPassword("ABC");
        user.setUsername(connection.getDisplayName());
        user.setPassword(StringUtils.randomAlphanumeric(8));

        
        
        userRepository.save(user);
        return user.getUserId().toString();
    }

}