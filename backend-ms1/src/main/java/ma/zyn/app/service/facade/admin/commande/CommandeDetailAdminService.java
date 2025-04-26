package ma.zyn.app.service.facade.admin.commande;

import java.util.List;
import ma.zyn.app.bean.core.commande.CommandeDetail;
import ma.zyn.app.dao.criteria.core.commande.CommandeDetailCriteria;
import ma.zyn.app.zynerator.service.IService;



public interface CommandeDetailAdminService {



    List<CommandeDetail> findByCommandeId(Long id);
    int deleteByCommandeId(Long id);
    long countByCommandeRef(String ref);
    List<CommandeDetail> findByProduitId(Long id);
    int deleteByProduitId(Long id);
    long countByProduitRef(String ref);




	CommandeDetail create(CommandeDetail t);

    CommandeDetail update(CommandeDetail t);

    List<CommandeDetail> update(List<CommandeDetail> ts,boolean createIfNotExist);

    CommandeDetail findById(Long id);

    CommandeDetail findOrSave(CommandeDetail t);

    CommandeDetail findByReferenceEntity(CommandeDetail t);

    CommandeDetail findWithAssociatedLists(Long id);

    List<CommandeDetail> findAllOptimized();

    List<CommandeDetail> findAll();

    List<CommandeDetail> findByCriteria(CommandeDetailCriteria criteria);

    List<CommandeDetail> findPaginatedByCriteria(CommandeDetailCriteria criteria, int page, int pageSize, String order, String sortField);

    int getDataSize(CommandeDetailCriteria criteria);

    List<CommandeDetail> delete(List<CommandeDetail> ts);

    boolean deleteById(Long id);

    List<List<CommandeDetail>> getToBeSavedAndToBeDeleted(List<CommandeDetail> oldList, List<CommandeDetail> newList);

}
