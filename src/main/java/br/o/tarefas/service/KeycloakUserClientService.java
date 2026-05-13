package br.o.tarefas.service;

import br.o.tarefas.service.request.KeycloakUserRequest;
import br.o.tarefas.service.request.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class KeycloakUserClientService {
    private final WebClient webClient;
    private final KeycloackAuthService authService;

    @Value("${spring.keycloak.endpoint}")
    private String endpoint;

    public KeycloakUserClientService() {
        this.webClient = WebClient
                .builder()
                .baseUrl(endpoint)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.authService = new KeycloackAuthService(endpoint);
    }

    public Mono<String> createUser(KeycloakUserRequest keycloakUserRequest) {
        return authService.obterAccessTokenAdmin()
                .flatMap(token -> webClient
                        .post()
                        .uri("/admin/realms/agenda-tarefas/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .bodyValue(keycloakUserRequest)
                        .retrieve()
                        .toBodilessEntity()
                        .map(response -> {
                            URI location = response.getHeaders().getLocation();
                            if (location != null) {
                                String path = location.getPath();
                                return path.substring(path.lastIndexOf('/') + 1);
                            } else {
                                throw new RuntimeException("Header 'location' não foi encontrado na resposta do Keycloak");
                            }
                        }));
    }

    public  Mono<Void> resetPassword(String userId, ResetPasswordRequest request) {
        return authService.obterAccessTokenAdmin()
                .flatMap(token -> webClient
                        .put()
                        .uri("/admin/realms/agenda-tarefas/users/{id}/reset-password", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .bodyValue(request)
                        .retrieve()
                        .toBodilessEntity()
                        .then());
    }

}
