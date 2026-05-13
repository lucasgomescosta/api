package br.o.tarefas.service;

import br.o.tarefas.dto.TarefaDTO;
import br.o.tarefas.entidade.Convidado;
import br.o.tarefas.entidade.ConvidadoPendente;
import br.o.tarefas.entidade.Tarefa;
import br.o.tarefas.entidade.Usuario;
import br.o.tarefas.exceptions.RestExceptions;
import br.o.tarefas.repository.TarefaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Criar uma nova tarefa
     * @param tarefaDTO dados da tarefa a ser criada
     * @return tarefa criada convertida para DTO
     */
    public TarefaDTO create(TarefaDTO tarefaDTO) {

        Tarefa tarefa = modelMapper.map(tarefaDTO, Tarefa.class);

        TarefaDTO tarefaDto = atribuirConvidadosValidos(tarefaDTO, tarefa);

        if (!tarefaDto.getConvidadosPendentes().isEmpty()) {
            List<Mono<Integer>> usuariosMono = tarefa.getConvidadoPendente().stream()
                    .filter(c -> c.getKeycloackId() == null || c.getKeycloackId().isBlank())
                    .map(usuarioService::keycloackCriarNovoUsuario)
                    .toList();

            Integer usuariosCriados = Flux.merge(usuariosMono)
                    .reduce(0, Integer::sum)
                    .block();

            System.out.println("Total de usuários criados no Keycloak: " + usuariosCriados);

        }

        return tarefaDto;
    }

    private TarefaDTO atribuirConvidadosValidos(TarefaDTO tarefaDTO, Tarefa tarefa) {
        List<Usuario> usuariosConvidados = usuarioService.validaConvidadoExistente(tarefaDTO.getConvidados());
        List<Convidado> convidados = usuariosConvidados.stream()
                        .map(usuario -> new Convidado(tarefa, usuario))
                .collect(Collectors.toList());
        tarefa.setConvidados(convidados);
        atribuiConvidadosPendentes(tarefaDTO, tarefa);

        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        return modelMapper.map(tarefaSalva, TarefaDTO.class);
    }

    private void atribuiConvidadosPendentes(TarefaDTO tarefaDTO,  Tarefa tarefaEntity) {
        List<String> emailsConvidadosExistentes = tarefaEntity.getConvidados().stream()
                .map(convidado -> convidado.getUsuario().getEmail())
                .toList();


        List<ConvidadoPendente> convidadoPendentes = tarefaDTO.getConvidados().stream()
                .filter(dto -> !emailsConvidadosExistentes.contains(dto.getEmail()))
                .map(dto -> new ConvidadoPendente(tarefaEntity, dto.getNome(), dto.getEmail()))
                .toList();

        List<ConvidadoPendente> usuarioKeycloakPendente = usuarioService.buscaUsuariosPendentesNoKeycloak(convidadoPendentes);

        Map<String, ConvidadoPendente> mapUsuarioKeycloak = usuarioKeycloakPendente.stream()
                        .collect(Collectors.toMap(ConvidadoPendente::getConvidadoEmail, Function.identity()));

        convidadoPendentes.forEach(convidado -> {
            ConvidadoPendente keycloakPendente = mapUsuarioKeycloak.get(convidado.getConvidadoEmail());
            if (keycloakPendente != null) {
                convidado.setKeycloackId(keycloakPendente.getKeycloackId());
            }
        });

        tarefaEntity.setConvidadoPendente(convidadoPendentes);
    }

    /**
     * Obter tarefa por ID
     * @param id identificador da tarefa
     * @return tarefa encontrada convertida para DTO
     * @throws RestExceptions.ResourceNotFoundException se tarefa não existir
     */
    public TarefaDTO getById(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RestExceptions.ResourceNotFoundException("Tarefa", id));

        return modelMapper.map(tarefa, TarefaDTO.class);
    }

    /**
     * Obter todas as tarefas
     * @return lista de todas as tarefas em DTO
     */
    public List<TarefaDTO> getAll() {
        try {
            return tarefaRepository.findAll()
                    .stream()
                    .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RestExceptions.ResourceTemporarilyUnavailableException("Lista de tarefas", "Erro interno do banco de dados");
        }
    }

    /**
     * Buscar tarefas por título
     * @param titulo título da tarefa a buscar
     * @return lista de tarefas encontradas convertidas para DTO
     */
    public List<TarefaDTO> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RestExceptions.InvalidDataException("título", "não pode estar vazio");
        }

        try {
            return tarefaRepository.findByTituloContainingIgnoreCase(titulo)
                    .stream()
                    .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RestExceptions.ResourceTemporarilyUnavailableException("Busca por título", "Erro interno do banco de dados");
        }
    }

    /**
     * Buscar tarefas por local
     * @param local local da tarefa a buscar
     * @return lista de tarefas encontradas convertidas para DTO
     */
    public List<TarefaDTO> buscarPorLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            throw new RestExceptions.InvalidDataException("local", "não pode estar vazio");
        }

        try {
            return tarefaRepository.findByLocalContainingIgnoreCase(local)
                    .stream()
                    .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RestExceptions.ResourceTemporarilyUnavailableException("Busca por local", "Erro interno do banco de dados");
        }
    }

    /**
     * Atualizar tarefa existente
     * @param id identificador da tarefa
     * @param tarefaDTO dados atualizados da tarefa
     * @return tarefa atualizada convertida para DTO
     * @throws RestExceptions.ResourceNotFoundException se tarefa não existir
     */
    public TarefaDTO update(Long id, TarefaDTO tarefaDTO) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new RestExceptions.ResourceNotFoundException("Tarefa", id));


            // Atualizar apenas os campos fornecidos
            if (tarefaDTO.getTitulo() != null) {
                tarefaExistente.setTitulo(tarefaDTO.getTitulo());
            }
            if (tarefaDTO.getDescricao() != null) {
                tarefaExistente.setDescricao(tarefaDTO.getDescricao());
            }
            if (tarefaDTO.getLocal() != null) {
                tarefaExistente.setLocal(tarefaDTO.getLocal());
            }
            if (tarefaDTO.getDataHora() != null) {
                tarefaExistente.setDataHora(tarefaDTO.getDataHora());
            }
        return atribuirConvidadosValidos(tarefaDTO, tarefaExistente);

    }

    /**
     * Deletar tarefa por ID
     * @param id identificador da tarefa
     * @throws RestExceptions.ResourceNotFoundException se tarefa não existir
     */
    public void delete(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RestExceptions.ResourceNotFoundException("Tarefa", id));

        try {
            tarefaRepository.delete(tarefa);
        } catch (Exception e) {
            throw new RestExceptions.OperationNotAllowedException("deletar tarefa", "Erro interno do banco de dados");
        }
    }
}
