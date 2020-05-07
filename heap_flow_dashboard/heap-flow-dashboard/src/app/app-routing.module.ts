import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InventoryComponent } from './inventory/inventory.component';
 
import { TransactionsComponent } from './transactions/transactions.component';
import { ReportsComponent } from './reports/reports.component';
import { VendorsComponent } from './vendors/vendors.component';
import { MachinesComponent } from './machines/machines.component';
import { AboutComponent } from './about/about.component';
import { AcceptMaterialComponent } from './transactions/accept-material/accept-material.component';
import { IssueMaterialComponent } from './transactions/issue-material/issue-material.component';
import { InventoryItemsGridComponent } from './inventory/inventory-items-grid/inventory-items-grid.component';
import { InventorySummaryComponent } from './inventory/inventory-summary/inventory-summary.component';

const routes: Routes = [
   
  {
    path:'inventory', 
    component: InventoryComponent,
    children:[
      {path:'inventory-items-list', component:InventoryItemsGridComponent},
      {path:'inventory-summary', component:InventorySummaryComponent}
    ]
  },
  { path:'transactions', 
    component: TransactionsComponent,
    children:[
      {path:'accept-material', component:AcceptMaterialComponent},
      {path:'issue-material', component:IssueMaterialComponent}
    ]
  },
  {path:'reports', component: ReportsComponent},
  {path:'vendors', component: VendorsComponent},
  {path:'machines', component: MachinesComponent},
  {path:'about', component: AboutComponent},
  {path:'**', redirectTo:'/'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
