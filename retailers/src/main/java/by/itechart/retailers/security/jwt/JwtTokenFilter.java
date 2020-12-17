package by.itechart.retailers.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public static void responseEntityToHttpServletResponse(ResponseEntity<Object> responseEntity, HttpServletResponse response)
            throws IOException {
        for (Map.Entry<String, List<String>> header : responseEntity.getHeaders()
                                                                    .entrySet()) {
            String chave = header.getKey();
            for (String valor : header.getValue()) {
                response.addHeader(chave, valor);
            }
        }
        response.setStatus(responseEntity.getStatusCodeValue());
        response.getWriter()
                .write(String.valueOf(responseEntity.getBody()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext()
                                         .setAuthentication(auth);
                }
            }
        } catch (JwtAuthenticationException e) {
            logger.error(e.getMessage());
            responseEntityToHttpServletResponse(new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED), response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}

