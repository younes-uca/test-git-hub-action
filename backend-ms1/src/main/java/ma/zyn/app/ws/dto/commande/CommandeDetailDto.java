package  ma.zyn.app.ws.dto.commande;

import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;


import ma.zyn.app.ws.dto.config.ProduitDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandeDetailDto  extends AuditBaseDto {

    private BigDecimal prix  ;
    private BigDecimal qte  ;

    private CommandeDto commande ;
    private ProduitDto produit ;



    public CommandeDetailDto(){
        super();
    }




    public BigDecimal getPrix(){
        return this.prix;
    }
    public void setPrix(BigDecimal prix){
        this.prix = prix;
    }


    public BigDecimal getQte(){
        return this.qte;
    }
    public void setQte(BigDecimal qte){
        this.qte = qte;
    }


    public CommandeDto getCommande(){
        return this.commande;
    }

    public void setCommande(CommandeDto commande){
        this.commande = commande;
    }
    public ProduitDto getProduit(){
        return this.produit;
    }

    public void setProduit(ProduitDto produit){
        this.produit = produit;
    }






}
