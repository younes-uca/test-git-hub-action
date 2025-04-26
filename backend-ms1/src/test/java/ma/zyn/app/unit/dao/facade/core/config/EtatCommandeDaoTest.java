package ma.zyn.app.unit.dao.facade.core.config;

import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.dao.facade.core.config.EtatCommandeDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.IntStream;
import java.time.LocalDateTime;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class EtatCommandeDaoTest {

@Autowired
    private EtatCommandeDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        EtatCommande entity = new EtatCommande();
        entity.setCode(code);
        underTest.save(entity);
        EtatCommande loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        EtatCommande loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        EtatCommande entity = new EtatCommande();
        entity.setId(id);
        underTest.save(entity);
        EtatCommande loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        EtatCommande entity = new EtatCommande();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        EtatCommande loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<EtatCommande> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<EtatCommande> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        EtatCommande given = constructSample(1);
        EtatCommande saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
