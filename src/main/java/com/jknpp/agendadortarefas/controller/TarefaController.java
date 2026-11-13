package com.jknpp.agendadortarefas.controller;


import com.jknpp.agendadortarefas.business.dto.TarefaDto;
import com.jknpp.agendadortarefas.business.service.TarefaService;
import com.jknpp.agendadortarefas.infrastructure.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefaController {
    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDto> cadastrarTarefa(@RequestHeader("Authorization") String token,
                                                     @RequestBody TarefaDto dto){
    return ResponseEntity.ok(tarefaService.cadastrarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefaDto>> buscaListaDeTarefasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal){
        return ResponseEntity.ok(tarefaService.buscarTarefasPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefaDto>> buscarTarefaPorEmail(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefaService.buscarTarefaEmail(token));
    }

    @PutMapping
    public ResponseEntity<TarefaDto> modificarTarefa(@RequestParam("id") String id,@RequestBody TarefaDto dto){
        return ResponseEntity.ok(tarefaService.modificarTarefa(id, dto));
    }

    @PatchMapping
    public ResponseEntity<TarefaDto> modificarStatusTarefa(@RequestParam("id") String id, @RequestParam("status") Status status){
        return ResponseEntity.ok(tarefaService.modificarStatusTarefa(id, status));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarTarefa(@RequestParam("id") String id){
        tarefaService.deletarTarefa(id);
        return ResponseEntity.ok().build();
    }
}
