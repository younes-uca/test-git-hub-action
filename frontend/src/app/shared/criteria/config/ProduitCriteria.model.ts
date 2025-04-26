
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class ProduitCriteria extends BaseCriteria {

    public id: number;
    public ref: string;
    public refLike: string;
    public libelle: string;
    public libelleLike: string;
     public prix: number;
     public prixMin: number;
     public prixMax: number;

}
