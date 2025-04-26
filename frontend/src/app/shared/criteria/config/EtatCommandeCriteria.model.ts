
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';

export class EtatCommandeCriteria extends BaseCriteria {

    public id: number;
    public libelle: string;
    public libelleLike: string;
    public code: string;
    public codeLike: string;
    public style: string;
    public styleLike: string;
    public description: string;
    public descriptionLike: string;

}
