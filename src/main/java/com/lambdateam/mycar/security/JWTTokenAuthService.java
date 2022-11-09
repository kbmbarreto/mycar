package com.lambdateam.mycar.security;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/** QUINTO PASSO PARA IMPLANTAR O SPRING SECURITY -> Criar as configurações abaixo **/

@Service
@Component
public class JWTTokenAuthService {

    /** Token validation time **/
    private static final long EXPIRATION_TIME = 172800000;

    /** Uma senha unica para compor a autenticação **/
    private static final String SECRET = "Hard_Password_W#9rrmenvZAIR5c5i33W!VLOu";

    /** Prefixo padrão de token **/
    private static final String TOKEN_PREFIX = "Bearer";

    private static final String HEADER_STRING = "Authorization";

    /** Gerando token de autenticação e adicionando o cabeçalho em resposta Http **/
    public void addAuthentication(HttpServletResponse response, String username) {

    }
}
