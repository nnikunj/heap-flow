import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class IssueMaterial {
    machineCode: string;
    recordDate: string;
    issuedViaEmp: string;

    outgoingItemsList: OutgoingItem[];
}

export class OutgoingItem {
    productCode: string;
    classification: string;
    inventoryType: string;
    quantity: Number;
}