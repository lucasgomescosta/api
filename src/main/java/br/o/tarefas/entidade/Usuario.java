package br.o.tarefas.entidade;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String nome;
    
    @Column
    private String email;
    
    @Column
    private String telefone;

    @OneToMany(mappedBy = "criador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tarefa> tarefasCriadas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Convidado> convites;
}
