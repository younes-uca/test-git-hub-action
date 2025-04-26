package  ma.zyn.app.ws.dto.config;

import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;




@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProduitDto  extends AuditBaseDto {

    private String ref  ;
    private String libelle  ;
    private BigDecimal prix  ;




    public ProduitDto(){
        super();
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








}
