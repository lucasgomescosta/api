package br.o.tarefas.repository;

import br.o.tarefas.entidade.Convidado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConvidadoRepository extends JpaRepository<Convidado, Long> {
    List<Convidado> findByUsuarioId(Long usuarioId);
}
