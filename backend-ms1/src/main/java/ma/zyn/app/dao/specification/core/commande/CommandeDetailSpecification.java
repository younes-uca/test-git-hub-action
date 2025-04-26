package  ma.zyn.app.dao.specification.core.commande;

import ma.zyn.app.dao.criteria.core.commande.CommandeDetailCriteria;
import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CommandeDetailSpecification extends  AbstractSpecification<CommandeDetailCriteria, CommandeDetail>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicateBigDecimal("prix", criteria.getPrix(), criteria.getPrixMin(), criteria.getPrixMax());
        addPredicateBigDecimal("qte", criteria.getQte(), criteria.getQteMin(), criteria.getQteMax());
        addPredicateFk("commande","id", criteria.getCommande()==null?null:criteria.getCommande().getId());
        addPredicateFk("commande","id", criteria.getCommandes());
        addPredicateFk("commande","ref", criteria.getCommande()==null?null:criteria.getCommande().getRef());
        addPredicateFk("produit","id", criteria.getProduit()==null?null:criteria.getProduit().getId());
        addPredicateFk("produit","id", criteria.getProduits());
        addPredicateFk("produit","ref", criteria.getProduit()==null?null:criteria.getProduit().getRef());
    }

    public CommandeDetailSpecification(CommandeDetailCriteria criteria) {
        super(criteria);
    }

    public CommandeDetailSpecification(CommandeDetailCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
