package br.o.tarefas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TarefaDTO", description = "DTO representando uma Tarefa")
public class TarefaDTO {
    
    @Schema(description = "ID único da tarefa", example = "1")
    private Long id;
    
    @NotBlank(message = "Título não pode estar vazio")
    @Size(min = 3, max = 255, message = "Título deve ter entre 3 e 255 caracteres")
    @Schema(description = "Título da tarefa", example = "Implementar API", required = true)
    private String titulo;
    
    @Size(max = 1000, message = "Descrição não pode exceder 1000 caracteres")
    @Schema(description = "Descrição detalhada da tarefa", example = "Criar endpoints REST para gerenciamento de tarefas")
    private String descricao;
    
    @Size(max = 255, message = "Local não pode exceder 255 caracteres")
    @Schema(description = "Local onde a tarefa deve ser realizada", example = "Escritório")
    private String local;
    
    @Schema(description = "Data e hora da tarefa", example = "2026-04-06T10:30:00")
    private String dataHora;

    @NotNull(message = "Nenhum convidado foi selecionado")
    private List<ConvidadoDTO> convidados;

    private List<ConvidadoPendenteDTO> convidadosPendentes;

    @NotNull
    private UsuarioDTO criador;

}
