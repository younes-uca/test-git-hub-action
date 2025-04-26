import {EtatCommandeCriteria} from '../config/EtatCommandeCriteria.model';
import {CommandeDetailCriteria} from './CommandeDetailCriteria.model';

import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class CommandeCriteria extends BaseCriteria {

    public id: number;
    public ref: string;
    public refLike: string;
     public montant: number;
     public montantMin: number;
     public montantMax: number;
    public dateCommande: Date;
    public dateCommandeFrom: Date;
    public dateCommandeTo: Date;
      public commandeDetails: Array<CommandeDetailCriteria>;

}
