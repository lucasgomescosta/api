package br.o.tarefas.entidade;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "convidados")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Convidado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefaId")
    private Tarefa tarefa;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;
}
