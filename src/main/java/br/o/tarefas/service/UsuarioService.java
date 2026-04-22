package br.o.tarefas.service;

import br.o.tarefas.dto.ConvidadoDTO;
import br.o.tarefas.dto.TarefaDTO;
import br.o.tarefas.dto.UsuarioDTO;
import br.o.tarefas.entidade.Convidado;
import br.o.tarefas.entidade.Usuario;
import br.o.tarefas.exceptions.RestExceptions;
import br.o.tarefas.repository.ConvidadoRepository;
import br.o.tarefas.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConvidadoRepository convidadoRepository;

    @Autowired
    private ModelMapper modelMapper;



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
}
