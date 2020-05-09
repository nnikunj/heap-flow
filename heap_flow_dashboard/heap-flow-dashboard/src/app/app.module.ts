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
import { IssueMaterialComponent } from './transactions/issue-material/issue-material.component';
import { InventorySummaryComponent } from './inventory/inventory-summary/inventory-summary.component';
import { VendorsListComponent } from './vendors/vendors-list/vendors-list.component';
import { VendorsAddComponent } from './vendors/vendors-add/vendors-add.component';
import { MachinesListComponent } from './machines/machines-list/machines-list.component';
import { MachinesAddComponent } from './machines/machines-add/machines-add.component';
import { InventoryImportComponent } from './inventory/inventory-import/inventory-import.component';
import { MachineImportComponent } from './machines/machine-import/machine-import.component';
import { VendorImportComponent } from './vendors/vendor-import/vendor-import.component';
import { AddInventoryItemComponent } from './inventory/add-inventory-item/add-inventory-item.component';
import { FormsModule } from '@angular/forms';
 
 
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
    IssueMaterialComponent,
    InventorySummaryComponent,
    VendorsListComponent,
    VendorsAddComponent,
    MachinesListComponent,
    MachinesAddComponent,
    InventoryImportComponent,
    MachineImportComponent,
    VendorImportComponent,
    AddInventoryItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
