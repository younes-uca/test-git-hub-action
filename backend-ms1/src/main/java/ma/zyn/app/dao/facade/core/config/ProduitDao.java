package ma.zyn.app.dao.facade.core.config;

import org.springframework.data.jpa.repository.Query;
import ma.zyn.app.zynerator.repository.AbstractRepository;
import ma.zyn.app.bean.core.config.Produit;
import org.springframework.stereotype.Repository;
import ma.zyn.app.bean.core.config.Produit;
import java.util.List;


@Repository
public interface ProduitDao extends AbstractRepository<Produit,Long>  {
    Produit findByRef(String ref);
    int deleteByRef(String ref);


    @Query("SELECT NEW Produit(item.id,item.libelle) FROM Produit item")
    List<Produit> findAllOptimized();

}
