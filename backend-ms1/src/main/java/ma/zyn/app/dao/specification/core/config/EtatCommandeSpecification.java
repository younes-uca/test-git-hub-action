package  ma.zyn.app.dao.specification.core.config;

import ma.zyn.app.dao.criteria.core.config.EtatCommandeCriteria;
import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class EtatCommandeSpecification extends  AbstractSpecification<EtatCommandeCriteria, EtatCommande>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
    }

    public EtatCommandeSpecification(EtatCommandeCriteria criteria) {
        super(criteria);
    }

    public EtatCommandeSpecification(EtatCommandeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
