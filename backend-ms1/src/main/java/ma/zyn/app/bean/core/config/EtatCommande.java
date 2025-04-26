package ma.zyn.app.bean.core.config;








import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "etat_commande")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="etat_commande_seq",sequenceName="etat_commande_seq",allocationSize=1, initialValue = 1)
public class EtatCommande  extends BaseEntity     {




    @Column(length = 500)
    private String libelle;

    @Column(length = 500)
    private String code;

    @Column(length = 500)
    private String style;

    private String description;



    public EtatCommande(){
        super();
    }

    public EtatCommande(Long id){
        this.id = id;
    }

    public EtatCommande(Long id,String libelle){
        this.id = id;
        this.libelle = libelle ;
    }
    public EtatCommande(String libelle){
        this.libelle = libelle ;
    }
    public EtatCommande(String libelle,String code){
        this.libelle=libelle;
        this.code=code;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="etat_commande_seq")
      @Override
    public Long getId(){
        return this.id;
    }
        @Override
    public void setId(Long id){
        this.id = id;
    }
    public String getLibelle(){
        return this.libelle;
    }
    public void setLibelle(String libelle){
        this.libelle = libelle;
    }
    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getStyle(){
        return this.style;
    }
    public void setStyle(String style){
        this.style = style;
    }
      @Column(columnDefinition="TEXT")
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtatCommande etatCommande = (EtatCommande) o;
        return id != null && id.equals(etatCommande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

