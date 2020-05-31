import { Injectable } from '@angular/core';
import { Pageable, Sort } from './pageable';

@Injectable({
    providedIn: 'root'
})

export class InventorySummaryResponse {
    content: InventorySummary[];
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

export class InventorySummary {
    inventoryItemCode: string;
    descriptions: Descriptions;
    creation: Date;
    modified: Date;
    baseUnitMeasure: string;
    productGrpCode: string;
    genProductPostingGrp: string;
    itemCategoryCode: string;
    gstGrpCode: string;
    hsnSacCode: string;

    type: Type;

    averageUnitPrice: Number;
    quantity: Number;
    value: Number;

}

export class Type {
    id: Number;
    version: Number;
    modified: Date;
    creation: Date;
    typeName: string;
    description: string;
    consideredForValuation: boolean;
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