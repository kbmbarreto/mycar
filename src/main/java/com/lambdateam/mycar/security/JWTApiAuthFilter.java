package com.lambdateam.mycar.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Sétimo PASSO PARA IMPLANTAR O SPRING SECURITY -> Criar as configurações abaixo
 *
 * <p>Filtro onde todas as requisições serão capturadas para autenticar
 */
public class JWTApiAuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        /**
         * Estabelece a autenticação para a requisição
         */
        Authentication authentication = new JWTTokenAuthService().getAuthentication((HttpServletRequest) servletRequest);

        /**
         * Coloca o processo de autenticação no Spring Security
         */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        /**
         * Continua o processo
         */
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
