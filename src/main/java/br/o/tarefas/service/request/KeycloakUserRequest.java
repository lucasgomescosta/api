package br.o.tarefas.service.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUserRequest {
    private String userName;
    public boolean enabled;
    public boolean emailVerified;
    public String email;
    public String firstName;
    public String lastName;
    public List<String> requiredActions;
}
