package ma.zyn.app.unit.dao.facade.core.config;

import ma.zyn.app.bean.core.config.Produit;
import ma.zyn.app.dao.facade.core.config.ProduitDao;

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
public class ProduitDaoTest {

@Autowired
    private ProduitDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByRef(){
        String ref = "ref-1";
        Produit entity = new Produit();
        entity.setRef(ref);
        underTest.save(entity);
        Produit loaded = underTest.findByRef(ref);
        assertThat(loaded.getRef()).isEqualTo(ref);
    }

    @Test
    void shouldDeleteByRef() {
        String ref = "ref-12345678";
        int result = underTest.deleteByRef(ref);

        Produit loaded = underTest.findByRef(ref);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Produit entity = new Produit();
        entity.setId(id);
        underTest.save(entity);
        Produit loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Produit entity = new Produit();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Produit loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Produit> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Produit> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Produit given = constructSample(1);
        Produit saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Produit constructSample(int i) {
		Produit given = new Produit();
        given.setRef("ref-"+i);
        given.setLibelle("libelle-"+i);
        given.setPrix(BigDecimal.TEN);
        return given;
    }

}
