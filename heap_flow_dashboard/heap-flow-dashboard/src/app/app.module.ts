import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClarityModule } from '@clr/angular';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
 
import { SubNavigationMenuComponent } from './sub-navigation-menu/sub-navigation-menu.component';
import { InventoryComponent } from './inventory/inventory.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { ReportsComponent } from './reports/reports.component';
import { VendorsComponent } from './vendors/vendors.component';
import { MachinesComponent } from './machines/machines.component';
import { AboutComponent } from './about/about.component';
import { InventoryItemsGridComponent } from './inventory/inventory-items-grid/inventory-items-grid.component';
import { HttpClientModule } from '@angular/common/http';
import { AcceptMaterialComponent } from './transactions/accept-material/accept-material.component';
import { AcceptMaterialListComponent, AcceptMaterialDeleteDialog } from 'src/app/transactions/accept-material-list/accept-material-list.component'
import { IssueMaterialComponent } from './transactions/issue-material/issue-material.component';
import { IssueMaterialListComponent, IssueMaterialDeleteDialog } from 'src/app/transactions/issue-material-list/issue-material-list.component';
import { InventorySummaryComponent } from './inventory/inventory-summary/inventory-summary.component';
import { VendorsListComponent } from './vendors/vendors-list/vendors-list.component';
import { VendorsAddComponent } from './vendors/vendors-add/vendors-add.component';
import { MachinesListComponent } from './machines/machines-list/machines-list.component';
import { MachinesAddComponent } from 'src/app/machines/machines-add/machines-add.component';
import { InventoryImportComponent } from './inventory/inventory-import/inventory-import.component';
import { MachineImportComponent } from './machines/machine-import/machine-import.component';
import { VendorImportComponent } from './vendors/vendor-import/vendor-import.component';
import { AddInventoryItemComponent } from './inventory/add-inventory-item/add-inventory-item.component';
import { AddInventoryDialog } from 'src/app/inventory/add-inventory-item/add-inventory-dialog.component';
import { PrintItemQRCode } from 'src/app/inventory/print-item-qrcode/print-item-qrcode.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core'; 
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar'
import { MatDividerModule } from '@angular/material/divider';
import { MatTableModule } from '@angular/material/table';
import { MatSnackBarModule  } from '@angular/material/snack-bar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatDialogModule } from '@angular/material/dialog'
import { PdfViewerModule } from 'ng2-pdf-viewer';
import { ImportExcelComponent } from './import-excel/import-excel.component';
import { PdfJsViewerModule } from 'ng2-pdfjs-viewer';
import { InventoryReserveComponent } from './inventory/inventory-reserve/inventory-reserve.component';
 
@NgModule({
  declarations: [
    AppComponent,
    SubNavigationMenuComponent,
    InventoryComponent,
    TransactionsComponent,
    ReportsComponent,
    VendorsComponent,
    MachinesComponent,
    AboutComponent,
    InventoryItemsGridComponent,
    AcceptMaterialComponent,
    AcceptMaterialListComponent,
    AcceptMaterialDeleteDialog,
    IssueMaterialComponent,
    IssueMaterialListComponent,
    IssueMaterialDeleteDialog,
    InventorySummaryComponent,
    VendorsListComponent,
    VendorsAddComponent,
    MachinesListComponent,
    MachinesAddComponent,
    InventoryImportComponent,
    MachineImportComponent,
    VendorImportComponent,
    AddInventoryItemComponent,
    AddInventoryDialog,
    PrintItemQRCode,
    ImportExcelComponent,
    InventoryReserveComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule ,
    MatAutocompleteModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    MatButtonModule,
    MatDividerModule,
    MatTableModule,
    MatSnackBarModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSortModule,
    MatDialogModule,
    MatProgressBarModule,
    MatIconModule,
    MatCardModule,
    PdfViewerModule,
    PdfJsViewerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
