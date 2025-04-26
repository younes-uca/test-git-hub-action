import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

import {environment} from 'src/environments/environment';
import {PaginatedList} from 'src/app/zynerator/dto/PaginatedList.model';
import  moment from 'moment/moment';

import {ProduitDto} from 'src/app/shared/model/config/Produit.model';
import {ProduitCriteria} from 'src/app/shared/criteria/config/ProduitCriteria.model';


@Injectable({
  providedIn: 'root'
})
export class ProduitAdminService {
    protected _API = '';
    protected _items: Array<ProduitDto>;
    protected _item: ProduitDto;
    protected _selections: Array<ProduitDto>;
    protected _createDialog: boolean;
    protected _editDialog: boolean;
    protected _viewDialog: boolean;
    protected _criteria: ProduitCriteria;
    protected _validate = false;


    private _createActionIsValid = true;
    private _editActionIsValid = true;
    private _listActionIsValid = true;
    private _deleteActionIsValid = true;
    private _viewActionIsValid = true;
    private _duplicateActionIsValid = true;


    private _createAction = 'create';
    private _listAction = 'list';
    private _editAction = 'edit';
    private _deleteAction = 'delete';
    private _viewAction = 'view';
    private _duplicateAction = 'duplicate';
    private _entityName: string;

    protected API_PERMISSION: string ;


    constructor(private http: HttpClient) {
        this.API_PERMISSION = environment.apiUrl + 'modelPermissionUser/';
    }


    public findAll() {
        return this.http.get<Array<ProduitDto>>(this.API);
    }

    public findAllOptimized() {
        return this.http.get<Array<ProduitDto>>(this.API + 'optimized');
    }

    public findPaginatedByCriteria(criteria: ProduitCriteria): Observable<PaginatedList<ProduitDto>> {
        return this.http.post<PaginatedList<ProduitDto>>(this.API + 'find-paginated-by-criteria', criteria);
    }

    public save(): Observable<ProduitDto> {
        return this.http.post<ProduitDto>(this.API, this.item);
    }

    public delete(dto: ProduitDto) {
        return this.http.delete<number>(this.API + 'id/' + dto.id);
    }


    public edit(): Observable<ProduitDto> {
        return this.http.put<ProduitDto>(this.API, this.item);
    }


    public findByCriteria(criteria: ProduitCriteria): Observable<Array<ProduitDto>> {
        return this.http.post<Array<ProduitDto>>(this.API + 'find-by-criteria', criteria);
    }

    public findByIdWithAssociatedList(item: ProduitDto): Observable<ProduitDto> {
        return this.http.get<ProduitDto>(this.API + 'id/' + item.id);
    }

    public deleteMultiple() {
        return this.http.post<void>(this.API + 'multiple', this.selections);
    }


    public exportPdf(element: ProduitDto): Observable<ArrayBuffer> {
        return this.http.post(this.API + 'exportPdf/', element, {responseType: 'arraybuffer'});
    }

    public hasActionPermission(username: string, actionReference: string): Observable<boolean> {
        // tslint:disable-next-line:max-line-length
        return this.http.get<boolean>(this.API_PERMISSION + 'user/' + username + '/model/' + this.entityName + '/action/' + actionReference);
    }

    public importExcel(file: File): Observable<Array<ProduitDto>> {
        const formData: FormData = new FormData();
        formData.append('file', file, file.name);
        return this.http.post<Array<ProduitDto>>(this.API + 'import-excel', formData);
    }



    public format(myDate: Date): Date {
        if (myDate != null) {
            const newdate = new Date(myDate);
            const formattedDate = moment(newdate).format(environment.dateFormatEdit);
            console.log(formattedDate);
            myDate = new Date(formattedDate);
        }
        return myDate;
    }

    get API() {
        return environment.apiUrlOrdermanagementms1 + 'admin/produit/';
    }

    public get items(): Array<ProduitDto> {
        if (this._items == null) {
            this._items = new Array<ProduitDto>();
        }
        return this._items;
    }

    public set items(value: Array<ProduitDto>) {
        this._items = value;
    }

    public get item(): ProduitDto {
        if (this._item == null) {
            this._item = new ProduitDto();
        }
        return this._item;
    }

    public set item(value: ProduitDto) {
        this._item = value;
    }

    public get selections(): Array<ProduitDto> {
        if (this._selections == null) {
            this._selections = new Array<ProduitDto>();
        }
        return this._selections;
    }


    public set selections(value: Array<ProduitDto>) {
        this._selections = value;
    }

    public get createDialog(): boolean {
        return this._createDialog;
    }

    public set createDialog(value: boolean) {
        this._createDialog = value;
    }

    public get editDialog(): boolean {
        return this._editDialog;
    }

    public set editDialog(value: boolean) {
        this._editDialog = value;
    }

    public get viewDialog(): boolean {
        return this._viewDialog;
    }

    public set viewDialog(value: boolean) {
        this._viewDialog = value;
    }

    public get criteria(): ProduitCriteria {
        if (this._criteria == null) {
            this._criteria = new ProduitCriteria();
        }
        return this._criteria;
    }

    public set criteria(value: ProduitCriteria) {
        this._criteria = value;
    }


    public setApi(API: string) {
        this._API = API;
    }

    set validate(value: boolean) {
        this._validate = value;
    }


    get createAction(): string {
        return this._createAction;
    }

    set createAction(value: string) {
        this._createAction = value;
    }

    get listAction(): string {
        return this._listAction;
    }

    set listAction(value: string) {
        this._listAction = value;
    }

    get editAction(): string {
        return this._editAction;
    }

    set editAction(value: string) {
        this._editAction = value;
    }

    get deleteAction(): string {
        return this._deleteAction;
    }

    set deleteAction(value: string) {
        this._deleteAction = value;
    }

    get createActionIsValid(): boolean {
        return this._createActionIsValid;
    }

    set createActionIsValid(value: boolean) {
        this._createActionIsValid = value;
    }


    get editActionIsValid(): boolean {
        return this._editActionIsValid;
    }

    set editActionIsValid(value: boolean) {
        this._editActionIsValid = value;
    }

    get listActionIsValid(): boolean {
        return this._listActionIsValid;
    }

    set listActionIsValid(value: boolean) {
        this._listActionIsValid = value;
    }

    get deleteActionIsValid(): boolean {
        return this._deleteActionIsValid;
    }

    set deleteActionIsValid(value: boolean) {
        this._deleteActionIsValid = value;
    }

    get viewAction(): string {
        return this._viewAction;
    }

    set viewAction(value: string) {
        this._viewAction = value;
    }

    get duplicateAction(): string {
        return this._duplicateAction;
    }

    set duplicateAction(value: string) {
        this._duplicateAction = value;
    }

    get viewActionIsValid(): boolean {
        return this._viewActionIsValid;
    }

    set viewActionIsValid(value: boolean) {
        this._viewActionIsValid = value;
    }

    get duplicateActionIsValid(): boolean {
        return this._duplicateActionIsValid;
    }

    set duplicateActionIsValid(value: boolean) {
        this._duplicateActionIsValid = value;
    }

    get entityName(): string {
        return this._entityName;
    }

    set entityName(value: string) {
        this._entityName = value;
    }

}
