package ma.zyn.app.bean.core.commande;

import java.util.List;

import java.time.LocalDateTime;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.bean.core.config.Produit;


import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "commande")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="commande_seq",sequenceName="commande_seq",allocationSize=1, initialValue = 1)
public class Commande  extends BaseEntity     {




    @Column(length = 500)
    private String ref;

    private BigDecimal montant = BigDecimal.ZERO;

    private LocalDateTime dateCommande ;

    private EtatCommande etatCommande ;

    private List<CommandeDetail> commandeDetails ;

    public Commande(){
        super();
    }

    public Commande(Long id){
        this.id = id;
    }

    public Commande(Long id,String ref){
        this.id = id;
        this.ref = ref ;
    }
    public Commande(String ref){
        this.ref = ref ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="commande_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etat_commande")
    public EtatCommande getEtatCommande(){
        return this.etatCommande;
    }
    public void setEtatCommande(EtatCommande etatCommande){
        this.etatCommande = etatCommande;
    }
    public LocalDateTime getDateCommande(){
        return this.dateCommande;
    }
    public void setDateCommande(LocalDateTime dateCommande){
        this.dateCommande = dateCommande;
    }
    @OneToMany(mappedBy = "commande")
    public List<CommandeDetail> getCommandeDetails(){
        return this.commandeDetails;
    }

    public void setCommandeDetails(List<CommandeDetail> commandeDetails){
        this.commandeDetails = commandeDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commande commande = (Commande) o;
        return id != null && id.equals(commande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

