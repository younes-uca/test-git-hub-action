import {CommandeCriteria} from './CommandeCriteria.model';
import {ProduitCriteria} from '../config/ProduitCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class CommandeDetailCriteria extends BaseCriteria {

    public id: number;
     public prix: number;
     public prixMin: number;
     public prixMax: number;
     public qte: number;
     public qteMin: number;
     public qteMax: number;
  public commande: CommandeCriteria ;
  public commandes: Array<CommandeCriteria> ;
  public produit: ProduitCriteria ;
  public produits: Array<ProduitCriteria> ;

}
