import { Injectable } from '@angular/core';
import { Pageable, Sort } from './pageable';

@Injectable({
    providedIn: 'root'
})

export class IssueMaterialResponse {
    content: IssueMaterial[];

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

export class IssueMaterial {
    dbId: string;
    entryCreatedOnDate: string;
    issueDate: string;
    inventoryType: string;
    outgoingQuantity: string;
    uom: string;
    outgoingMaterial: string;
    outgoingMaterialDescription: string;
    classificationCategory: string;
    consumingMachine: string;
    issuedToEngineer: string;
    issuedForDept: string;
    approvedBy: string;
    issuedBy: string;
    issueSlipNumber: string;
    outgoingMaterialPrice: string
}