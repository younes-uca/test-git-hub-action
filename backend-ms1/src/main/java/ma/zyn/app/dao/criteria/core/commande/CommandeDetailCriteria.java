package  ma.zyn.app.dao.criteria.core.commande;


import ma.zyn.app.dao.criteria.core.config.ProduitCriteria;

import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;

public class CommandeDetailCriteria extends  BaseCriteria  {

    private String prix;
    private String prixMin;
    private String prixMax;
    private String qte;
    private String qteMin;
    private String qteMax;

    private CommandeCriteria commande ;
    private List<CommandeCriteria> commandes ;
    private ProduitCriteria produit ;
    private List<ProduitCriteria> produits ;


    public String getPrix(){
        return this.prix;
    }
    public void setPrix(String prix){
        this.prix = prix;
    }   
    public String getPrixMin(){
        return this.prixMin;
    }
    public void setPrixMin(String prixMin){
        this.prixMin = prixMin;
    }
    public String getPrixMax(){
        return this.prixMax;
    }
    public void setPrixMax(String prixMax){
        this.prixMax = prixMax;
    }
      
    public String getQte(){
        return this.qte;
    }
    public void setQte(String qte){
        this.qte = qte;
    }   
    public String getQteMin(){
        return this.qteMin;
    }
    public void setQteMin(String qteMin){
        this.qteMin = qteMin;
    }
    public String getQteMax(){
        return this.qteMax;
    }
    public void setQteMax(String qteMax){
        this.qteMax = qteMax;
    }
      

    public CommandeCriteria getCommande(){
        return this.commande;
    }

    public void setCommande(CommandeCriteria commande){
        this.commande = commande;
    }
    public List<CommandeCriteria> getCommandes(){
        return this.commandes;
    }

    public void setCommandes(List<CommandeCriteria> commandes){
        this.commandes = commandes;
    }
    public ProduitCriteria getProduit(){
        return this.produit;
    }

    public void setProduit(ProduitCriteria produit){
        this.produit = produit;
    }
    public List<ProduitCriteria> getProduits(){
        return this.produits;
    }

    public void setProduits(List<ProduitCriteria> produits){
        this.produits = produits;
    }
}
