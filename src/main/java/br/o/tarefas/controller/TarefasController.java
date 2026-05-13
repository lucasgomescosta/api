package br.o.tarefas.controller;

import br.o.tarefas.dto.TarefaDTO;
import br.o.tarefas.service.TarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "API REST para gerenciamento de tarefas")
public class TarefasController {
    
    @Autowired
    private TarefaService tarefaService;
    
    /**
     * POST /tarefas - Criar uma nova tarefa
     */
    @PreAuthorize("hasRole('TAREFA')")
    @PostMapping
    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<TarefaDTO> create(@Valid @RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO tarefaCriada = tarefaService.create(tarefaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaCriada);
    }
    
    /**
     * GET /tarefas/{id} - Obter tarefa por ID
     */
    @PreAuthorize("hasRole('TAREFA')")
    @GetMapping("/{id}")
    @Operation(summary = "Obter tarefa por ID", description = "Retorna uma tarefa específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<TarefaDTO> get(
            @Parameter(description = "ID da tarefa", required = true)
            @PathVariable Long id) {
        TarefaDTO tarefa = tarefaService.getById(id);
        return ResponseEntity.ok(tarefa);
    }
    
    /**
     * GET /tarefas - Obter todas as tarefas
     */
    @PreAuthorize("hasRole('TAREFA')")
    @GetMapping
    @Operation(summary = "Listar todas as tarefas", description = "Retorna uma lista com todas as tarefas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class)))
    public ResponseEntity<List<TarefaDTO>> getAll() {
        List<TarefaDTO> tarefas = tarefaService.getAll();
        return ResponseEntity.ok(tarefas);
    }
    
    /**
     * GET /tarefas/buscar/titulo?titulo=xxx - Buscar tarefas por título
     */
    @PreAuthorize("hasRole('TAREFA')")
    @GetMapping("/buscar/titulo")
    @Operation(summary = "Buscar tarefas por título", description = "Busca tarefas que contenham o título especificado")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class)))
    public ResponseEntity<List<TarefaDTO>> buscarPorTitulo(
            @Parameter(description = "Título ou parte do título da tarefa", required = true)
            @RequestParam String titulo) {
        List<TarefaDTO> tarefas = tarefaService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(tarefas);
    }
    
    /**
     * GET /tarefas/buscar/local?local=xxx - Buscar tarefas por local
     */
    @PreAuthorize("hasRole('TAREFA')")
    @GetMapping("/buscar/local")
    @Operation(summary = "Buscar tarefas por local", description = "Busca tarefas que contenham o local especificado")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class)))
    public ResponseEntity<List<TarefaDTO>> buscarPorLocal(
            @Parameter(description = "Local ou parte do local da tarefa", required = true)
            @RequestParam String local) {
        List<TarefaDTO> tarefas = tarefaService.buscarPorLocal(local);
        return ResponseEntity.ok(tarefas);
    }
    
    /**
     * PUT /tarefas/{id} - Atualizar tarefa existente
     */
    @PreAuthorize("hasRole('TAREFA')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza uma tarefa existente com novos dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TarefaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<TarefaDTO> update(
            @Parameter(description = "ID da tarefa", required = true)
            @PathVariable Long id,
            @Valid @RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO tarefaAtualizada = tarefaService.update(id, tarefaDTO);
        return ResponseEntity.ok(tarefaAtualizada);
    }
    
    /**
     * DELETE /tarefas/{id} - Deletar tarefa
     */
    @PreAuthorize("hasRole('TAREFA')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da tarefa", required = true)
            @PathVariable Long id) {
        tarefaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
