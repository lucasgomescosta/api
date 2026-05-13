package br.o.tarefas.service;


import br.o.tarefas.service.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class KeycloackAuthService {

    private final WebClient webClient;

    public KeycloackAuthService(String keycloackEndpoint) {
        this.webClient = WebClient.builder()
                .baseUrl(keycloackEndpoint)
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }

    public Mono<String> obterAccessTokenAdmin() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("client_id", "admin-cli");
        headers.add("username", "admin");
        headers.add("password", "admin");
        headers.add("grant_type", "password");

        return  webClient
                .post()
                .uri("/realms/master/protocol/openid-connect/token")
                .bodyValue(headers)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(TokenResponse::getAccessToken);
    }
}
