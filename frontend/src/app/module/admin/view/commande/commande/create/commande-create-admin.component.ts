import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {CommandeAdminService} from 'src/app/shared/service/admin/commande/CommandeAdmin.service';
import {CommandeDto} from 'src/app/shared/model/commande/Commande.model';
import {CommandeCriteria} from 'src/app/shared/criteria/commande/CommandeCriteria.model';
import {EtatCommandeDto} from 'src/app/shared/model/config/EtatCommande.model';
import {EtatCommandeAdminService} from 'src/app/shared/service/admin/config/EtatCommandeAdmin.service';
import {CommandeDetailDto} from 'src/app/shared/model/commande/CommandeDetail.model';
import {CommandeDetailAdminService} from 'src/app/shared/service/admin/commande/CommandeDetailAdmin.service';
import {ProduitDto} from 'src/app/shared/model/config/Produit.model';
import {ProduitAdminService} from 'src/app/shared/service/admin/config/ProduitAdmin.service';
@Component({
  selector: 'app-commande-create-admin',
  standalone: false,
  templateUrl: './commande-create-admin.component.html'
})
export class CommandeCreateAdminComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;
    protected commandeDetailsIndex = -1;

    private _commandeDetailsElement = new CommandeDetailDto();


   private _validCommandeRef = true;
   private _validCommandeMontant = true;
   private _validCommandeEtatCommande = true;
    private _validEtatCommandeLibelle = true;
    private _validEtatCommandeCode = true;
    private _validEtatCommandeStyle = true;
    private _validCommandeDetailsPrix = true;
    private _validCommandeDetailsQte = true;
    private _validCommandeDetailsCommande = true;
    private _validCommandeDetailsProduit = true;

	constructor(private service: CommandeAdminService , private etatCommandeService: EtatCommandeAdminService, private commandeDetailService: CommandeDetailAdminService, private produitService: ProduitAdminService, @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.commandeDetailsElement.produit = new ProduitDto();
        this.produitService.findAll().subscribe((data) => this.produits = data);
        this.etatCommandeService.findAll().subscribe((data) => this.etatCommandes = data);
    }



    public save(): void {
        this.submitted = true;
        this.validateForm();
        if (this.errorMessages.length === 0) {
            this.saveWithShowOption(false);
        } else {
            this.messageService.add({severity: 'error',summary: 'Erreurs',detail: 'Merci de corrigé les erreurs sur le formulaire'});
        }
    }

    public saveWithShowOption(showList: boolean) {
        this.service.save().subscribe(item => {
            if (item != null) {
                this.items.push({...item});
                this.createDialog = false;
                this.submitted = false;
                this.item = new CommandeDto();
            } else {
                this.messageService.add({severity: 'error', summary: 'Erreurs', detail: 'Element existant'});
            }

        }, error => {
            console.log(error);
        });
    }


    public hideCreateDialog() {
        this.createDialog = false;
        this.setValidation(true);
    }



    validateCommandeDetails(){
        this.errorMessages = new Array();
        this.validateCommandeDetailsPrix();
        this.validateCommandeDetailsQte();
        this.validateCommandeDetailsCommande();
        this.validateCommandeDetailsProduit();
    }


    public  setValidation(value: boolean){
        this.validCommandeRef = value;
        this.validCommandeMontant = value;
        this.validCommandeEtatCommande = value;
        this.validCommandeDetailsPrix = value;
        this.validCommandeDetailsQte = value;
        this.validCommandeDetailsCommande = value;
        this.validCommandeDetailsProduit = value;
    }

    public addCommandeDetails() {
        if( this.item.commandeDetails == null )
            this.item.commandeDetails = new Array<CommandeDetailDto>();

       this.validateCommandeDetails();

       if (this.errorMessages.length === 0) {
            if (this.commandeDetailsIndex == -1){
                this.item.commandeDetails.push({... this.commandeDetailsElement});
            }else {
                this.item.commandeDetails[this.commandeDetailsIndex] =this.commandeDetailsElement;
            }
              this.commandeDetailsElement = new CommandeDetailDto();
              this.commandeDetailsIndex = -1;
       }else{
           this.messageService.add({severity: 'error',summary: 'Erreurs',detail: 'Merci de corrigé les erreurs suivant : ' + this.errorMessages});
       }
    }

    public deleteCommandeDetails(p: CommandeDetailDto, index: number) {
        this.item.commandeDetails.splice(index, 1);
    }

    public editCommandeDetails(p: CommandeDetailDto, index: number) {
        this.commandeDetailsElement = {... p};
        this.commandeDetailsIndex = index;
        this.activeTab = 0;
    }


    public  validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateCommandeRef();
        this.validateCommandeMontant();
        this.validateCommandeEtatCommande();
    }

    public validateCommandeRef(){
        if (this.stringUtilService.isEmpty(this.item.ref)) {
        this.errorMessages.push('Ref non valide');
        this.validCommandeRef = false;
        } else {
            this.validCommandeRef = true;
        }
    }
    public validateCommandeMontant(){
        if (this.stringUtilService.isEmpty(this.item.montant)) {
        this.errorMessages.push('Montant non valide');
        this.validCommandeMontant = false;
        } else {
            this.validCommandeMontant = true;
        }
    }
    public validateCommandeEtatCommande(){
        if (this.stringUtilService.isEmpty(this.item.etatCommande)) {
        this.errorMessages.push('Etat commande non valide');
        this.validCommandeEtatCommande = false;
        } else {
            this.validCommandeEtatCommande = true;
        }
    }

    public validateCommandeDetailsPrix(){
        if (this.commandeDetailsElement.prix == null) {
            this.errorMessages.push('Prix de la commandeDetail est  invalide');
            this.validCommandeDetailsPrix = false;
        } else {
            this.validCommandeDetailsPrix = true;
        }
    }
    public validateCommandeDetailsQte(){
        if (this.commandeDetailsElement.qte == null) {
            this.errorMessages.push('Qte de la commandeDetail est  invalide');
            this.validCommandeDetailsQte = false;
        } else {
            this.validCommandeDetailsQte = true;
        }
    }
    public validateCommandeDetailsCommande(){
        if (this.commandeDetailsElement.commande == null) {
            this.errorMessages.push('Commande de la commandeDetail est  invalide');
            this.validCommandeDetailsCommande = false;
        } else {
            this.validCommandeDetailsCommande = true;
        }
    }
    public validateCommandeDetailsProduit(){
        if (this.commandeDetailsElement.produit == null) {
            this.errorMessages.push('Produit de la commandeDetail est  invalide');
            this.validCommandeDetailsProduit = false;
        } else {
            this.validCommandeDetailsProduit = true;
        }
    }


    get produit(): ProduitDto {
        return this.produitService.item;
    }
    set produit(value: ProduitDto) {
        this.produitService.item = value;
    }
    get produits(): Array<ProduitDto> {
        return this.produitService.items;
    }
    set produits(value: Array<ProduitDto>) {
        this.produitService.items = value;
    }
    get createProduitDialog(): boolean {
        return this.produitService.createDialog;
    }
    set createProduitDialog(value: boolean) {
        this.produitService.createDialog= value;
    }
    get etatCommande(): EtatCommandeDto {
        return this.etatCommandeService.item;
    }
    set etatCommande(value: EtatCommandeDto) {
        this.etatCommandeService.item = value;
    }
    get etatCommandes(): Array<EtatCommandeDto> {
        return this.etatCommandeService.items;
    }
    set etatCommandes(value: Array<EtatCommandeDto>) {
        this.etatCommandeService.items = value;
    }
    get createEtatCommandeDialog(): boolean {
        return this.etatCommandeService.createDialog;
    }
    set createEtatCommandeDialog(value: boolean) {
        this.etatCommandeService.createDialog= value;
    }



    get validCommandeRef(): boolean {
        return this._validCommandeRef;
    }

    set validCommandeRef(value: boolean) {
         this._validCommandeRef = value;
    }
    get validCommandeMontant(): boolean {
        return this._validCommandeMontant;
    }

    set validCommandeMontant(value: boolean) {
         this._validCommandeMontant = value;
    }
    get validCommandeEtatCommande(): boolean {
        return this._validCommandeEtatCommande;
    }

    set validCommandeEtatCommande(value: boolean) {
         this._validCommandeEtatCommande = value;
    }

    get validEtatCommandeLibelle(): boolean {
        return this._validEtatCommandeLibelle;
    }
    set validEtatCommandeLibelle(value: boolean) {
        this._validEtatCommandeLibelle = value;
    }
    get validEtatCommandeCode(): boolean {
        return this._validEtatCommandeCode;
    }
    set validEtatCommandeCode(value: boolean) {
        this._validEtatCommandeCode = value;
    }
    get validEtatCommandeStyle(): boolean {
        return this._validEtatCommandeStyle;
    }
    set validEtatCommandeStyle(value: boolean) {
        this._validEtatCommandeStyle = value;
    }
    get validCommandeDetailsPrix(): boolean {
        return this._validCommandeDetailsPrix;
    }
    set validCommandeDetailsPrix(value: boolean) {
        this._validCommandeDetailsPrix = value;
    }
    get validCommandeDetailsQte(): boolean {
        return this._validCommandeDetailsQte;
    }
    set validCommandeDetailsQte(value: boolean) {
        this._validCommandeDetailsQte = value;
    }
    get validCommandeDetailsCommande(): boolean {
        return this._validCommandeDetailsCommande;
    }
    set validCommandeDetailsCommande(value: boolean) {
        this._validCommandeDetailsCommande = value;
    }
    get validCommandeDetailsProduit(): boolean {
        return this._validCommandeDetailsProduit;
    }
    set validCommandeDetailsProduit(value: boolean) {
        this._validCommandeDetailsProduit = value;
    }

    get commandeDetailsElement(): CommandeDetailDto {
        if( this._commandeDetailsElement == null )
            this._commandeDetailsElement = new CommandeDetailDto();
        return this._commandeDetailsElement;
    }

    set commandeDetailsElement(value: CommandeDetailDto) {
        this._commandeDetailsElement = value;
    }

    get items(): Array<CommandeDto> {
        return this.service.items;
    }

    set items(value: Array<CommandeDto>) {
        this.service.items = value;
    }

    get item(): CommandeDto {
        return this.service.item;
    }

    set item(value: CommandeDto) {
        this.service.item = value;
    }

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get criteria(): CommandeCriteria {
        return this.service.criteria;
    }

    set criteria(value: CommandeCriteria) {
        this.service.criteria = value;
    }

    get dateFormat() {
        return environment.dateFormatCreate;
    }

    get dateFormatColumn() {
        return environment.dateFormatCreate;
    }

    get submitted(): boolean {
        return this._submitted;
    }

    set submitted(value: boolean) {
        this._submitted = value;
    }

    get errorMessages(): string[] {
        if (this._errorMessages == null) {
            this._errorMessages = new Array<string>();
        }
        return this._errorMessages;
    }

    set errorMessages(value: string[]) {
        this._errorMessages = value;
    }

    get validate(): boolean {
        return this.service.validate;
    }

    set validate(value: boolean) {
        this.service.validate = value;
    }


    get activeTab(): number {
        return this._activeTab;
    }

    set activeTab(value: number) {
        this._activeTab = value;
    }

}
