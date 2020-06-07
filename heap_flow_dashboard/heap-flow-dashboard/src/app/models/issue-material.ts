import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class IssueMaterial {
    issueSlipNumber: string;
    recordDate: string;
    machineCode: string;
    issuedViaEmp: string;
    issuedForDept: string;
    approvedBy: string;
    loggedInUser : string;

    outgoingItemsList: OutgoingItem[];
}

export class OutgoingItem {
    productCode: string;
    classification: string;
    inventoryType: string;
    quantity: Number;
    baseUnitMeasure: string; //This is just for display purpose. This field should not be sent in post request.
}