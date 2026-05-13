package br.o.tarefas.exceptions;

import br.o.tarefas.dto.ErrorResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setTimestamp(LocalDateTime.now());
        responseDTO.setMessage("Você não tem permissão para acessar este recurso.");
        responseDTO.setError("Forbidden");
        responseDTO.setStatus(HttpServletResponse.SC_FORBIDDEN);
        responseDTO.setPath(request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDTO));
    }
}
