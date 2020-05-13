import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { MatTable } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';


import { AcceptMaterial } from 'src/app/models/accept-material';
import { VendorService } from 'src/app/services/vendor.service';
import { Vendor } from 'src/app/models/vendor'
import { Item } from 'src/app/models/item';
import { ApiHandlerService } from 'src/app/services/api-handler.service';
import { InventoryService } from 'src/app/services/inventory.service'

interface SelectInterface {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-accept-material',
  templateUrl: './accept-material.component.html',
  styleUrls: ['./accept-material.component.scss'],
  providers: [VendorService]
})
export class AcceptMaterialComponent implements OnInit {
  vendorNameControl = new FormControl();
  materialAcceptDateControl = new FormControl((new Date()).toISOString());
  materialAcceptGRNControl = new FormControl();
  materialAcceptInvoiceControl = new FormControl();

  materialAcceptItemCodeControl = new FormControl();
  materialAcceptClassificationControl = new FormControl();
  materialAcceptInventoryTypeControl = new FormControl();
  materialAcceptQuantityControl = new FormControl();
  materialAcceptPriceyControl = new FormControl();

  inventory_url: string = "http://localhost:9443/api/v1/inventory-items/fetch-inventory-item-with-product-code/";

  vendorObj: Vendor;

  @ViewChild(MatTable) table: MatTable<any>;

  vendors = <any>[];
  acceptMaterialModel: AcceptMaterial;
  items: Item[] = [];

  inventoryTypes: SelectInterface[] = [
    { value: 'FOC', viewValue: 'FREE OF COST' },
    { value: 'PURCHASED', viewValue: 'PURCHASED' },
    { value: 'IMPORTED', viewValue: 'IMPORTED' },
    { value: 'REPAIRED_OR_REJECTED', viewValue: 'REPAIRED OR REJECTED' },
    { value: 'SERVICE_OR_AMC', viewValue: 'SERVICE OR AMC' },
    { value: 'CMC', viewValue: 'CMC' }
  ];

  classifications: SelectInterface[] = [
    { value: 'PROJECT', viewValue: 'PROJECT' },
    { value: 'R&D', viewValue: 'R&D' }
  ]

  displayedColumns: string[] = ['productCode', 'description', 'classification', 'inventoryType', 'quantity', 'pricePerUnit'];

  constructor(private vendorService: VendorService, private apiHandlerService: ApiHandlerService, private _snackBar: MatSnackBar,
    private inventoryService: InventoryService) {
  }

  ngOnInit(): void {
    this.vendorNameControl.valueChanges
      .subscribe(
        term => {

          if (term != '') {
            this.vendorService.search(term).subscribe(
              data => {
                console.log(data);
                this.vendors = data as any[]
              }
            )
          }
        }

      );

  }

  displayVendorName(vendor: Vendor): string {
    return vendor && vendor.name ? vendor.name : '';
  }

  addMaterial(event: Event) {
    this.inventoryService.search(this.materialAcceptItemCodeControl.value).subscribe((data: any) => {
      console.dir(data);
      if (data) {
        let item = new Item();
        item.productCode = this.materialAcceptItemCodeControl.value;
        item.classification = this.materialAcceptClassificationControl.value;
        item.quantity = this.materialAcceptQuantityControl.value;
        item.pricePerUnit = this.materialAcceptPriceyControl.value;
        item.inventoryType = this.materialAcceptInventoryTypeControl.value;
        if (data.descriptions && data.descriptions.description) {
          item.description = data.descriptions.description;
        }
        this.items.push(item);

        this.table.renderRows();
      } else {
        this.openSnackBar('Item not found', 'Please enter correct item code.');
      }
    });
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 10000,
    });
  }

  submitMaterial(event: Event) {
    let am = new AcceptMaterial();
    //am.vendorName = this.vendorNameControl.value.name;
    console.log('name ' + this.vendorNameControl.value.vendorId);
    am.vendorCode = this.vendorNameControl.value.vendorId;
    am.recordDate = this.materialAcceptDateControl.value.toDateString();
    am.grn = this.materialAcceptGRNControl.value;
    am.invoice = this.materialAcceptInvoiceControl.value;

    am.incomingItemsList = this.items;

    console.log('am : ' + JSON.stringify(am));
    console.dir(am);

    this.apiHandlerService.save('http://localhost:9443/api/v1/inventory/accept-materials', am).subscribe((data) => {
      console.log('response from save : ' + data);
      console.dir(data);
    }, (error) => {
      console.error(error);
    });
  }

  resetForm(event: Event) {
    this.vendorNameControl.setValue('');
    this.materialAcceptDateControl.setValue((new Date()).toISOString());
    this.materialAcceptGRNControl.setValue('');
    this.materialAcceptInvoiceControl.setValue('');

    this.materialAcceptItemCodeControl.setValue('');
    this.materialAcceptClassificationControl.setValue('');
    this.materialAcceptQuantityControl.setValue('');
    this.materialAcceptPriceyControl.setValue('');
    this.materialAcceptInventoryTypeControl.setValue('');

    this.items = [];
    this.table.renderRows();
  }
}
