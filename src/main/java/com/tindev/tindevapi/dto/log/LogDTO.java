package com.tindev.tindevapi.dto.log;

import com.tindev.tindevapi.enums.TipoLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {

    @Enumerated(EnumType.STRING)
    private TipoLog tipoLog;

    private String descricao;

}
