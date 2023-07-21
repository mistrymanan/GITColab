package com.gitcolab.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gitcolab.dto.UserInfo;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.UserRepository;


@Service
public class ExploreService {

    private ModelMapper mapper = null;
    
    @Autowired
    private UserRepository userRepository;

    public ExploreService() {
    }

    @Autowired
    public ExploreService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = new ModelMapper();
    }

    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        
        // Mapper for UserInfo
        // TypeMap <User, UserInfo> typeMap = this.mapper.createTypeMap(User.class, UserInfo.class);
        // if (typeMap == null) {
        //     this.mapper.createTypeMap(User.class, UserInfo.class);
        // }
        // UserInfo userInfo = this.mapper.map(user, UserInfo.class);
        return this.mapper.map(user,UserInfo.class);
    }

    public List<UserInfo> getConnections(String username) {
        List<UserInfo> connections = new ArrayList<UserInfo>();

        for (int i = 0; i < 3; i++) {
            User user = userRepository.findById(Long.parseLong(username) + i).get();        
            connections.add(this.mapper.map(user,UserInfo.class));
        }
        return connections;
    }
}
