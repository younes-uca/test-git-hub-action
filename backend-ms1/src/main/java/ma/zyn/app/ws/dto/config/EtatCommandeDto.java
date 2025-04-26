package  ma.zyn.app.ws.dto.config;

import ma.zyn.app.zynerator.dto.AuditBaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;





@JsonInclude(JsonInclude.Include.NON_NULL)
public class EtatCommandeDto  extends AuditBaseDto {

    private String libelle  ;
    private String code  ;
    private String style  ;
    private String description  ;




    public EtatCommandeDto(){
        super();
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


    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }








}
