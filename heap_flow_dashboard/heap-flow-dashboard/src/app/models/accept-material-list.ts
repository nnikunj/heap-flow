import { Injectable } from '@angular/core';
import { Pageable, Sort } from './pageable';

@Injectable({
    providedIn: 'root'
})

export class AcceptMaterialResponse {
    content : AcceptMaterial[];
    
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

export class AcceptMaterial {
    dbId: string;
    entryCreatedOnDate: string;
    recievedDate: string;
    inventoryType: string;
    incomingQuantity: string;
    pricePerUnit: string;
    incomingMaterial: string;
    classificationCategory: string;
    invoiceNumber: string;
    invoiceDate: string;
    poNumber: string;
    poDate: string;
    intdentNumber: string;
    materialAcceptedBy: string;
    grnNumber: string;
    department: string;
    vendor: string;
    incomingMaterialDescription: string;
    uom: string
}