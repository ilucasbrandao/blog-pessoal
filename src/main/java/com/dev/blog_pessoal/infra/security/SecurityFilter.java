package com.dev.blog_pessoal.infra.security;

import com.dev.blog_pessoal.model.User;
import com.dev.blog_pessoal.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Filtro personalizado que estende OncePerRequestFilter.
 * Isso garante que ele rode exatamente UMA vez para cada requisição HTTP que chegar à API.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    // Dependências necessárias para validar o token e buscar o usuário no banco
    TokenService tokenService;
    UserRepository userRepository;

    // Construtor para injeção manual de dependências (geralmente chamado na classe SecurityConfiguration)
    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    /**
     * O 'coração' do filtro. Toda requisição passa por este método de forma obrigatória.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Tenta extrair o token puro que veio no cabeçalho (Header) da requisição
        var token = this.recoverToken(request);

        // 2. Valida o token. Se for válido, retorna o e-mail (subject); se for inválido, retorna null
        var login = tokenService.validateToken(token);

        // 3. Se o token for válido (login não for null), o usuário será autenticado no contexto do Spring
        if (login != null) {
            // Busca os dados completos do usuário no banco de dados através do e-mail extraído do token
            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User not found"));

            // Define o nível de acesso do usuário (Role). Aqui está fixado como "ROLE_USER" temporariamente
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

            // Cria o objeto de autenticação oficial do Spring Security contendo o usuário, credenciais (null) e permissões
            var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

            // Salva essa autenticação no Contexto do Spring. A partir desta linha, o Spring sabe QUEM está logado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 4. ESSENCIAL: Diz para a aplicação continuar o fluxo e ir para o próximo filtro ou para o Controller
        filterChain.doFilter(request, response);
    }

    /**
     * Método auxiliar para ler o cabeçalho 'Authorization' e extrair apenas a String do token.
     */
    private String recoverToken(HttpServletRequest request){
        // Busca a linha "Authorization" dentro do cabeçalho da requisição HTTP
        var authHeader = request.getHeader("Authorization");

        // Se o cabeçalho não existir (usuário não mandou token), retorna null
        if(authHeader == null) return null;

        // Remove a palavra "Bearer " (com o espaço) para isolar o código JWT puro
        return authHeader.replace("Bearer ", "");
    }
}
