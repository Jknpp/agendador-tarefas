package com.jknpp.agendadortarefas.business.service;


import com.jknpp.agendadortarefas.business.dto.TarefaDto;
import com.jknpp.agendadortarefas.infrastructure.entity.Status;
import com.jknpp.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.jknpp.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.jknpp.agendadortarefas.infrastructure.mapper.TarefaMapper;
import com.jknpp.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.jknpp.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefasRepository;
    private final TarefaMapper tarefaMapper;
    private final JwtUtil jwtUtil;

    public TarefaDto cadastrarTarefa(String token, TarefaDto dto){
        String email = jwtUtil.extractUsername(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(Status.PENDENTE);
        dto.setEmailUsuario(email);

        TarefasEntity tarefa = tarefaMapper.toEntity(dto);
        return tarefaMapper.toDto(tarefasRepository.save(tarefa));
    }

    public List<TarefaDto> buscarTarefasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal){
        List<TarefasEntity> tarefas = tarefasRepository.findByDataEventoBetweenAndStatus(dataInicial, dataFinal, Status.PENDENTE);

        return tarefas.stream()
                .map(entity -> TarefaDto.builder()
                        .id(entity.getId())
                        .nomeTarefa(entity.getNomeTarefa())
                        .descricao(entity.getDescricao())
                        .dataCriacao(entity.getDataCriacao())
                        .dataEvento(entity.getDataEvento())
                        .emailUsuario(entity.getEmailUsuario())
                        .dataAlteracao(entity.getDataAlteracao())
                        .status(entity.getStatus())
                        .build())
                .toList();

    }

    public List<TarefaDto> buscarTarefaEmail(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        List<TarefasEntity> tarefas = tarefasRepository.findByEmailUsuario(email);
        return tarefaMapper.listaParaDto(tarefas);
    }

    public TarefaDto modificarTarefa(String id, TarefaDto dto){
        TarefasEntity tarefa = tarefasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa no id não encontrado " +id));

        TarefasEntity tarefasEntity = tarefaMapper.updateTarefa(dto, tarefa);

        return tarefaMapper.toDto(tarefasRepository.save(tarefasEntity));
    }

    public TarefaDto modificarStatusTarefa(String id, Status status){
        TarefasEntity tarefa = tarefasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa no id não encontrado " +id));
        tarefa.setStatus(status);
        return tarefaMapper.toDto(tarefasRepository.save(tarefa));
    }

    public void deletarTarefa(String id){
        TarefasEntity tarefas = tarefasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa no id não encontrado " +id));
        tarefasRepository.delete(tarefas);
    }
}
