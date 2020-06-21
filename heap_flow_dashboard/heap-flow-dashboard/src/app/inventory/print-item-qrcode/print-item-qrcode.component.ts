import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { DOCUMENT } from '@angular/common'

import { InventoryItem } from 'src/app/models/inventory-item';

@Component({
    selector: 'print-item-qrcode',
    templateUrl: `./print-item-qrcode.component.html`
})
export class PrintItemQRCode {
    pdfSrc: string;

    constructor(public dialogRef: MatDialogRef<PrintItemQRCode>,
        @Inject(MAT_DIALOG_DATA) data: InventoryItem,
        @Inject(DOCUMENT) document: any) {
        this.pdfSrc = 'http://localhost:8443/api/rpts/inventory/fetch-item-qrcode/' + btoa(data.inventoryItemCode);
    }

    loadComplete() {
        setTimeout(function () { window.print(); }, 300);
    }

}