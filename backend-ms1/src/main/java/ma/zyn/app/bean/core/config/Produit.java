package ma.zyn.app.bean.core.config;








import com.fasterxml.jackson.annotation.JsonInclude;
import ma.zyn.app.zynerator.bean.BaseEntity;
import jakarta.persistence.*;
import java.util.Objects;
import java.math.BigDecimal;

@Entity
@Table(name = "produit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="produit_seq",sequenceName="produit_seq",allocationSize=1, initialValue = 1)
public class Produit  extends BaseEntity     {




    @Column(length = 500)
    private String ref;

    @Column(length = 500)
    private String libelle;

    private BigDecimal prix = BigDecimal.ZERO;



    public Produit(){
        super();
    }

    public Produit(Long id){
        this.id = id;
    }

    public Produit(Long id,String libelle){
        this.id = id;
        this.libelle = libelle ;
    }
    public Produit(String libelle){
        this.libelle = libelle ;
    }




    @Id
    @Column(name = "id")
    @GeneratedValue(strategy =  GenerationType.SEQUENCE,generator="produit_seq")
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
    public String getLibelle(){
        return this.libelle;
    }
    public void setLibelle(String libelle){
        this.libelle = libelle;
    }
    public BigDecimal getPrix(){
        return this.prix;
    }
    public void setPrix(BigDecimal prix){
        this.prix = prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return id != null && id.equals(produit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

