package  ma.zyn.app.ws.converter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.config.Produit;
import ma.zyn.app.ws.dto.config.ProduitDto;

@Component
public class ProduitConverter {



    public Produit toItem(ProduitDto dto) {
        if (dto == null) {
            return null;
        } else {
        Produit item = new Produit();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getRef()))
                item.setRef(dto.getRef());
            if(StringUtil.isNotEmpty(dto.getLibelle()))
                item.setLibelle(dto.getLibelle());
            if(StringUtil.isNotEmpty(dto.getPrix()))
                item.setPrix(dto.getPrix());



        return item;
        }
    }


    public ProduitDto toDto(Produit item) {
        if (item == null) {
            return null;
        } else {
            ProduitDto dto = new ProduitDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getRef()))
                dto.setRef(item.getRef());
            if(StringUtil.isNotEmpty(item.getLibelle()))
                dto.setLibelle(item.getLibelle());
            if(StringUtil.isNotEmpty(item.getPrix()))
                dto.setPrix(item.getPrix());


        return dto;
        }
    }


	
    public List<Produit> toItem(List<ProduitDto> dtos) {
        List<Produit> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ProduitDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ProduitDto> toDto(List<Produit> items) {
        List<ProduitDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Produit item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ProduitDto dto, Produit t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Produit> copy(List<ProduitDto> dtos) {
        List<Produit> result = new ArrayList<>();
        if (dtos != null) {
            for (ProduitDto dto : dtos) {
                Produit instance = new Produit();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
