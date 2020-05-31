import { Vendor } from './vendor';
import { InventoryItem } from './inventory-item';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AcceptMaterial {
  recordDate: string;
  vendorCode: string;
  vendorName: string;
  intentNumber: string;
  grn: string;
  invoice: string;
  invoiceDate: string;
  poNumber: string;
  poDate: string;
  loggedInUser: string;

  incomingItemsList: Item[];

  constructor() {
    //      this.vendor = new Vendor();
    this.recordDate = (new Date()).getDate().toString();
  }
}

export class Item {

  constructor() { }

  productCode: string;
  classification: string;
  inventoryType: string;
  description: string;
  quantity: Number;
  pricePerUnit: Number;
  baseUnitMeasure: string;//This is just for display purpose. It should not be sent in post request.
}
