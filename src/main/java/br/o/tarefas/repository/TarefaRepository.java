package br.o.tarefas.repository;

import br.o.tarefas.entidade.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    // Buscar tarefas por título
    List<Tarefa> findByTituloContainingIgnoreCase(String titulo);
    
    // Buscar tarefas por local
    List<Tarefa> findByLocalContainingIgnoreCase(String local);
}
