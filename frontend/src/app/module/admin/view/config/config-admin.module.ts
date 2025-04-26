import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import {ToastModule} from 'primeng/toast';
import {ToolbarModule} from 'primeng/toolbar';
import {TableModule} from 'primeng/table';
import {DropdownModule} from 'primeng/dropdown';
import {InputSwitchModule} from 'primeng/inputswitch';
import {TextareaModule} from 'primeng/textarea';
import {EditorModule} from "primeng/editor";

import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import {CalendarModule} from 'primeng/calendar';
import {PanelModule} from 'primeng/panel';
import {InputNumberModule} from 'primeng/inputnumber';
import {BadgeModule} from 'primeng/badge';
import { MultiSelectModule } from 'primeng/multiselect';
import {TranslateModule} from '@ngx-translate/core';
import {FileUploadModule} from 'primeng/fileupload';
import {FullCalendarModule} from '@fullcalendar/angular';
import {CardModule} from "primeng/card";
import {TagModule} from "primeng/tag";

import { ProduitCreateAdminComponent } from './produit/create/produit-create-admin.component';
import { ProduitEditAdminComponent } from './produit/edit/produit-edit-admin.component';
import { ProduitViewAdminComponent } from './produit/view/produit-view-admin.component';
import { ProduitListAdminComponent } from './produit/list/produit-list-admin.component';
import { EtatCommandeCreateAdminComponent } from './etat-commande/create/etat-commande-create-admin.component';
import { EtatCommandeEditAdminComponent } from './etat-commande/edit/etat-commande-edit-admin.component';
import { EtatCommandeViewAdminComponent } from './etat-commande/view/etat-commande-view-admin.component';
import { EtatCommandeListAdminComponent } from './etat-commande/list/etat-commande-list-admin.component';

import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {TabViewModule} from 'primeng/tabview';
import { SplitButtonModule } from 'primeng/splitbutton';
import { MessageModule } from 'primeng/message';
import {MessagesModule} from 'primeng/messages';
import {PaginatorModule} from 'primeng/paginator';
import { IconField } from 'primeng/iconfield';
import { InputIcon } from 'primeng/inputicon';


@NgModule({
  declarations: [

    ProduitCreateAdminComponent,
    ProduitListAdminComponent,
    ProduitViewAdminComponent,
    ProduitEditAdminComponent,
    EtatCommandeCreateAdminComponent,
    EtatCommandeListAdminComponent,
    EtatCommandeViewAdminComponent,
    EtatCommandeEditAdminComponent,
  ],
  imports: [
    CommonModule,
    ToastModule,
    ToolbarModule,
    TableModule,
    ConfirmDialogModule,
    DialogModule,
    PasswordModule,
    InputTextModule,
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    SplitButtonModule,
    DropdownModule,
    TabViewModule,
    InputSwitchModule,
    TextareaModule,
    CalendarModule,
    PanelModule,
    MessageModule,
    MessagesModule,
    InputNumberModule,
    BadgeModule,
    MultiSelectModule,
    PaginatorModule,
    TranslateModule,
    FileUploadModule,
    FullCalendarModule,
    CardModule,
    EditorModule,
    TagModule,
    IconField,
    InputIcon


  ],
  exports: [
  ProduitCreateAdminComponent,
  ProduitListAdminComponent,
  ProduitViewAdminComponent,
  ProduitEditAdminComponent,
  EtatCommandeCreateAdminComponent,
  EtatCommandeListAdminComponent,
  EtatCommandeViewAdminComponent,
  EtatCommandeEditAdminComponent,
  ],
})
export class ConfigAdminModule { }
