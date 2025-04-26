
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class ProduitDto extends BaseDto{

    public ref: string;

    public libelle: string;

    public prix: null | number;



    constructor() {
        super();

        this.ref = '';
        this.libelle = '';
        this.prix = null;

        }

}
