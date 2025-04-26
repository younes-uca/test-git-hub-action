
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class EtatCommandeDto extends BaseDto{

    public libelle: string;

    public code: string;

    public style: string;

    public description: string;



    constructor() {
        super();

        this.libelle = '';
        this.code = '';
        this.style = '';
        this.description = '';

        }

}
