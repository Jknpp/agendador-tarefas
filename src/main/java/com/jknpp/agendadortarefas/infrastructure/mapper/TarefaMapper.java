package com.jknpp.agendadortarefas.infrastructure.mapper;


import com.jknpp.agendadortarefas.business.dto.TarefaDto;
import com.jknpp.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.springframework.stereotype.Component;
import java.util.List;



@Component
public class TarefaMapper {

    public TarefaDto toDto(TarefasEntity tarefasEntity){
        if(tarefasEntity == null){
            return null;
        }

        return TarefaDto.builder()
                .id(tarefasEntity.getId())
                .nomeTarefa(tarefasEntity.getNomeTarefa())
                .descricao(tarefasEntity.getDescricao())
                .dataCriacao(tarefasEntity.getDataCriacao())
                .dataEvento(tarefasEntity.getDataEvento())
                .emailUsuario(tarefasEntity.getEmailUsuario())
                .dataAlteracao(tarefasEntity.getDataAlteracao())
                .status(tarefasEntity.getStatus())
                .build();
    }

    public TarefasEntity toEntity(TarefaDto dto){
        if(dto == null){
            return null;
        }

        return TarefasEntity.builder()
                .id(dto.getId())
                .nomeTarefa(dto.getNomeTarefa())
                .descricao(dto.getDescricao())
                .dataCriacao(dto.getDataCriacao())
                .dataEvento(dto.getDataEvento())
                .emailUsuario(dto.getEmailUsuario())
                .dataAlteracao(dto.getDataAlteracao())
                .status(dto.getStatus())
                .build();
    }

    public TarefasEntity updateTarefa(TarefaDto dto, TarefasEntity entity){
        return TarefasEntity.builder()
                .id(entity.getId())
                .nomeTarefa(dto.getNomeTarefa() != null ? dto.getNomeTarefa() : entity.getNomeTarefa())
                .descricao(dto.getDescricao() != null ? dto.getDescricao() : entity.getDescricao())
                .dataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : entity.getDataCriacao())
                .dataEvento(dto.getDataEvento() != null ? dto.getDataEvento() : entity.getDataEvento())
                .emailUsuario(dto.getEmailUsuario() != null ? dto.getEmailUsuario() : entity.getEmailUsuario())
                .dataAlteracao(dto.getDataAlteracao() != null ? dto.getDataAlteracao() : entity.getDataAlteracao())
                .status(dto.getStatus() != null ? dto.getStatus() : entity.getStatus())
                .build();
    }

    public List<TarefaDto> listaParaDto(List<TarefasEntity> entity){
        return entity.stream()
                .map(this::toDto)
                .toList();
    }

}
