package ma.zyn.app.service.impl.admin.config;



import ma.zyn.app.zynerator.exception.EntityNotFoundException;
import ma.zyn.app.bean.core.config.Produit;
import ma.zyn.app.dao.criteria.core.config.ProduitCriteria;
import ma.zyn.app.dao.facade.core.config.ProduitDao;
import ma.zyn.app.dao.specification.core.config.ProduitSpecification;
import ma.zyn.app.service.facade.admin.config.ProduitAdminService;
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
public class ProduitAdminServiceImpl implements ProduitAdminService {

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public Produit update(Produit t) {
        Produit loadedItem = dao.findById(t.getId()).orElse(null);
        if (loadedItem == null) {
            throw new EntityNotFoundException("errors.notFound", new String[]{Produit.class.getSimpleName(), t.getId().toString()});
        } else {
            dao.save(t);
            return loadedItem;
        }
    }

    public Produit findById(Long id) {
        return dao.findById(id).orElse(null);
    }


    public Produit findOrSave(Produit t) {
        if (t != null) {
            Produit result = findByReferenceEntity(t);
            if (result == null) {
                return dao.save(t);
            } else {
                return result;
            }
        }
        return null;
    }

    public List<Produit> findAll() {
        return dao.findAll();
    }

    public List<Produit> findByCriteria(ProduitCriteria criteria) {
        List<Produit> content = null;
        if (criteria != null) {
            ProduitSpecification mySpecification = constructSpecification(criteria);
            content = dao.findAll(mySpecification);
        } else {
            content = dao.findAll();
        }
        return content;

    }


    private ProduitSpecification constructSpecification(ProduitCriteria criteria) {
        ProduitSpecification mySpecification =  (ProduitSpecification) RefelexivityUtil.constructObjectUsingOneParam(ProduitSpecification.class, criteria);
        return mySpecification;
    }

    public List<Produit> findPaginatedByCriteria(ProduitCriteria criteria, int page, int pageSize, String order, String sortField) {
        ProduitSpecification mySpecification = constructSpecification(criteria);
        order = (order != null && !order.isEmpty()) ? order : "desc";
        sortField = (sortField != null && !sortField.isEmpty()) ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);
        return dao.findAll(mySpecification, pageable).getContent();
    }

    public int getDataSize(ProduitCriteria criteria) {
        ProduitSpecification mySpecification = constructSpecification(criteria);
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
    public List<Produit> delete(List<Produit> list) {
		List<Produit> result = new ArrayList();
        if (list != null) {
            for (Produit t : list) {
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
    public Produit create(Produit t) {
        Produit loaded = findByReferenceEntity(t);
        Produit saved;
        if (loaded == null) {
            saved = dao.save(t);
        }else {
            saved = null;
        }
        return saved;
    }

    public Produit findWithAssociatedLists(Long id){
        Produit result = dao.findById(id).orElse(null);
        return result;
    }

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public List<Produit> update(List<Produit> ts, boolean createIfNotExist) {
        List<Produit> result = new ArrayList<>();
        if (ts != null) {
            for (Produit t : ts) {
                if (t.getId() == null) {
                    dao.save(t);
                } else {
                    Produit loadedItem = dao.findById(t.getId()).orElse(null);
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


    private boolean isEligibleForCreateOrUpdate(boolean createIfNotExist, Produit t, Produit loadedItem) {
        boolean eligibleForCreateCrud = t.getId() == null;
        boolean eligibleForCreate = (createIfNotExist && (t.getId() == null || loadedItem == null));
        boolean eligibleForUpdate = (t.getId() != null && loadedItem != null);
        return (eligibleForCreateCrud || eligibleForCreate || eligibleForUpdate);
    }









    public Produit findByReferenceEntity(Produit t){
        return t==null? null : dao.findByRef(t.getRef());
    }



    public List<Produit> findAllOptimized() {
        return dao.findAllOptimized();
    }

    @Override
    public List<List<Produit>> getToBeSavedAndToBeDeleted(List<Produit> oldList, List<Produit> newList) {
        List<List<Produit>> result = new ArrayList<>();
        List<Produit> resultDelete = new ArrayList<>();
        List<Produit> resultUpdateOrSave = new ArrayList<>();
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

    private void extractToBeSaveOrDelete(List<Produit> oldList, List<Produit> newList, List<Produit> resultUpdateOrSave, List<Produit> resultDelete) {
		for (int i = 0; i < oldList.size(); i++) {
                Produit myOld = oldList.get(i);
                Produit t = newList.stream().filter(e -> myOld.equals(e)).findFirst().orElse(null);
                if (t != null) {
                    resultUpdateOrSave.add(t); // update
                } else {
                    resultDelete.add(myOld);
                }
            }
            for (int i = 0; i < newList.size(); i++) {
                Produit myNew = newList.get(i);
                Produit t = oldList.stream().filter(e -> myNew.equals(e)).findFirst().orElse(null);
                if (t == null) {
                    resultUpdateOrSave.add(myNew); // create
                }
            }
	}








    public ProduitAdminServiceImpl(ProduitDao dao) {
        this.dao = dao;
    }

    private ProduitDao dao;
}
