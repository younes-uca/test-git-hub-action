package ma.zyn.app.dao.facade.core.config;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.config.EtatCommande;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.config.EtatCommande;
import java.util.List;


@Repository
public interface EtatCommandeDao extends AbstractRepository<EtatCommande,Long>  {
    EtatCommande findByCode(String code);
    int deleteByCode(String code);


    @Query("SELECT NEW EtatCommande(item.id,item.libelle) FROM EtatCommande item")
    List<EtatCommande> findAllOptimized();

}
