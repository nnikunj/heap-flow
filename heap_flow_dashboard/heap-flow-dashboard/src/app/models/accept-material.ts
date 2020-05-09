import { Vendor } from './vendor';
import { InventoryItem } from './inventory-item';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class AcceptMaterial {
    vendor: Vendor;
    invoice: string;
    grn: string;
    recordDate: string;

    quantity: Number;
    price: Number;

    acceptingMaterialCode: string;
    classification: string;

    items: InventoryItem[];

    constructor() {
      this.vendor = new Vendor();
      this.recordDate= (new Date()).getDate().toString();
    }

}
