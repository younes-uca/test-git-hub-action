import {Component, OnInit} from '@angular/core';


import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';
import {Inject, Injectable, PLATFORM_ID} from '@angular/core';


import {environment} from 'src/environments/environment';

import {RoleService} from 'src/app/zynerator/security/shared/service/Role.service';
import {AbstractService} from 'src/app/zynerator/service/AbstractService';
import {BaseDto} from 'src/app/zynerator/dto/BaseDto.model';
import {BaseCriteria} from 'src/app/zynerator/criteria/BaseCriteria.model';
import {StringUtilService} from 'src/app/zynerator/util/StringUtil.service';
import {ServiceLocator} from 'src/app/zynerator/service/ServiceLocator';
import {ConfirmationService, MessageService,MenuItem} from 'primeng/api';
import {FileTempDto} from 'src/app/zynerator/dto/FileTempDto.model';


import {CommandeDetailAdminService} from 'src/app/shared/service/admin/commande/CommandeDetailAdmin.service';
import {CommandeDetailDto} from 'src/app/shared/model/commande/CommandeDetail.model';
import {CommandeDetailCriteria} from 'src/app/shared/criteria/commande/CommandeDetailCriteria.model';

import {CommandeDto} from 'src/app/shared/model/commande/Commande.model';
import {CommandeAdminService} from 'src/app/shared/service/admin/commande/CommandeAdmin.service';
import {ProduitDto} from 'src/app/shared/model/config/Produit.model';
import {ProduitAdminService} from 'src/app/shared/service/admin/config/ProduitAdmin.service';
@Component({
  selector: 'app-commande-detail-view-admin',
  standalone: false,
  templateUrl: './commande-detail-view-admin.component.html'
})
export class CommandeDetailViewAdminComponent implements OnInit {


	protected _submitted = false;
    protected _errorMessages = new Array<string>();

    protected datePipe: DatePipe;
    protected messageService: MessageService;
    protected confirmationService: ConfirmationService;
    protected roleService: RoleService;
    protected router: Router;
    protected stringUtilService: StringUtilService;



    constructor(private service: CommandeDetailAdminService, private commandeService: CommandeAdminService, private produitService: ProduitAdminService){
		this.datePipe = ServiceLocator.injector.get(DatePipe);
        this.messageService = ServiceLocator.injector.get(MessageService);
        this.confirmationService = ServiceLocator.injector.get(ConfirmationService);
        this.roleService = ServiceLocator.injector.get(RoleService);
        this.router = ServiceLocator.injector.get(Router);
        this.stringUtilService = ServiceLocator.injector.get(StringUtilService);
	}

    ngOnInit(): void {
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

    public hideViewDialog() {
        this.viewDialog = false;
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

    get viewDialog(): boolean {
        return this.service.viewDialog;
    }

    set viewDialog(value: boolean) {
        this.service.viewDialog = value;
    }

    get criteria(): CommandeDetailCriteria {
        return this.service.criteria;
    }

    set criteria(value: CommandeDetailCriteria) {
        this.service.criteria = value;
    }

    get dateFormat(){
        return environment.dateFormatView;
    }

    get dateFormatColumn(){
        return environment.dateFormatList;
    }


}
