package ma.zyn.app.service.facade.admin.config;

import java.util.List;
import ma.zyn.app.bean.core.config.Produit;
import ma.zyn.app.dao.criteria.core.config.ProduitCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface ProduitAdminService {







	Produit create(Produit t);

    Produit update(Produit t);

    List<Produit> update(List<Produit> ts,boolean createIfNotExist);

    Produit findById(Long id);

    Produit findOrSave(Produit t);

    Produit findByReferenceEntity(Produit t);

    Produit findWithAssociatedLists(Long id);

    List<Produit> findAllOptimized();

    List<Produit> findAll();

    List<Produit> findByCriteria(ProduitCriteria criteria);

    List<Produit> findPaginatedByCriteria(ProduitCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(ProduitCriteria criteria);

    List<Produit> delete(List<Produit> ts);

    boolean deleteById(Long id);

    List<List<Produit>> getToBeSavedAndToBeDeleted(List<Produit> oldList, List<Produit> newList);

}
