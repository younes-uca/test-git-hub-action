import {EtatCommandeDto} from '../config/EtatCommande.model';
import {CommandeDetailDto} from './CommandeDetail.model';

import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';


export class CommandeDto extends BaseDto{

    public ref: string;

    public montant: null | number;

   public dateCommande: Date;

    public etatCommande: EtatCommandeDto ;
     public commandeDetails: Array<CommandeDetailDto>;


    constructor() {
        super();

        this.ref = '';
        this.montant = null;
        this.dateCommande = null;
        this.commandeDetails = new Array<CommandeDetailDto>();

        }

}
