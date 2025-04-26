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

import ma.zyn.app.bean.core.commande.Commande;
import ma.zyn.app.dao.criteria.core.commande.CommandeCriteria;
import ma.zyn.app.service.facade.admin.commande.CommandeAdminService;
import ma.zyn.app.ws.converter.commande.CommandeConverter;
import ma.zyn.app.ws.dto.commande.CommandeDto;
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
@RequestMapping("/api/admin/commande/")
public class CommandeRestAdmin {




    @Operation(summary = "Finds a list of all commandes")
    @GetMapping("")
    public ResponseEntity<List<CommandeDto>> findAll() throws Exception {
        ResponseEntity<List<CommandeDto>> res = null;
        List<Commande> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
            converter.initObject(true);
        List<CommandeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all commandes")
    @GetMapping("optimized")
    public ResponseEntity<List<CommandeDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<CommandeDto>> res = null;
        List<Commande> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<CommandeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a commande by id")
    @GetMapping("id/{id}")
    public ResponseEntity<CommandeDto> findById(@PathVariable Long id) {
        Commande t = service.findById(id);
        if (t != null) {
            converter.init(true);
            CommandeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a commande by ref")
    @GetMapping("ref/{ref}")
    public ResponseEntity<CommandeDto> findByRef(@PathVariable String ref) {
	    Commande t = service.findByReferenceEntity(new Commande(ref));
        if (t != null) {
            converter.init(true);
            CommandeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  commande")
    @PostMapping("")
    public ResponseEntity<CommandeDto> save(@RequestBody CommandeDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Commande myT = converter.toItem(dto);
            Commande t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                CommandeDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  commande")
    @PutMapping("")
    public ResponseEntity<CommandeDto> update(@RequestBody CommandeDto dto) throws Exception {
        ResponseEntity<CommandeDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Commande t = service.findById(dto.getId());
            converter.copy(dto,t);
            Commande updated = service.update(t);
            CommandeDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of commande")
    @PostMapping("multiple")
    public ResponseEntity<List<CommandeDto>> delete(@RequestBody List<CommandeDto> dtos) throws Exception {
        ResponseEntity<List<CommandeDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Commande> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified commande")
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


    @Operation(summary = "Finds a commande and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<CommandeDto> findWithAssociatedLists(@PathVariable Long id) {
        Commande loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        CommandeDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds commandes by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<CommandeDto>> findByCriteria(@RequestBody CommandeCriteria criteria) throws Exception {
        ResponseEntity<List<CommandeDto>> res = null;
        List<Commande> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initList(false);
        converter.initObject(true);
        List<CommandeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated commandes by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody CommandeCriteria criteria) throws Exception {
        List<Commande> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initList(false);
        converter.initObject(true);
        List<CommandeDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets commande data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody CommandeCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<CommandeDto> findDtos(List<Commande> list){
        converter.initList(false);
        converter.initObject(true);
        List<CommandeDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<CommandeDto> getDtoResponseEntity(CommandeDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }






   public CommandeRestAdmin(CommandeAdminService service, CommandeConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final CommandeAdminService service;
    private final CommandeConverter converter;





}
