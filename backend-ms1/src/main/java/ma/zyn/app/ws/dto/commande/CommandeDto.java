package  ma.zyn.app.ws.dto.commande;

import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;


import ma.zyn.app.ws.dto.config.EtatCommandeDto;
import ma.zyn.app.ws.dto.config.ProduitDto;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommandeDto  extends AuditBaseDto {

    private String ref  ;
    private BigDecimal montant  ;
    private String dateCommande ;

    private EtatCommandeDto etatCommande ;

    private List<CommandeDetailDto> commandeDetails ;


    public CommandeDto(){
        super();
    }




    public String getRef(){
        return this.ref;
    }
    public void setRef(String ref){
        this.ref = ref;
    }


    public BigDecimal getMontant(){
        return this.montant;
    }
    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    public String getDateCommande(){
        return this.dateCommande;
    }
    public void setDateCommande(String dateCommande){
        this.dateCommande = dateCommande;
    }


    public EtatCommandeDto getEtatCommande(){
        return this.etatCommande;
    }

    public void setEtatCommande(EtatCommandeDto etatCommande){
        this.etatCommande = etatCommande;
    }



    public List<CommandeDetailDto> getCommandeDetails(){
        return this.commandeDetails;
    }

    public void setCommandeDetails(List<CommandeDetailDto> commandeDetails){
        this.commandeDetails = commandeDetails;
    }



}
