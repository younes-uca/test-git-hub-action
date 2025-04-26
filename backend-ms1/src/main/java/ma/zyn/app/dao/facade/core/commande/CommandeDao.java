package ma.zyn.app.dao.facade.core.commande;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.commande.Commande;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.commande.Commande;
import java.util.List;


@Repository
public interface CommandeDao extends AbstractRepository<Commande,Long>  {
    Commande findByRef(String ref);
    int deleteByRef(String ref);

    List<Commande> findByEtatCommandeCode(String code);
    List<Commande> findByEtatCommandeId(Long id);
    int deleteByEtatCommandeId(Long id);
    int deleteByEtatCommandeCode(String code);
    long countByEtatCommandeCode(String code);

    @Query("SELECT NEW Commande(item.id,item.ref) FROM Commande item")
    List<Commande> findAllOptimized();

}
