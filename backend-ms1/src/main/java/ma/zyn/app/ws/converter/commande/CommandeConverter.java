package  ma.zyn.app.ws.converter.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;
import ma.zyn.app.zynerator.util.ListUtil;

import ma.zyn.app.ws.converter.config.EtatCommandeConverter;
import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.ws.converter.commande.CommandeDetailConverter;
import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.ws.converter.config.ProduitConverter;
import ma.zyn.app.bean.core.config.Produit;



import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.commande.Commande;
import ma.zyn.app.ws.dto.commande.CommandeDto;

@Component
public class CommandeConverter {

    @Autowired
    private EtatCommandeConverter etatCommandeConverter ;
    @Autowired
    private CommandeDetailConverter commandeDetailConverter ;
    @Autowired
    private ProduitConverter produitConverter ;
    private boolean etatCommande;
    private boolean commandeDetails;

    public  CommandeConverter() {
        init(true);
    }

    public Commande toItem(CommandeDto dto) {
        if (dto == null) {
            return null;
        } else {
        Commande item = new Commande();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getRef()))
                item.setRef(dto.getRef());
            if(StringUtil.isNotEmpty(dto.getMontant()))
                item.setMontant(dto.getMontant());
            if(StringUtil.isNotEmpty(dto.getDateCommande()))
                item.setDateCommande(DateUtil.stringEnToDate(dto.getDateCommande()));
            if(this.etatCommande && dto.getEtatCommande()!=null)
                item.setEtatCommande(etatCommandeConverter.toItem(dto.getEtatCommande())) ;


            if(this.commandeDetails && ListUtil.isNotEmpty(dto.getCommandeDetails()))
                item.setCommandeDetails(commandeDetailConverter.toItem(dto.getCommandeDetails()));


        return item;
        }
    }


    public CommandeDto toDto(Commande item) {
        if (item == null) {
            return null;
        } else {
            CommandeDto dto = new CommandeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getRef()))
                dto.setRef(item.getRef());
            if(StringUtil.isNotEmpty(item.getMontant()))
                dto.setMontant(item.getMontant());
            if(item.getDateCommande()!=null)
                dto.setDateCommande(DateUtil.dateTimeToString(item.getDateCommande()));
            if(this.etatCommande && item.getEtatCommande()!=null) {
                dto.setEtatCommande(etatCommandeConverter.toDto(item.getEtatCommande())) ;

            }
        if(this.commandeDetails && ListUtil.isNotEmpty(item.getCommandeDetails())){
            commandeDetailConverter.init(true);
            commandeDetailConverter.setCommande(false);
            dto.setCommandeDetails(commandeDetailConverter.toDto(item.getCommandeDetails()));
            commandeDetailConverter.setCommande(true);

        }


        return dto;
        }
    }

    public void init(boolean value) {
        initList(value);
    }

    public void initList(boolean value) {
        this.commandeDetails = value;
    }
    public void initObject(boolean value) {
        this.etatCommande = value;
    }
	
    public List<Commande> toItem(List<CommandeDto> dtos) {
        List<Commande> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CommandeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CommandeDto> toDto(List<Commande> items) {
        List<CommandeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Commande item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CommandeDto dto, Commande t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getEtatCommande() == null  && dto.getEtatCommande() != null){
            t.setEtatCommande(new EtatCommande());
        }else if (t.getEtatCommande() != null  && dto.getEtatCommande() != null){
            t.setEtatCommande(null);
            t.setEtatCommande(new EtatCommande());
        }
        if (dto.getEtatCommande() != null)
        etatCommandeConverter.copy(dto.getEtatCommande(), t.getEtatCommande());
        if (dto.getCommandeDetails() != null)
            t.setCommandeDetails(commandeDetailConverter.copy(dto.getCommandeDetails()));
    }

    public List<Commande> copy(List<CommandeDto> dtos) {
        List<Commande> result = new ArrayList<>();
        if (dtos != null) {
            for (CommandeDto dto : dtos) {
                Commande instance = new Commande();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public EtatCommandeConverter getEtatCommandeConverter(){
        return this.etatCommandeConverter;
    }
    public void setEtatCommandeConverter(EtatCommandeConverter etatCommandeConverter ){
        this.etatCommandeConverter = etatCommandeConverter;
    }
    public CommandeDetailConverter getCommandeDetailConverter(){
        return this.commandeDetailConverter;
    }
    public void setCommandeDetailConverter(CommandeDetailConverter commandeDetailConverter ){
        this.commandeDetailConverter = commandeDetailConverter;
    }
    public ProduitConverter getProduitConverter(){
        return this.produitConverter;
    }
    public void setProduitConverter(ProduitConverter produitConverter ){
        this.produitConverter = produitConverter;
    }
    public boolean  isEtatCommande(){
        return this.etatCommande;
    }
    public void  setEtatCommande(boolean etatCommande){
        this.etatCommande = etatCommande;
    }
    public boolean  isCommandeDetails(){
        return this.commandeDetails ;
    }
    public void  setCommandeDetails(boolean commandeDetails ){
        this.commandeDetails  = commandeDetails ;
    }
}
