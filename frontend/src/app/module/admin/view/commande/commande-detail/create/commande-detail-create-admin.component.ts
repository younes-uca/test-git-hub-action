import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {CommandeDetailAdminService} from 'src/app/shared/service/admin/commande/CommandeDetailAdmin.service';
import {CommandeDetailDto} from 'src/app/shared/model/commande/CommandeDetail.model';
import {CommandeDetailCriteria} from 'src/app/shared/criteria/commande/CommandeDetailCriteria.model';
import {CommandeDto} from 'src/app/shared/model/commande/Commande.model';
import {CommandeAdminService} from 'src/app/shared/service/admin/commande/CommandeAdmin.service';
import {ProduitDto} from 'src/app/shared/model/config/Produit.model';
import {ProduitAdminService} from 'src/app/shared/service/admin/config/ProduitAdmin.service';
@Component({
  selector: 'app-commande-detail-create-admin',
  standalone: false,
  templateUrl: './commande-detail-create-admin.component.html'
})
export class CommandeDetailCreateAdminComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;



   private _validCommandeDetailPrix = true;
   private _validCommandeDetailQte = true;
   private _validCommandeDetailCommande = true;
   private _validCommandeDetailProduit = true;
    private _validCommandeRef = true;
    private _validCommandeMontant = true;
    private _validCommandeEtatCommande = true;
    private _validProduitRef = true;
    private _validProduitLibelle = true;
    private _validProduitPrix = true;

	constructor(private service: CommandeDetailAdminService , private commandeService: CommandeAdminService, private produitService: ProduitAdminService, @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
        this.commandeService.findAll().subscribe((data) => this.commandes = data);
        this.produitService.findAll().subscribe((data) => this.produits = data);
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
                this.item = new CommandeDetailDto();
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





    public  setValidation(value: boolean){
        this.validCommandeDetailPrix = value;
        this.validCommandeDetailQte = value;
        this.validCommandeDetailCommande = value;
        this.validCommandeDetailProduit = value;
    }



    public  validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateCommandeDetailPrix();
        this.validateCommandeDetailQte();
        this.validateCommandeDetailCommande();
        this.validateCommandeDetailProduit();
    }

    public validateCommandeDetailPrix(){
        if (this.stringUtilService.isEmpty(this.item.prix)) {
        this.errorMessages.push('Prix non valide');
        this.validCommandeDetailPrix = false;
        } else {
            this.validCommandeDetailPrix = true;
        }
    }
    public validateCommandeDetailQte(){
        if (this.stringUtilService.isEmpty(this.item.qte)) {
        this.errorMessages.push('Qte non valide');
        this.validCommandeDetailQte = false;
        } else {
            this.validCommandeDetailQte = true;
        }
    }
    public validateCommandeDetailCommande(){
        if (this.stringUtilService.isEmpty(this.item.commande)) {
        this.errorMessages.push('Commande non valide');
        this.validCommandeDetailCommande = false;
        } else {
            this.validCommandeDetailCommande = true;
        }
    }
    public validateCommandeDetailProduit(){
        if (this.stringUtilService.isEmpty(this.item.produit)) {
        this.errorMessages.push('Produit non valide');
        this.validCommandeDetailProduit = false;
        } else {
            this.validCommandeDetailProduit = true;
        }
    }


    public async openCreateCommande(commande: string) {
    const isPermistted = await this.roleService.isPermitted('Commande', 'add');
    if(isPermistted) {
         this.commande = new CommandeDto();
         this.createCommandeDialog = true;
    }else{
        this.messageService.add({
        severity: 'error', summary: 'erreur', detail: 'problème de permission'
        });
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
    get commande(): CommandeDto {
        return this.commandeService.item;
    }
    set commande(value: CommandeDto) {
        this.commandeService.item = value;
    }
    get commandes(): Array<CommandeDto> {
        return this.commandeService.items;
    }
    set commandes(value: Array<CommandeDto>) {
        this.commandeService.items = value;
    }
    get createCommandeDialog(): boolean {
        return this.commandeService.createDialog;
    }
    set createCommandeDialog(value: boolean) {
        this.commandeService.createDialog= value;
    }



    get validCommandeDetailPrix(): boolean {
        return this._validCommandeDetailPrix;
    }

    set validCommandeDetailPrix(value: boolean) {
         this._validCommandeDetailPrix = value;
    }
    get validCommandeDetailQte(): boolean {
        return this._validCommandeDetailQte;
    }

    set validCommandeDetailQte(value: boolean) {
         this._validCommandeDetailQte = value;
    }
    get validCommandeDetailCommande(): boolean {
        return this._validCommandeDetailCommande;
    }

    set validCommandeDetailCommande(value: boolean) {
         this._validCommandeDetailCommande = value;
    }
    get validCommandeDetailProduit(): boolean {
        return this._validCommandeDetailProduit;
    }

    set validCommandeDetailProduit(value: boolean) {
         this._validCommandeDetailProduit = value;
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
    get validProduitRef(): boolean {
        return this._validProduitRef;
    }
    set validProduitRef(value: boolean) {
        this._validProduitRef = value;
    }
    get validProduitLibelle(): boolean {
        return this._validProduitLibelle;
    }
    set validProduitLibelle(value: boolean) {
        this._validProduitLibelle = value;
    }
    get validProduitPrix(): boolean {
        return this._validProduitPrix;
    }
    set validProduitPrix(value: boolean) {
        this._validProduitPrix = value;
    }


    get items(): Array<CommandeDetailDto> {
        return this.service.items;
    }

    set items(value: Array<CommandeDetailDto>) {
        this.service.items = value;
    }

    get item(): CommandeDetailDto {
        return this.service.item;
    }

    set item(value: CommandeDetailDto) {
        this.service.item = value;
    }

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get criteria(): CommandeDetailCriteria {
        return this.service.criteria;
    }

    set criteria(value: CommandeDetailCriteria) {
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
