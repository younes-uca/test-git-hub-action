package ma.zyn.app.service.impl.admin.commande;



import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.dao.criteria.core.commande.CommandeDetailCriteria;
import ma.zyn.app.dao.facade.core.commande.CommandeDetailDao;
import ma.zyn.app.dao.specification.core.commande.CommandeDetailSpecification;
import ma.zyn.app.service.facade.admin.commande.CommandeDetailAdminService;
import ma.zyn.app.zynerator.service.AbstractServiceImpl;
import static ma.zyn.app.zynerator.util.ListUtil.*;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import ma.zyn.app.zynerator.util.RefelexivityUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ma.zyn.app.service.facade.admin.commande.CommandeAdminService ;
import ma.zyn.app.bean.core.commande.Commande ;
import ma.zyn.app.service.facade.admin.config.ProduitAdminService ;
import ma.zyn.app.bean.core.config.Produit ;

import java.util.List;
@Service
public class CommandeDetailAdminServiceImpl implements CommandeDetailAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CommandeDetail update(CommandeDetail t) {
        CommandeDetail loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{CommandeDetail.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public CommandeDetail findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public CommandeDetail findOrSave(CommandeDetail t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            CommandeDetail result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<CommandeDetail> findAll() {
        return dao.findAll();
    }

    public List<CommandeDetail> findByCriteria(CommandeDetailCriteria criteria) {
        List<CommandeDetail> content = null;
        if (criteria != null) {
            CommandeDetailSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CommandeDetailSpecification constructSpecification(CommandeDetailCriteria criteria) {
        CommandeDetailSpecification mySpecification =  (CommandeDetailSpecification) RefelexivityUtil.constructObjectUsingOneParam(CommandeDetailSpecification.class, criteria);
        return mySpecification;
    }

    public List<CommandeDetail> findPaginatedByCriteria(CommandeDetailCriteria criteria, int page, int pageSize, String order, String sortField) {
        CommandeDetailSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CommandeDetailCriteria criteria) {
        CommandeDetailSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<CommandeDetail> findByCommandeId(Long id){
        return dao.findByCommandeId(id);
    }
    public int deleteByCommandeId(Long id){
        return dao.deleteByCommandeId(id);
    }
    public long countByCommandeRef(String ref){
        return dao.countByCommandeRef(ref);
    }
    public List<CommandeDetail> findByProduitId(Long id){
        return dao.findByProduitId(id);
    }
    public int deleteByProduitId(Long id){
        return dao.deleteByProduitId(id);
    }
    public long countByProduitRef(String ref){
        return dao.countByProduitRef(ref);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            dao.deleteById(id);
        }
        return condition;
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CommandeDetail> delete(List<CommandeDetail> list) {
		List<CommandeDetail> result = new ArrayList();
        if (list != null) {
            for (CommandeDetail t : list) {
                if(dao.findById(t.getId()).isEmpty()){
					result.add(t);
				}else{
                    dao.deleteById(t.getId());
                }
            }
        }
		return result;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public CommandeDetail create(CommandeDetail t) {
        CommandeDetail loaded = findByReferenceEntity(t);
        CommandeDetail saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public CommandeDetail findWithAssociatedLists(Long id){
        CommandeDetail result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<CommandeDetail> update(List<CommandeDetail> ts, boolean createIfNotExist) {
        List<CommandeDetail> result = new ArrayList<>();
        if (ts != null) {
            for (CommandeDetail t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    CommandeDetail loadedItem = dao.findById(t.getId()).orElse(null);
                    if (isEligibleForCreateOrUpdate(createIfNotExist, t, loadedItem)) {
                        dao.save(t);
                    } else {
                        result.add(t);
                    }
                }
            }
        }
        return result;
    }


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, CommandeDetail t, CommandeDetail loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public CommandeDetail findByReferenceEntity(CommandeDetail t) {
        return t == null || t.getId() == null ? null : findById(t.getId());
    }
    public void findOrSaveAssociatedObject(CommandeDetail t){
        if( t != null) {
            t.setCommande(commandeService.findOrSave(t.getCommande()));
            t.setProduit(produitService.findOrSave(t.getProduit()));
        }
    }



    public List<CommandeDetail> findAllOptimized() {
        return dao.findAll();
    }

    @Override
    public List<List<CommandeDetail>> getToBeSavedAndToBeDeleted(List<CommandeDetail> oldList, List<CommandeDetail> newList) {
        List<List<CommandeDetail>> result = new ArrayList<>();
        List<CommandeDetail> resultDelete = new ArrayList<>();
        List<CommandeDetail> resultUpdateOrSave = new ArrayList<>();
        if (isEmpty(oldList) && isNotEmpty(newList)) {
            resultUpdateOrSave.addAll(newList);
        } else if (isEmpty(newList) && isNotEmpty(oldList)) {
            resultDelete.addAll(oldList);
        } else if (isNotEmpty(newList) && isNotEmpty(oldList)) {
			extractToBeSaveOrDelete(oldList, newList, resultUpdateOrSave, resultDelete);
        }
        result.add(resultUpdateOrSave);
        result.add(resultDelete);
        return result;
    }

    private void extractToBeSaveOrDelete(List<CommandeDetail> oldList, List<CommandeDetail> newList, List<CommandeDetail> resultUpdateOrSave, List<CommandeDetail> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                CommandeDetail myOld = oldList.get(i);
                CommandeDetail t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                CommandeDetail myNew = newList.get(i);
                CommandeDetail t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}







    @Autowired
    private CommandeAdminService commandeService ;
    @Autowired
    private ProduitAdminService produitService ;

    public CommandeDetailAdminServiceImpl(CommandeDetailDao dao) {
        this.dao = dao;
    }

    private CommandeDetailDao dao;
}
