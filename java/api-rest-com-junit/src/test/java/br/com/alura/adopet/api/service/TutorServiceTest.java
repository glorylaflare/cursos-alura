package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService tutorService;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private CadastroTutorDto dto;

    @Mock
    private Tutor tutor;

    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Test
    void NaoDeveCadastrarTutorTelefoneOuEmailJaCadastrado() {
        //Arrange + Act
        given(tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(true);

        //Assert
        assertThrows(ValidationException.class, () -> tutorService.cadastrar(dto));
    }

    @Test
    void DeveCadastrarTutor() {
        //Arrange
        given(tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(false);

        //Act + Assert
        assertDoesNotThrow(() -> tutorService.cadastrar(dto));
        then(tutorRepository).should().save(new Tutor(dto));
    }

    @Test
    void DeveAtualizarDadosTutor() {
        //Arrange
        given(tutorRepository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        //Act
        tutorService.atualizar(atualizacaoTutorDto);

        //Assert
        then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }
}