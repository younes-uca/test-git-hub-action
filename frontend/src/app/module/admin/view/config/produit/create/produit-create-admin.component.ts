import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';

import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {ProduitAdminService} from 'src/app/shared/service/admin/config/ProduitAdmin.service';
import {ProduitDto} from 'src/app/shared/model/config/Produit.model';
import {ProduitCriteria} from 'src/app/shared/criteria/config/ProduitCriteria.model';
@Component({
  selector: 'app-produit-create-admin',
  standalone: false,
  templateUrl: './produit-create-admin.component.html'
})
export class ProduitCreateAdminComponent  implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;



   private _validProduitRef = true;
   private _validProduitLibelle = true;
   private _validProduitPrix = true;

	constructor(private service: ProduitAdminService , @Inject(PLATFORM_ID) private platformId? ) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
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
                this.item = new ProduitDto();
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
        this.validProduitRef = value;
        this.validProduitLibelle = value;
        this.validProduitPrix = value;
    }



    public  validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateProduitRef();
        this.validateProduitLibelle();
        this.validateProduitPrix();
    }

    public validateProduitRef(){
        if (this.stringUtilService.isEmpty(this.item.ref)) {
        this.errorMessages.push('Ref non valide');
        this.validProduitRef = false;
        } else {
            this.validProduitRef = true;
        }
    }
    public validateProduitLibelle(){
        if (this.stringUtilService.isEmpty(this.item.libelle)) {
        this.errorMessages.push('Libelle non valide');
        this.validProduitLibelle = false;
        } else {
            this.validProduitLibelle = true;
        }
    }
    public validateProduitPrix(){
        if (this.stringUtilService.isEmpty(this.item.prix)) {
        this.errorMessages.push('Prix non valide');
        this.validProduitPrix = false;
        } else {
            this.validProduitPrix = true;
        }
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



    get items(): Array<ProduitDto> {
        return this.service.items;
    }

    set items(value: Array<ProduitDto>) {
        this.service.items = value;
    }

    get item(): ProduitDto {
        return this.service.item;
    }

    set item(value: ProduitDto) {
        this.service.item = value;
    }

    get createDialog(): boolean {
        return this.service.createDialog;
    }

    set createDialog(value: boolean) {
        this.service.createDialog = value;
    }

    get criteria(): ProduitCriteria {
        return this.service.criteria;
    }

    set criteria(value: ProduitCriteria) {
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
