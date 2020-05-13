import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class Item {

    constructor(){}

    productCode: string;
    classification: string;
    inventoryType: string;
    description: string;
    quantity: Number;
    pricePerUnit: Number;

    


}