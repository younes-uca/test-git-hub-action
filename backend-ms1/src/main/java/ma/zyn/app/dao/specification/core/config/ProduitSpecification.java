package  ma.zyn.app.dao.specification.core.config;

import ma.zyn.app.dao.criteria.core.config.ProduitCriteria;
import ma.zyn.app.bean.core.config.Produit;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class ProduitSpecification extends  AbstractSpecification<ProduitCriteria, Produit>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("ref", criteria.getRef(),criteria.getRefLike());
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicateBigDecimal("prix", criteria.getPrix(), criteria.getPrixMin(), criteria.getPrixMax());
    }

    public ProduitSpecification(ProduitCriteria criteria) {
        super(criteria);
    }

    public ProduitSpecification(ProduitCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
