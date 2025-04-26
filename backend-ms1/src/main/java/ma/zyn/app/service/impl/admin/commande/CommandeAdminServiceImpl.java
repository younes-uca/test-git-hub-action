package ma.zyn.app.service.impl.admin.commande;



import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.commande.Commande;
import ma.zyn.app.dao.criteria.core.commande.CommandeCriteria;
import ma.zyn.app.dao.facade.core.commande.CommandeDao;
import ma.zyn.app.dao.specification.core.commande.CommandeSpecification;
import ma.zyn.app.service.facade.admin.commande.CommandeAdminService;
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

import ma.zyn.app.service.facade.admin.config.EtatCommandeAdminService ;
import ma.zyn.app.bean.core.config.EtatCommande ;
import ma.zyn.app.service.facade.admin.commande.CommandeDetailAdminService ;
import ma.zyn.app.bean.core.commande.CommandeDetail ;

import java.util.List;
@Service
public class CommandeAdminServiceImpl implements CommandeAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Commande update(Commande t) {
        Commande loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Commande.class.getSimpleName(), t.getId().toString()});
        } else {
            updateWithAssociatedLists(t);
            dao.save(t);
            return loadedItem;
        }
    }

    public Commande findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Commande findOrSave(Commande t) {
        if (t != null) {
            findOrSaveAssociatedObject(t);
            Commande result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Commande> findAll() {
        return dao.findAll();
    }

    public List<Commande> findByCriteria(CommandeCriteria criteria) {
        List<Commande> content = null;
        if (criteria != null) {
            CommandeSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private CommandeSpecification constructSpecification(CommandeCriteria criteria) {
        CommandeSpecification mySpecification =  (CommandeSpecification) RefelexivityUtil.constructObjectUsingOneParam(CommandeSpecification.class, criteria);
        return mySpecification;
    }

    public List<Commande> findPaginatedByCriteria(CommandeCriteria criteria, int page, int pageSize, String order, String sortField) {
        CommandeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(CommandeCriteria criteria) {
        CommandeSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
    }

    public List<Commande> findByEtatCommandeCode(String code){
        return dao.findByEtatCommandeCode(code);
    }
    public List<Commande> findByEtatCommandeId(Long id){
        return dao.findByEtatCommandeId(id);
    }
    public int deleteByEtatCommandeCode(String code){
        return dao.deleteByEtatCommandeCode(code);
    }
    public int deleteByEtatCommandeId(Long id){
        return dao.deleteByEtatCommandeId(id);
    }
    public long countByEtatCommandeCode(String code){
        return dao.countByEtatCommandeCode(code);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean deleteById(Long id) {
        boolean condition = (id != null);
        if (condition) {
            deleteAssociatedLists(id);
            dao.deleteById(id);
        }
        return condition;
    }

    public void deleteAssociatedLists(Long id) {
        commandeDetailService.deleteByCommandeId(id);
    }




    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Commande> delete(List<Commande> list) {
		List<Commande> result = new ArrayList();
        if (list != null) {
            for (Commande t : list) {
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
    public Commande create(Commande t) {
        Commande loaded = findByReferenceEntity(t);
        Commande saved;
        if (loaded == null) {
            saved = dao.save(t);
            if (t.getCommandeDetails() != null) {
                t.getCommandeDetails().forEach(element-> {
                    element.setCommande(saved);
                    commandeDetailService.create(element);
                });
            }
        }else {
            saved = null;
        }
        return saved;
    }

    public Commande findWithAssociatedLists(Long id){
        Commande result = dao.findById(id).orElse(null);
        if(result!=null && result.getId() != null) {
            result.setCommandeDetails(commandeDetailService.findByCommandeId(id));
        }
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Commande> update(List<Commande> ts, boolean createIfNotExist) {
        List<Commande> result = new ArrayList<>();
        if (ts != null) {
            for (Commande t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Commande loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Commande t, Commande loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }

    public void updateWithAssociatedLists(Commande commande){
    if(commande !=null && commande.getId() != null){
        List<List<CommandeDetail>> resultCommandeDetails= commandeDetailService.getToBeSavedAndToBeDeleted(commandeDetailService.findByCommandeId(commande.getId()),commande.getCommandeDetails());
            commandeDetailService.delete(resultCommandeDetails.get(1));
        emptyIfNull(resultCommandeDetails.get(0)).forEach(e -> e.setCommande(commande));
        commandeDetailService.update(resultCommandeDetails.get(0),true);
        }
    }








    public Commande findByReferenceEntity(Commande t){
        return t==null? null : dao.findByRef(t.getRef());
    }
    public void findOrSaveAssociatedObject(Commande t){
        if( t != null) {
            t.setEtatCommande(etatCommandeService.findOrSave(t.getEtatCommande()));
        }
    }



    public List<Commande> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Commande>> getToBeSavedAndToBeDeleted(List<Commande> oldList, List<Commande> newList) {
        List<List<Commande>> result = new ArrayList<>();
        List<Commande> resultDelete = new ArrayList<>();
        List<Commande> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Commande> oldList, List<Commande> newList, List<Commande> resultUpdateOrSave, List<Commande> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Commande myOld = oldList.get(i);
                Commande t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Commande myNew = newList.get(i);
                Commande t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}







    @Autowired
    private EtatCommandeAdminService etatCommandeService ;
    @Autowired
    private CommandeDetailAdminService commandeDetailService ;

    public CommandeAdminServiceImpl(CommandeDao dao) {
        this.dao = dao;
    }

    private CommandeDao dao;
}
