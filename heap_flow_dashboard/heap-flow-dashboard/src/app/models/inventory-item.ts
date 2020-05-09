
 
import { Injectable } from '@angular/core';
@Injectable({
    providedIn: 'root'
  })
export class InventoryItem {
    constructor() {}
    dbId: Number;
    id: string;
    description: string;

}
