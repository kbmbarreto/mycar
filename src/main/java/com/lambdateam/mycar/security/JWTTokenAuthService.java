package com.lambdateam.mycar.security;

import com.lambdateam.mycar.ApplicationContextLoad;
import com.lambdateam.mycar.model.UserModel;
import com.lambdateam.mycar.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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
    public void addAuthentication(HttpServletResponse response, String username) throws Exception {

        /** Montagem do token **/
        String JWT = Jwts.builder() /** Chama o gerador de token **/
                .setSubject(username) /** Adiciona o usuário **/
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /** Tempo de expiração **/
                .signWith(SignatureAlgorithm.HS512, SECRET).compact(); /** Compactação e algoritmos de geração de senha **/

                String token = TOKEN_PREFIX + " " + JWT; /** Junta o token com o prefixo **/

                /** Adiciona o cabeçalho Http **/
                response.addHeader(HEADER_STRING, token);

                /** Escreve token como resposta no corpo Http **/
                response.getWriter().write("{\"Authorization\": \""+token+"\"}");
    }

    /** Retorna o usuário validado com token ou, caso não seja válido, retorna null **/
    public Authentication getAuthentication(HttpServletRequest request) {

        /** Pega o token enviado no cabeçalho http **/
        String token = request.getHeader(HEADER_STRING);

        if(token != null) {
            /** Faz a validação do token do usuário na requisição **/
            String user = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody().getSubject();

            if (user != null) {
                UserModel userModel = ApplicationContextLoad.getApplicationContext()
                        .getBean(UserRepository.class).findUserByLogin(user);

                /** Retorna o usuário logado **/
                if (userModel != null) {
                    return new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword(), userModel.getAuthorities());
                }
            }
        }

        return null; /** Não autorizado **/
    }
}
