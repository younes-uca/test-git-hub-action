package  ma.zyn.app.dao.criteria.core.commande;


import ma.zyn.app.dao.criteria.core.config.EtatCommandeCriteria;

import ma.zyn.app.zynerator.criteria.BaseCriteria;

import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class CommandeCriteria extends  BaseCriteria  {

    private String ref;
    private String refLike;
    private String montant;
    private String montantMin;
    private String montantMax;
    private LocalDateTime dateCommande;
    private LocalDateTime dateCommandeFrom;
    private LocalDateTime dateCommandeTo;

    private EtatCommandeCriteria etatCommande ;
    private List<EtatCommandeCriteria> etatCommandes ;


    public String getRef(){
        return this.ref;
    }
    public void setRef(String ref){
        this.ref = ref;
    }
    public String getRefLike(){
        return this.refLike;
    }
    public void setRefLike(String refLike){
        this.refLike = refLike;
    }

    public String getMontant(){
        return this.montant;
    }
    public void setMontant(String montant){
        this.montant = montant;
    }   
    public String getMontantMin(){
        return this.montantMin;
    }
    public void setMontantMin(String montantMin){
        this.montantMin = montantMin;
    }
    public String getMontantMax(){
        return this.montantMax;
    }
    public void setMontantMax(String montantMax){
        this.montantMax = montantMax;
    }
      
    public LocalDateTime getDateCommande(){
        return this.dateCommande;
    }
    public void setDateCommande(LocalDateTime dateCommande){
        this.dateCommande = dateCommande;
    }
    public LocalDateTime getDateCommandeFrom(){
        return this.dateCommandeFrom;
    }
    public void setDateCommandeFrom(LocalDateTime dateCommandeFrom){
        this.dateCommandeFrom = dateCommandeFrom;
    }
    public LocalDateTime getDateCommandeTo(){
        return this.dateCommandeTo;
    }
    public void setDateCommandeTo(LocalDateTime dateCommandeTo){
        this.dateCommandeTo = dateCommandeTo;
    }

    public EtatCommandeCriteria getEtatCommande(){
        return this.etatCommande;
    }

    public void setEtatCommande(EtatCommandeCriteria etatCommande){
        this.etatCommande = etatCommande;
    }
    public List<EtatCommandeCriteria> getEtatCommandes(){
        return this.etatCommandes;
    }

    public void setEtatCommandes(List<EtatCommandeCriteria> etatCommandes){
        this.etatCommandes = etatCommandes;
    }
}
