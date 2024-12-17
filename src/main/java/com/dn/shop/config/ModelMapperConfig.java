package com.dn.shop.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.config.Configuration.AccessLevel;
import com.dn.shop.model.entity.User;
import com.dn.shop.model.dto.user.UserDTO;
import com.dn.shop.model.entity.Order;
import com.dn.shop.model.dto.order.OrderDTO;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // Configure global settings
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(AccessLevel.PRIVATE);
        
        // User to UserDTO mapping
        modelMapper.createTypeMap(User.class, UserDTO.class)
            .addMappings(mapper -> {
                mapper.map(User::getFirstName, UserDTO::setName);
                mapper.map(User::getEmail, UserDTO::setEmail);
                mapper.map(User::getId, UserDTO::setId);
            });
            
        // Order to OrderDTO mapping
        modelMapper.createTypeMap(Order.class, OrderDTO.class)
            .addMappings(mapper -> {
                mapper.map(Order::getId, OrderDTO::setId);
                mapper.map(Order::getTotalPrice, OrderDTO::setTotalPrice);
                mapper.map(Order::getStatus, OrderDTO::setStatus);
                mapper.map(Order::getCreatedAt, OrderDTO::setCreatedAt);
            });
            
        return modelMapper;
    }
} 