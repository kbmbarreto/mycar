package com.lambdateam.mycar.security;

import com.lambdateam.mycar.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
/** Mapeia URL, endereços, autoriza ou bloqueia acessos a URL **/
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    /** Configura as solicitações de acesso por http **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /** Ativando a proteção contra usuários que não estão validados por token **/
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        /** Ativando a permissão para a pagina inicial do sistema **/
                .disable().authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                /** URL de Logout - Redireciona após o user deslogar do sistema **/
                .anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
                /** Mapeia URL de Logout e invalida o usuário **/
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

                /** Filtra reuisições de login para autenticação **/

                /** Filtra demais requisições para verificar a presença do token JWT no header http **/

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /** Service que irá consultar o usuário no banco de dados **/
        auth.userDetailsService(userDetailsService)
                /** Padrão de codificação **/
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
