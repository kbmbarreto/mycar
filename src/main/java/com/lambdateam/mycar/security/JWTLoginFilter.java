package com.lambdateam.mycar.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdateam.mycar.model.UserModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** SEXTO PASSO PARA IMPLANTAR O SPRING SECURITY -> Criar as configurações abaixo **/

/** Estabelece nosso gerenciador de token **/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    protected JWTLoginFilter(String url, AuthenticationManager authenticationManager) {

        /** Obrigamos a autenticar a URL **/
        super(new AntPathRequestMatcher(url));

        /** Gerenciador de autenticação **/
        setAuthenticationManager(authenticationManager);
    }

    /** Retorna o usuário ao processar a autenticação **/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        /** Recebendo o token para validar **/
        UserModel userModel = new ObjectMapper()
                .readValue(request.getInputStream(), UserModel.class);

        /** Retorna o usuário: login, senha e acessos **/
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        new JWTTokenAuthService().addAuthentication(response, authResult.getName());
    }
}
