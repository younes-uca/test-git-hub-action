package  ma.zyn.app.ws.facade.admin.commande;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.dao.criteria.core.commande.CommandeDetailCriteria;
import ma.zyn.app.service.facade.admin.commande.CommandeDetailAdminService;
import ma.zyn.app.ws.converter.commande.CommandeDetailConverter;
import ma.zyn.app.ws.dto.commande.CommandeDetailDto;
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
@RequestMapping("/api/admin/commandeDetail/")
public class CommandeDetailRestAdmin {




    @Operation(summary = "Finds a list of all commandeDetails")
    @GetMapping("")
    public ResponseEntity<List<CommandeDetailDto>> findAll() throws Exception {
        ResponseEntity<List<CommandeDetailDto>> res = null;
        List<CommandeDetail> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<CommandeDetailDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a commandeDetail by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CommandeDetailDto> findById(@PathVariable Long id) {
        CommandeDetail t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CommandeDetailDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  commandeDetail")
    @PostMapping("")
    public ResponseEntity<CommandeDetailDto> save(@RequestBody CommandeDetailDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            CommandeDetail myT = converter.toItem(dto);
            CommandeDetail t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CommandeDetailDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  commandeDetail")
    @PutMapping("")
    public ResponseEntity<CommandeDetailDto> update(@RequestBody CommandeDetailDto dto) throws Exception {
        ResponseEntity<CommandeDetailDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            CommandeDetail t = service.findById(dto.getId());
            converter.copy(dto,t);
            CommandeDetail updated = service.update(t);
            CommandeDetailDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of commandeDetail")
    @PostMapping("multiple")
    public ResponseEntity<List<CommandeDetailDto>> delete(@RequestBody List<CommandeDetailDto> dtos) throws Exception {
        ResponseEntity<List<CommandeDetailDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<CommandeDetail> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified commandeDetail")
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

    @Operation(summary = "find by commande id")
    @GetMapping("commande/id/{id}")
    public List<CommandeDetailDto> findByCommandeId(@PathVariable Long id){
        return findDtos(service.findByCommandeId(id));
    }
    @Operation(summary = "delete by commande id")
    @DeleteMapping("commande/id/{id}")
    public int deleteByCommandeId(@PathVariable Long id){
        return service.deleteByCommandeId(id);
    }
    @Operation(summary = "find by produit id")
    @GetMapping("produit/id/{id}")
    public List<CommandeDetailDto> findByProduitId(@PathVariable Long id){
        return findDtos(service.findByProduitId(id));
    }
    @Operation(summary = "delete by produit id")
    @DeleteMapping("produit/id/{id}")
    public int deleteByProduitId(@PathVariable Long id){
        return service.deleteByProduitId(id);
    }

    @Operation(summary = "Finds a commandeDetail and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CommandeDetailDto> findWithAssociatedLists(@PathVariable Long id) {
        CommandeDetail loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CommandeDetailDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds commandeDetails by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CommandeDetailDto>> findByCriteria(@RequestBody CommandeDetailCriteria criteria) throws Exception {
        ResponseEntity<List<CommandeDetailDto>> res = null;
        List<CommandeDetail> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<CommandeDetailDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated commandeDetails by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CommandeDetailCriteria criteria) throws Exception {
        List<CommandeDetail> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<CommandeDetailDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets commandeDetail data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CommandeDetailCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CommandeDetailDto> findDtos(List<CommandeDetail> list){
        converter.initObject(true);
        List<CommandeDetailDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CommandeDetailDto> getDtoResponseEntity(CommandeDetailDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }






   public CommandeDetailRestAdmin(CommandeDetailAdminService service, CommandeDetailConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CommandeDetailAdminService service;
    private final CommandeDetailConverter converter;





}
