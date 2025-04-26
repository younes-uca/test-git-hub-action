package  ma.zyn.app.ws.facade.admin.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.dao.criteria.core.config.EtatCommandeCriteria;
import ma.zyn.app.service.facade.admin.config.EtatCommandeAdminService;
import ma.zyn.app.ws.converter.config.EtatCommandeConverter;
import ma.zyn.app.ws.dto.config.EtatCommandeDto;
import ma.zyn.app.zynerator.controller.AbstractController;
import ma.zyn.app.zynerator.dto.AuditEntityDto;
import ma.zyn.app.zynerator.util.PaginatedList;


import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ma.zyn.app.zynerator.process.Result;


import org.springframework.web.multipart.MultipartFile;
import ma.zyn.app.zynerator.dto.FileTempDto;

@RestController
@RequestMapping("/api/admin/etatCommande/")
public class EtatCommandeRestAdmin {




    @Operation(summary = "Finds a list of all etatCommandes")
    @GetMapping("")
    public ResponseEntity<List<EtatCommandeDto>> findAll() throws Exception {
        ResponseEntity<List<EtatCommandeDto>> res = null;
        List<EtatCommande> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<EtatCommandeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all etatCommandes")
    @GetMapping("optimized")
    public ResponseEntity<List<EtatCommandeDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<EtatCommandeDto>> res = null;
        List<EtatCommande> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<EtatCommandeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a etatCommande by id")
    @GetMapping("id/{id}")
    public ResponseEntity<EtatCommandeDto> findById(@PathVariable Long id) {
        EtatCommande t = service.findById(id);
        if (t != null) {
            EtatCommandeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a etatCommande by libelle")
    @GetMapping("libelle/{libelle}")
    public ResponseEntity<EtatCommandeDto> findByLibelle(@PathVariable String libelle) {
	    EtatCommande t = service.findByReferenceEntity(new EtatCommande(libelle));
        if (t != null) {
            EtatCommandeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  etatCommande")
    @PostMapping("")
    public ResponseEntity<EtatCommandeDto> save(@RequestBody EtatCommandeDto dto) throws Exception {
        if(dto!=null){
            EtatCommande myT = converter.toItem(dto);
            EtatCommande t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                EtatCommandeDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  etatCommande")
    @PutMapping("")
    public ResponseEntity<EtatCommandeDto> update(@RequestBody EtatCommandeDto dto) throws Exception {
        ResponseEntity<EtatCommandeDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            EtatCommande t = service.findById(dto.getId());
            converter.copy(dto,t);
            EtatCommande updated = service.update(t);
            EtatCommandeDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of etatCommande")
    @PostMapping("multiple")
    public ResponseEntity<List<EtatCommandeDto>> delete(@RequestBody List<EtatCommandeDto> dtos) throws Exception {
        ResponseEntity<List<EtatCommandeDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<EtatCommande> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified etatCommande")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) throws Exception {
        ResponseEntity<Long> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (id != null) {
            boolean resultDelete = service.deleteById(id);
            if (resultDelete) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(id, status);
        return res;
    }


    @Operation(summary = "Finds a etatCommande and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<EtatCommandeDto> findWithAssociatedLists(@PathVariable Long id) {
        EtatCommande loaded =  service.findWithAssociatedLists(id);
        EtatCommandeDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds etatCommandes by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<EtatCommandeDto>> findByCriteria(@RequestBody EtatCommandeCriteria criteria) throws Exception {
        ResponseEntity<List<EtatCommandeDto>> res = null;
        List<EtatCommande> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<EtatCommandeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated etatCommandes by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody EtatCommandeCriteria criteria) throws Exception {
        List<EtatCommande> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<EtatCommandeDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets etatCommande data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody EtatCommandeCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<EtatCommandeDto> findDtos(List<EtatCommande> list){
        List<EtatCommandeDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<EtatCommandeDto> getDtoResponseEntity(EtatCommandeDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }






   public EtatCommandeRestAdmin(EtatCommandeAdminService service, EtatCommandeConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final EtatCommandeAdminService service;
    private final EtatCommandeConverter converter;





}
