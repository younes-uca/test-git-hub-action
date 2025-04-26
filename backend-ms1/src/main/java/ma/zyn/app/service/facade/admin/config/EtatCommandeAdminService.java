package ma.zyn.app.service.facade.admin.config;

import java.util.List;
import ma.zyn.app.bean.core.config.EtatCommande;
import ma.zyn.app.dao.criteria.core.config.EtatCommandeCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface EtatCommandeAdminService {







	EtatCommande create(EtatCommande t);

    EtatCommande update(EtatCommande t);

    List<EtatCommande> update(List<EtatCommande> ts,boolean createIfNotExist);

    EtatCommande findById(Long id);

    EtatCommande findOrSave(EtatCommande t);

    EtatCommande findByReferenceEntity(EtatCommande t);

    EtatCommande findWithAssociatedLists(Long id);

    List<EtatCommande> findAllOptimized();

    List<EtatCommande> findAll();

    List<EtatCommande> findByCriteria(EtatCommandeCriteria criteria);

    List<EtatCommande> findPaginatedByCriteria(EtatCommandeCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(EtatCommandeCriteria criteria);

    List<EtatCommande> delete(List<EtatCommande> ts);

    boolean deleteById(Long id);

    List<List<EtatCommande>> getToBeSavedAndToBeDeleted(List<EtatCommande> oldList, List<EtatCommande> newList);

}
