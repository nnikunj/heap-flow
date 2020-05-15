import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InventoryItem {
  constructor() { }

  dbId: Number;
  inventoryItemCode: string;
  stokcs: Stock[];
  descriptions: Descriptions[];
  modified: Date;
  creation: Date;
  baseUnitMeasure: string;
  productGrpCode: string;
  genProductPostingGrp: string;
  itemCategoryCode: string;
  gstGrpCode: string;
  hsnSacCode: string;
}

export class Stock {
  constructor() { }

  inventoryTypeName: string;
  quantity: Number;
  averageUnitPrice: Number;

}

export class Descriptions {
  constructor() { }

  description: string;
  description2: string;
  description3: string;
  description4: string;
  description5: string;
  description6: string;
}
