package ma.zyn.app.unit.service.impl.admin.config;

import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.dao.facade.core.config.EtatCommandeDao;
import ma.zyn.app.service.impl.admin.config.EtatCommandeAdminServiceImpl;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;



import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class EtatCommandeAdminServiceImplTest {

    @Mock
    private EtatCommandeDao repository;
    private AutoCloseable autoCloseable;
    private EtatCommandeAdminServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new EtatCommandeAdminServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllEtatCommande() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveEtatCommande() {
        // Given
        EtatCommande toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteEtatCommande() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetEtatCommandeById() {
        // Given
        Long idToRetrieve = 1L; // Example EtatCommande ID to retrieve
        EtatCommande expected = new EtatCommande(); // You need to replace EtatCommande with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        EtatCommande result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private EtatCommande constructSample(int i) {
		EtatCommande given = new EtatCommande();
        given.setLibelle("libelle-"+i);
        given.setCode("code-"+i);
        given.setStyle("style-"+i);
        given.setDescription("description-"+i);
        return given;
    }

}
