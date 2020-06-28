import { Component, Inject, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { DOCUMENT } from '@angular/common'
import { DomSanitizer, SafeUrl, SafeHtml } from '@angular/platform-browser';

import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';

import { InventoryItem } from 'src/app/models/inventory-item';

@Component({
    selector: 'print-item-qrcode',
    templateUrl: `./print-item-qrcode.component.html`
})
export class PrintItemQRCode {
    // pdfSrc: SafeUrl;
    // pdfSrc: string;

    @ViewChild('pdfViewer') public pdfViewer;

    constructor(public dialogRef: MatDialogRef<PrintItemQRCode>,
        @Inject(MAT_DIALOG_DATA) data: InventoryItem,
        @Inject(DOCUMENT) document: any,
        private sanitizer: DomSanitizer,
        private http: HttpClient) {
        // this.pdfSrc = this.sanitizer.bypassSecurityTrustResourceUrl('/api/rpts/inventory/fetch-item-qrcode/' + btoa(data.inventoryItemCode));
        // this.pdfSrc = '/api/rpts/inventory/fetch-item-qrcode/' + btoa(data.inventoryItemCode);
        // console.log(this.pdfSrc);
        // this.setInnerHtml('/api/rpts/inventory/fetch-item-qrcode/' + btoa(data.inventoryItemCode));

        let url = '/api/rpts/inventory/fetch-item-qrcode/' + btoa(data.inventoryItemCode);
        this.downloadFile(url).subscribe(
            (res) => {
                this.pdfViewer.pdfSrc = res; // pdfSrc can be Blob or Uint8Array
                this.pdfViewer.refresh(); // Ask pdf viewer to load/refresh pdf
            }
        );
    }

    // loadComplete() {
    //     setTimeout(function () { window.print(); }, 300);
    // }

    // getPDFUrl() {
    //     // console.log(this.pdfSrc)
    //     // console.log(this.sanitizer.bypassSecurityTrustUrl(this.pdfSrc))
    //     // return this.sanitizer.bypassSecurityTrustUrl(this.pdfSrc);
    //     // return this.pdfSrc;
    // }

    // public innerHtml: SafeHtml;
    // public setInnerHtml(pdfurl: string) {
    //     this.innerHtml = this.sanitizer.bypassSecurityTrustHtml(
    //         // "<object data='" + pdfurl + "' type='application/pdf' class='embed-responsive-item'></object>"
    //         "<embed #embed type='application/pdf' src='" + pdfurl + "' width='auto' height='auto' onload='window.print()' onclick='alert(2)'>"
    //     );
    // }

    private downloadFile(url: string): any {
        return this.http.get(url, { responseType: 'blob' })
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

}