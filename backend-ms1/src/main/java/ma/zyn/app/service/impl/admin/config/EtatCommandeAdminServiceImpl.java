package ma.zyn.app.service.impl.admin.config;



import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.dao.criteria.core.config.EtatCommandeCriteria;
import ma.zyn.app.dao.facade.core.config.EtatCommandeDao;
import ma.zyn.app.dao.specification.core.config.EtatCommandeSpecification;
import ma.zyn.app.service.facade.admin.config.EtatCommandeAdminService;
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


import java.util.List;
@Service
public class EtatCommandeAdminServiceImpl implements EtatCommandeAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public EtatCommande update(EtatCommande t) {
        EtatCommande loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{EtatCommande.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public EtatCommande findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public EtatCommande findOrSave(EtatCommande t) {
        if (t != null) {
            EtatCommande result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<EtatCommande> findAll() {
        return dao.findAll();
    }

    public List<EtatCommande> findByCriteria(EtatCommandeCriteria criteria) {
        List<EtatCommande> content = null;
        if (criteria != null) {
            EtatCommandeSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private EtatCommandeSpecification constructSpecification(EtatCommandeCriteria criteria) {
        EtatCommandeSpecification mySpecification =  (EtatCommandeSpecification) RefelexivityUtil.constructObjectUsingOneParam(EtatCommandeSpecification.class, criteria);
        return mySpecification;
    }

    public List<EtatCommande> findPaginatedByCriteria(EtatCommandeCriteria criteria, int page, int pageSize, String order, String sortField) {
        EtatCommandeSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(EtatCommandeCriteria criteria) {
        EtatCommandeSpecification mySpecification = constructSpecification(criteria);
        mySpecification.setDistinct(true);
        return ((Long) dao.count(mySpecification)).intValue();
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
    public List<EtatCommande> delete(List<EtatCommande> list) {
		List<EtatCommande> result = new ArrayList();
        if (list != null) {
            for (EtatCommande t : list) {
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
    public EtatCommande create(EtatCommande t) {
        EtatCommande loaded = findByReferenceEntity(t);
        EtatCommande saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public EtatCommande findWithAssociatedLists(Long id){
        EtatCommande result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<EtatCommande> update(List<EtatCommande> ts, boolean createIfNotExist) {
        List<EtatCommande> result = new ArrayList<>();
        if (ts != null) {
            for (EtatCommande t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    EtatCommande loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, EtatCommande t, EtatCommande loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public EtatCommande findByReferenceEntity(EtatCommande t){
        return t==null? null : dao.findByCode(t.getCode());
    }



    public List<EtatCommande> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<EtatCommande>> getToBeSavedAndToBeDeleted(List<EtatCommande> oldList, List<EtatCommande> newList) {
        List<List<EtatCommande>> result = new ArrayList<>();
        List<EtatCommande> resultDelete = new ArrayList<>();
        List<EtatCommande> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<EtatCommande> oldList, List<EtatCommande> newList, List<EtatCommande> resultUpdateOrSave, List<EtatCommande> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                EtatCommande myOld = oldList.get(i);
                EtatCommande t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                EtatCommande myNew = newList.get(i);
                EtatCommande t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}








    public EtatCommandeAdminServiceImpl(EtatCommandeDao dao) {
        this.dao = dao;
    }

    private EtatCommandeDao dao;
}
