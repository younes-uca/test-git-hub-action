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
import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.ws.dto.config.EtatCommandeDto;

@Component
public class EtatCommandeConverter {



    public EtatCommande toItem(EtatCommandeDto dto) {
        if (dto == null) {
            return null;
        } else {
        EtatCommande item = new EtatCommande();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getLibelle()))
                item.setLibelle(dto.getLibelle());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getStyle()))
                item.setStyle(dto.getStyle());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());



        return item;
        }
    }


    public EtatCommandeDto toDto(EtatCommande item) {
        if (item == null) {
            return null;
        } else {
            EtatCommandeDto dto = new EtatCommandeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getLibelle()))
                dto.setLibelle(item.getLibelle());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getStyle()))
                dto.setStyle(item.getStyle());
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());


        return dto;
        }
    }


	
    public List<EtatCommande> toItem(List<EtatCommandeDto> dtos) {
        List<EtatCommande> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (EtatCommandeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<EtatCommandeDto> toDto(List<EtatCommande> items) {
        List<EtatCommandeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (EtatCommande item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(EtatCommandeDto dto, EtatCommande t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<EtatCommande> copy(List<EtatCommandeDto> dtos) {
        List<EtatCommande> result = new ArrayList<>();
        if (dtos != null) {
            for (EtatCommandeDto dto : dtos) {
                EtatCommande instance = new EtatCommande();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
