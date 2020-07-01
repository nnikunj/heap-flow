import { Injectable } from '@angular/core';
import { Pageable, Sort } from 'src/app/models/pageable';

@Injectable({
    providedIn: 'root'
})

export class InventoryReserveResponse {
    content : InventoryReserve[];

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

export class InventoryReserve {
    id: number;
    itemCode: string;
    uom: string;
    descriptions: string;
    avialableQuantity: number;
    reservQuantity: number;
    category: string;
}

