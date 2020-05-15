import { Vendor } from './vendor';
import { InventoryItem } from './inventory-item';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class AcceptMaterial {
    vendorName: string;
    vendorCode: string;
    invoice: string;
    grn: string;
    recordDate: string;

    incomingItemsList : Item[];

    constructor() {
//      this.vendor = new Vendor();
      this.recordDate= (new Date()).getDate().toString();
    }

}

export class Item {

  constructor(){}

  productCode: string;
  classification: string;
  inventoryType: string;
  description: string;
  quantity: Number;
  pricePerUnit: Number;
}
