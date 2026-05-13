package br.o.tarefas.service;

import br.o.tarefas.dto.ConvidadoDTO;
import br.o.tarefas.dto.TarefaDTO;
import br.o.tarefas.dto.UsuarioDTO;
import br.o.tarefas.entidade.Convidado;
import br.o.tarefas.entidade.ConvidadoPendente;
import br.o.tarefas.entidade.Usuario;
import br.o.tarefas.exceptions.RestExceptions;
import br.o.tarefas.repository.ConvidadoPendenteRepository;
import br.o.tarefas.repository.ConvidadoRepository;
import br.o.tarefas.repository.UsuarioRepository;
import br.o.tarefas.service.request.KeycloakUserRequest;
import br.o.tarefas.service.request.ResetPasswordRequest;
import br.o.tarefas.util.PasswordGenerateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final ConvidadoRepository convidadoRepository;

    private final ModelMapper modelMapper;

    private final KeycloakUserClientService keycloakUserClientService;

    private final ConvidadoPendenteRepository convidadoPendenteRepository;



    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return modelMapper.map(usuarioRepository.save(usuario), UsuarioDTO.class);
    }

    @Transactional
    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario user = usuarioRepository.findById(id).orElseThrow(() -> new RestExceptions.ResourceNotFoundException("Usuário não encontrado"));

        user.setNome(usuarioDTO.getNome());
        user.setEmail(usuarioDTO.getEmail());
        user.setTelefone(usuarioDTO.getTelefone());

        return modelMapper.map(usuarioRepository.save(user), UsuarioDTO.class);
    }

    public Mono<Integer> keycloackCriarNovoUsuario(ConvidadoPendente usuarioConvidadoPendente) {
        KeycloakUserRequest keycloakUserRequest = getKeycloakUserRequest(usuarioConvidadoPendente);
        String senhaAleatoria = PasswordGenerateUtil.generate(10);
        System.out.println("Senha: " + senhaAleatoria);

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest("password", senhaAleatoria, true);

        return keycloakUserClientService.createUser(keycloakUserRequest)
                .flatMap(userId -> keycloakUserClientService.resetPassword(userId, resetPasswordRequest)
                        .then(Mono.fromCallable(() ->
                                convidadoPendenteRepository.updateKeycloakIdByEmail(userId, usuarioConvidadoPendente.getConvidadoEmail())
                        ))
                );
    }

    private KeycloakUserRequest getKeycloakUserRequest(ConvidadoPendente usuarioConvidadoPendente) {
        KeycloakUserRequest keycloakUserRequest = new KeycloakUserRequest();
        keycloakUserRequest.setUserName(usuarioConvidadoPendente.getConvidadoEmail());
        keycloakUserRequest.setEmail(usuarioConvidadoPendente.getConvidadoEmail());
        keycloakUserRequest.setEmailVerified(true);
        keycloakUserRequest.setEnabled(true);
        keycloakUserRequest.setFirstName(usuarioConvidadoPendente.getConvidadoNome().split(" ")[0]);
        keycloakUserRequest.setLastName(usuarioConvidadoPendente.getConvidadoNome().split(" ")[1]);
        keycloakUserRequest.setRequiredActions(List.of("UPDATE_PASSWORD"));
        return keycloakUserRequest;
    }

    public List<ConvidadoPendente> buscaUsuariosPendentesNoKeycloak(List<ConvidadoPendente> convidadoPendentes) {
        return convidadoPendentes.stream()
                .flatMap(convidado -> convidadoPendenteRepository.findConvidadosPendentesKeycloak(convidado.getConvidadoEmail()).stream())
                .collect(Collectors.toList());
    }

    public List<Usuario> validaConvidadoExistente(List<ConvidadoDTO> convidados) {
        List<Usuario> usuarios = convidados.stream()
                .map(ConvidadoDTO::getEmail)
                .map(usuarioRepository::findByEmail)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        if (usuarios.size() != convidados.size()) {
            throw new RestExceptions.ResourceNotFoundException("Um ou mais convidados não foram encontrados");
        }

        return usuarios;
    }


    public List<TarefaDTO> buscaTarefasConvidado(Long usuarioId) {
        List<Convidado> convites = convidadoRepository.findByUsuarioId(usuarioId);

        return convites.stream()
                .map(convidado -> modelMapper.map(convidado.getTarefa(), TarefaDTO.class))
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> recuperarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }
}
