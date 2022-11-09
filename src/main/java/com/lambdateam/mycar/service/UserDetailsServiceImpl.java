package com.lambdateam.mycar.service;

import com.lambdateam.mycar.model.UserModel;
import com.lambdateam.mycar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** TERCEIRO PASSO PARA IMPLANTAR O SPRING SECURITY -> Criar o serviço para carregar o usuário **/

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel userModel = userRepository.findUserByLogin(username);

        if(userModel == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        return new User(userModel.getUserName(), userModel.getPassword(), userModel.getAuthorities());
    }
}
