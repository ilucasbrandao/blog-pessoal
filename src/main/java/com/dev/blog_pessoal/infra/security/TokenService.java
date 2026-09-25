package com.dev.blog_pessoal.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dev.blog_pessoal.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service // Registra a classe como um serviço gerenciado pelo Spring (Injeção de Dependência pronta)
public class TokenService {

    // Injeta a senha secreta definida no arquivo 'application.properties' ou 'application.yml'
    // Essa senha garante que ninguém consiga forjar ou alterar um token gerado pela sua API
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Cria e assina um novo Token JWT quando o usuário faz login com sucesso.
     */
    public String generateToken(User user) {
        try {
            // Define o algoritmo de criptografia passando a nossa palavra secreta (HMAC256)
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Constrói o esqueleto do Token JWT passo a passo
            String token = JWT.create()
                    .withIssuer("blog-pessoal") // Identifica quem gerou o token (sua API)
                    .withSubject(user.getEmail()) // Guarda a informação principal do usuário (neste caso, o e-mail)
                    .withExpiresAt(this.generateExpirationDate()) // Define o momento exato em que o token deixará de funcionar
                    .sign(algorithm); // Assina o token de forma digital usando o algoritmo e o secret

            return token; // Retorna a String criptografada pronta (Ex: "eyJhbGciOi...")

        } catch (JWTCreationException exception) {
            // Caso ocorra algum erro interno da biblioteca do Auth0 ao gerar o token
            throw new RuntimeException(exception);
        }
    }

    /**
     * Verifica se o token recebido da requisição é autêntico, está dentro da validade e foi emitido por esta API.
     */
    public String validateToken(String token) {
        try {
            // Prepara o mesmo algoritmo de criptografia com o mesmo segredo
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Executa o processo de descriptografia e validação estrutural do token
            return JWT.require(algorithm)
                    .withIssuer("blog-pessoal") // Valida se o emissor gravado no token é realmente "blog-pessoal"
                    .build() // Constrói a configuração da validação
                    .verify(token) // Abre o token e roda todos os testes (Validade, assinatura, etc.)
                    .getSubject(); // Se passou no teste, extrai e retorna o e-mail (subject) guardado lá dentro

        } catch (JWTVerificationException ex) {
            // Se o token estiver expirado, adulterado ou for inválido, cai aqui.
            // Retorna null para sinalizar ao SecurityFilter que a validação falhou silenciosamente
            return null;
        }
    }

    /**
     * Método auxiliar para calcular o tempo de vida do token.
     * Retorna um objeto Instant representando o fuso horário correto do Brasil.
     */
    public Instant generateExpirationDate(){
        // Pega o horário atual do servidor, adiciona 2 horas de vida útil, e ajusta para o fuso GMT-3 (Horário de Brasília)
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-3"));
    }
}
