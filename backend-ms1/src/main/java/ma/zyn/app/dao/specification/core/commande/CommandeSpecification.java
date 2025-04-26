package  ma.zyn.app.dao.specification.core.commande;

import ma.zyn.app.dao.criteria.core.commande.CommandeCriteria;
import ma.zyn.app.bean.core.commande.Commande;
import ma.zyn.app.zynerator.specification.AbstractSpecification;


public class CommandeSpecification extends  AbstractSpecification<CommandeCriteria, Commande>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("ref", criteria.getRef(),criteria.getRefLike());
        addPredicateBigDecimal("montant", criteria.getMontant(), criteria.getMontantMin(), criteria.getMontantMax());
        addPredicate("dateCommande", criteria.getDateCommande(), criteria.getDateCommandeFrom(), criteria.getDateCommandeTo());
        addPredicateFk("etatCommande","id", criteria.getEtatCommande()==null?null:criteria.getEtatCommande().getId());
        addPredicateFk("etatCommande","id", criteria.getEtatCommandes());
        addPredicateFk("etatCommande","code", criteria.getEtatCommande()==null?null:criteria.getEtatCommande().getCode());
    }

    public CommandeSpecification(CommandeCriteria criteria) {
        super(criteria);
    }

    public CommandeSpecification(CommandeCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
