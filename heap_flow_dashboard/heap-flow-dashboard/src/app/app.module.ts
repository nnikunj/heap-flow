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
import { InventoryItemsGridComponent } from './inventory-items-grid/inventory-items-grid.component';
import { HttpClientModule } from '@angular/common/http';
 
 
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
    InventoryItemsGridComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
