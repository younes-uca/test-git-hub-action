package ma.zyn.app.unit.dao.facade.core.commande;

import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.dao.facade.core.commande.CommandeDetailDao;

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

import ma.zyn.app.bean.core.commande.Commande ;
import ma.zyn.app.bean.core.config.Produit ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CommandeDetailDaoTest {

@Autowired
    private CommandeDetailDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        CommandeDetail entity = new CommandeDetail();
        entity.setId(id);
        underTest.save(entity);
        CommandeDetail loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        CommandeDetail entity = new CommandeDetail();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        CommandeDetail loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<CommandeDetail> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<CommandeDetail> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        CommandeDetail given = constructSample(1);
        CommandeDetail saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private CommandeDetail constructSample(int i) {
		CommandeDetail given = new CommandeDetail();
        given.setPrix(BigDecimal.TEN);
        given.setQte(BigDecimal.TEN);
        given.setCommande(new Commande(1L));
        given.setProduit(new Produit(1L));
        return given;
    }

}
