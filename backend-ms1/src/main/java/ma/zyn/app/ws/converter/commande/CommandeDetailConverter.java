package  ma.zyn.app.ws.converter.commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.zyn.app.ws.converter.commande.CommandeConverter;
import ma.zyn.app.bean.core.commande.Commande;
import ma.zyn.app.ws.converter.config.ProduitConverter;
import ma.zyn.app.bean.core.config.Produit;

import ma.zyn.app.bean.core.commande.Commande;


import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.ws.dto.commande.CommandeDetailDto;

@Component
public class CommandeDetailConverter {

    @Autowired
    private CommandeConverter commandeConverter ;
    @Autowired
    private ProduitConverter produitConverter ;
    private boolean commande;
    private boolean produit;

    public  CommandeDetailConverter() {
        initObject(true);
    }

    public CommandeDetail toItem(CommandeDetailDto dto) {
        if (dto == null) {
            return null;
        } else {
        CommandeDetail item = new CommandeDetail();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getPrix()))
                item.setPrix(dto.getPrix());
            if(StringUtil.isNotEmpty(dto.getQte()))
                item.setQte(dto.getQte());
            if(dto.getCommande() != null && dto.getCommande().getId() != null){
                item.setCommande(new Commande());
                item.getCommande().setId(dto.getCommande().getId());
                item.getCommande().setRef(dto.getCommande().getRef());
            }

            if(this.produit && dto.getProduit()!=null)
                item.setProduit(produitConverter.toItem(dto.getProduit())) ;




        return item;
        }
    }


    public CommandeDetailDto toDto(CommandeDetail item) {
        if (item == null) {
            return null;
        } else {
            CommandeDetailDto dto = new CommandeDetailDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getPrix()))
                dto.setPrix(item.getPrix());
            if(StringUtil.isNotEmpty(item.getQte()))
                dto.setQte(item.getQte());
            if(this.commande && item.getCommande()!=null) {
                dto.setCommande(commandeConverter.toDto(item.getCommande())) ;

            }
            if(this.produit && item.getProduit()!=null) {
                dto.setProduit(produitConverter.toDto(item.getProduit())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.commande = value;
        this.produit = value;
    }
	
    public List<CommandeDetail> toItem(List<CommandeDetailDto> dtos) {
        List<CommandeDetail> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CommandeDetailDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CommandeDetailDto> toDto(List<CommandeDetail> items) {
        List<CommandeDetailDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (CommandeDetail item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CommandeDetailDto dto, CommandeDetail t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getCommande() == null  && dto.getCommande() != null){
            t.setCommande(new Commande());
        }else if (t.getCommande() != null  && dto.getCommande() != null){
            t.setCommande(null);
            t.setCommande(new Commande());
        }
        if(t.getProduit() == null  && dto.getProduit() != null){
            t.setProduit(new Produit());
        }else if (t.getProduit() != null  && dto.getProduit() != null){
            t.setProduit(null);
            t.setProduit(new Produit());
        }
        if (dto.getCommande() != null)
        commandeConverter.copy(dto.getCommande(), t.getCommande());
        if (dto.getProduit() != null)
        produitConverter.copy(dto.getProduit(), t.getProduit());
    }

    public List<CommandeDetail> copy(List<CommandeDetailDto> dtos) {
        List<CommandeDetail> result = new ArrayList<>();
        if (dtos != null) {
            for (CommandeDetailDto dto : dtos) {
                CommandeDetail instance = new CommandeDetail();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public CommandeConverter getCommandeConverter(){
        return this.commandeConverter;
    }
    public void setCommandeConverter(CommandeConverter commandeConverter ){
        this.commandeConverter = commandeConverter;
    }
    public ProduitConverter getProduitConverter(){
        return this.produitConverter;
    }
    public void setProduitConverter(ProduitConverter produitConverter ){
        this.produitConverter = produitConverter;
    }
    public boolean  isCommande(){
        return this.commande;
    }
    public void  setCommande(boolean commande){
        this.commande = commande;
    }
    public boolean  isProduit(){
        return this.produit;
    }
    public void  setProduit(boolean produit){
        this.produit = produit;
    }
}
