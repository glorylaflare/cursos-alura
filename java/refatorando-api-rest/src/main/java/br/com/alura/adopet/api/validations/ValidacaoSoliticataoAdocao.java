package br.com.alura.adopet.api.validations;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;

public interface ValidacaoSoliticataoAdocao {

    void validar(SolicitacaoAdocaoDto dto);
}
