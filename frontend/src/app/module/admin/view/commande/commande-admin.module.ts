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

import { CommandeCreateAdminComponent } from './commande/create/commande-create-admin.component';
import { CommandeEditAdminComponent } from './commande/edit/commande-edit-admin.component';
import { CommandeViewAdminComponent } from './commande/view/commande-view-admin.component';
import { CommandeListAdminComponent } from './commande/list/commande-list-admin.component';
import { CommandeDetailCreateAdminComponent } from './commande-detail/create/commande-detail-create-admin.component';
import { CommandeDetailEditAdminComponent } from './commande-detail/edit/commande-detail-edit-admin.component';
import { CommandeDetailViewAdminComponent } from './commande-detail/view/commande-detail-view-admin.component';
import { CommandeDetailListAdminComponent } from './commande-detail/list/commande-detail-list-admin.component';

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

    CommandeCreateAdminComponent,
    CommandeListAdminComponent,
    CommandeViewAdminComponent,
    CommandeEditAdminComponent,
    CommandeDetailCreateAdminComponent,
    CommandeDetailListAdminComponent,
    CommandeDetailViewAdminComponent,
    CommandeDetailEditAdminComponent,
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
  CommandeCreateAdminComponent,
  CommandeListAdminComponent,
  CommandeViewAdminComponent,
  CommandeEditAdminComponent,
  CommandeDetailCreateAdminComponent,
  CommandeDetailListAdminComponent,
  CommandeDetailViewAdminComponent,
  CommandeDetailEditAdminComponent,
  ],
})
export class CommandeAdminModule { }
