package br.o.tarefas.entidade;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tarefa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tarefa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @Column
    private String local;
    
    @Column(name = "data_hora")
    private String dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criadorId")
    private Usuario criador;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Convidado> convidados;
}
