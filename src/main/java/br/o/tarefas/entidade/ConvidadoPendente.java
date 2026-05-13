package br.o.tarefas.entidade;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity(name = "convidado_pendente")
@Table(name = "convidado_pendente")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ConvidadoPendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @JoinColumn(name = "tarefa_id")
    @ManyToOne
    private Tarefa tarefa;

    @NonNull
    @Column(name = "convidado_nome")
    private String convidadoNome;

    @NonNull
    @Column(name = "convidado_email")
    private String convidadoEmail;

    @Column(name = "keycloak_id")
    private String keycloackId;
}
