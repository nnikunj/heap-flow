import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InventoryComponent } from './inventory/inventory.component';

import { TransactionsComponent } from './transactions/transactions.component';
import { ReportsComponent } from './reports/reports.component';
import { VendorsComponent } from './vendors/vendors.component';
import { MachinesComponent } from './machines/machines.component';
import { AboutComponent } from './about/about.component';
import { AcceptMaterialComponent } from './transactions/accept-material/accept-material.component';
import { AcceptMaterialListComponent } from 'src/app/transactions/accept-material-list/accept-material-list.component';
import { IssueMaterialComponent } from './transactions/issue-material/issue-material.component';
import { IssueMaterialListComponent } from 'src/app/transactions/issue-material-list/issue-material-list.component'
import { InventoryItemsGridComponent } from './inventory/inventory-items-grid/inventory-items-grid.component';
import { InventorySummaryComponent } from './inventory/inventory-summary/inventory-summary.component';
import { VendorsListComponent } from './vendors/vendors-list/vendors-list.component';
import { VendorsAddComponent } from './vendors/vendors-add/vendors-add.component';
import { MachinesListComponent } from './machines/machines-list/machines-list.component';
import { MachinesAddComponent } from './machines/machines-add/machines-add.component';
import { InventoryImportComponent } from './inventory/inventory-import/inventory-import.component';
import { VendorImportComponent } from './vendors/vendor-import/vendor-import.component';
import { MachineImportComponent } from './machines/machine-import/machine-import.component';
import { AddInventoryItemComponent } from './inventory/add-inventory-item/add-inventory-item.component';

const routes: Routes = [

  {
    path: 'inventory',
    component: InventoryComponent,
    children: [
      { path: 'inventory-items-list', component: InventoryItemsGridComponent },
      { path: 'inventory-summary', component: InventorySummaryComponent },
      { path: 'import-inventory-items', component: InventoryImportComponent },
      { path: 'add-inventory-item', component: AddInventoryItemComponent }
    ]
  },
  {
    path: 'transactions',
    component: TransactionsComponent,
    children: [
      { path: 'accept-material', component: AcceptMaterialComponent },
      { path: 'issue-material', component: IssueMaterialComponent },
      { path: 'accept-material-list', component: AcceptMaterialListComponent},
      { path: 'issue-material-list', component: IssueMaterialListComponent}

    ]
  },
  { path: 'reports', component: ReportsComponent },
  {
    path: 'vendors',
    component: VendorsComponent,
    children: [
      { path: 'vendors-list', component: VendorsListComponent },
      { path: 'add-vendor', component: VendorsAddComponent },
      { path: 'import-vendors', component: VendorImportComponent }
    ]
  },
  {
    path: 'machines',
    component: MachinesComponent,
    children: [
      { path: 'machines-list', component: MachinesListComponent },
      { path: 'add-machine', component: MachinesAddComponent },
      { path: 'import-machines', component: MachineImportComponent }
    ]
  },
  { path: 'about', component: AboutComponent },
  { path: '**', redirectTo: '/' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
