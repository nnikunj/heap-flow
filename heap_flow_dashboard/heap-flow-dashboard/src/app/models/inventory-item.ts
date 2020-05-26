import { Injectable } from '@angular/core';
import { Pageable, Sort } from './pageable';

@Injectable({
  providedIn: 'root'
})

export class InventoryItemResponse {
  content: InventoryItem[];
  pageable: Pageable;

  totalElements: Number;
  totalPages: Number;
  last: boolean;
  number: Number;
  size: Number;
  sort: Sort;
  numberOfElements: Number;
  first: boolean;
  empty: boolean;
}

export class InventoryItem {
  constructor() { }
  stokcs: Stock[];

  inventoryItemCode: string;
  dbId: Number;
  descriptions: Descriptions[];
  creation: Date;
  modified: Date;
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
