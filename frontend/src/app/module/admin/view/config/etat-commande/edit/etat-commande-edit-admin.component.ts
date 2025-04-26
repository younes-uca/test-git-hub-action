import {Component, OnInit, Input} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';

import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';




import {EtatCommandeAdminService} from 'src/app/shared/service/admin/config/EtatCommandeAdmin.service';
import {EtatCommandeDto} from 'src/app/shared/model/config/EtatCommande.model';
import {EtatCommandeCriteria} from 'src/app/shared/criteria/config/EtatCommandeCriteria.model';



@Component({
  selector: 'app-etat-commande-edit-admin',
  standalone: false,
  templateUrl: './etat-commande-edit-admin.component.html'
})
export class EtatCommandeEditAdminComponent implements OnInit {

	protected _submitted = false;
    protected _errorMessages = new Array<string>();


    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;
    private _activeTab = 0;
    private _file: any;
    private _files: any;



    private _validEtatCommandeLibelle = true;
    private _validEtatCommandeCode = true;
    private _validEtatCommandeStyle = true;




    constructor(private service: EtatCommandeAdminService , @Inject(PLATFORM_ID) private platformId?) {
        this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
    }

    ngOnInit(): void {
    }

    public prepareEdit() {
    }



 public edit(): void {
        this.submitted = true;
        this.prepareEdit();
        this.validateForm();
        if (this.errorMessages.length === 0) {
            this.editWithShowOption(false);
        } else {
            this.messageService.add({
                severity: 'error',
                summary: 'Erreurs',
                detail: 'Merci de corrigé les erreurs sur le formulaire'
            });
        }
    }

    public editWithShowOption(showList: boolean) {
        this.service.edit().subscribe(religion=>{
            const myIndex = this.items.findIndex(e => e.id === this.item.id);
            this.items[myIndex] = religion;
            this.editDialog = false;
            this.submitted = false;
            this.item = new EtatCommandeDto();
        } , error =>{
            console.log(error);
        });
    }

    public hideEditDialog() {
        this.editDialog = false;
        this.setValidation(true);
    }





    public setValidation(value: boolean){
        this.validEtatCommandeLibelle = value;
        this.validEtatCommandeCode = value;
        this.validEtatCommandeStyle = value;
    }


    public validateForm(): void{
        this.errorMessages = new Array<string>();
        this.validateEtatCommandeLibelle();
        this.validateEtatCommandeCode();
        this.validateEtatCommandeStyle();
    }

    public validateEtatCommandeLibelle(){
        if (this.stringUtilService.isEmpty(this.item.libelle)) {
            this.errorMessages.push('Libelle non valide');
            this.validEtatCommandeLibelle = false;
        } else {
            this.validEtatCommandeLibelle = true;
        }
    }

    public validateEtatCommandeCode(){
        if (this.stringUtilService.isEmpty(this.item.code)) {
            this.errorMessages.push('Code non valide');
            this.validEtatCommandeCode = false;
        } else {
            this.validEtatCommandeCode = true;
        }
    }

    public validateEtatCommandeStyle(){
        if (this.stringUtilService.isEmpty(this.item.style)) {
            this.errorMessages.push('Style non valide');
            this.validEtatCommandeStyle = false;
        } else {
            this.validEtatCommandeStyle = true;
        }
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


	get items(): Array<EtatCommandeDto> {
        return this.service.items;
    }

    set items(value: Array<EtatCommandeDto>) {
        this.service.items = value;
    }

    get item(): EtatCommandeDto {
        return this.service.item;
    }

    set item(value: EtatCommandeDto) {
        this.service.item = value;
    }

    get editDialog(): boolean {
        return this.service.editDialog;
    }

    set editDialog(value: boolean) {
        this.service.editDialog = value;
    }

    get criteria(): EtatCommandeCriteria {
        return this.service.criteria;
    }

    set criteria(value: EtatCommandeCriteria) {
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
