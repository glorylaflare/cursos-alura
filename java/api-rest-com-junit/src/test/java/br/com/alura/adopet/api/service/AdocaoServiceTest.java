package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;
    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Mock
    private AprovacaoAdocaoDto aprovacaoAdocaoDto;

    @Mock
    private ReprovacaoAdocaoDto reprovacaoAdocaoDto;

    @Spy
    private Adocao adocao;

    @Test
    @DisplayName("Deve verificar se a adoção é salva ao ser solicitada")
    void VerificaSeAdocaoESalvaAoSoliticar() {
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        adocaoService.solicitar(dto);

        then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();
        Assertions.assertEquals(pet, adocaoSalva.getPet());
        Assertions.assertEquals(tutor, adocaoSalva.getTutor());
        Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    @DisplayName("Deve verificar se os validadores de adoção são chamados após a solicitação")
    void VerificaSeValidadorEChamado() {
        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.add(validador1);
        validacoes.add(validador2);

        adocaoService.solicitar(dto);

        BDDMockito.then(validador1).should().validar(dto);
        BDDMockito.then(validador2).should().validar(dto);
    }

    @Test
    @DisplayName("Deve verificar se envia um email ao solicitar adocao")
    void VerificaSeEnviaEmailAoSolicitarAdocao() {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(10l, 30l, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        adocaoService.solicitar(dto);

        then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocao = adocaoCaptor.getValue();
        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação."
        );
    }

    @Test
    @DisplayName("Deve aprovar uma adoção")
    void DeveAprovarUmaAdocao() {
        given(adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("marcelo@email.com");
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Marcelo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        adocaoService.aprovar(aprovacaoAdocaoDto);

        then(adocao).should().marcarComoAprovada();
        Assertions.assertEquals(StatusAdocao.APROVADO, adocao.getStatus());
    }

    @Test
    @DisplayName("Deve enviar email ao aprovar uma adoção")
    void DeveEnviarEmailAoAprovarUmaAdocao() {
        given(adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("marcelo@email.com");
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Marcelo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        adocaoService.aprovar(aprovacaoAdocaoDto);

        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet."
        );
    }

    @Test
    @DisplayName("Deve reprovar uma adoção")
    void DeveReprovarUmaAdocao() {
        given(adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("marcelo@email.com");
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Marcelo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        adocaoService.aprovar(aprovacaoAdocaoDto);

        then(adocao).should().marcarComoAprovada();
        Assertions.assertEquals(StatusAdocao.REPROVADO, adocao.getStatus());
    }

    @Test
    @DisplayName("Deve enviar email ao reprovar uma adoção")
    void DeveEnviarEmailAoReprovarUmaAdocao() {
        given(adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(abrigo.getEmail()).willReturn("marcelo@email.com");
        given(adocao.getTutor()).willReturn(tutor);
        given(tutor.getNome()).willReturn("Marcelo");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        adocaoService.reprovar(reprovacaoAdocaoDto);

        then(emailService).should().enviarEmail(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus()
        );
    }
}