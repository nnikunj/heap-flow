import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { MatTable } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ApiHandlerService } from 'src/app/services/api-handler.service';
import { InventoryService } from 'src/app/services/inventory.service'
import { HttpService } from 'src/app/services/http.service'

import { AcceptMaterial, Item } from 'src/app/models/accept-material';
import { Vendor } from 'src/app/models/vendor'
import { InventoryItem } from 'src/app/models/inventory-item';

interface SelectInterface {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-accept-material',
  templateUrl: './accept-material.component.html',
  styleUrls: ['./accept-material.component.scss'],
  providers: [HttpService]
})
export class AcceptMaterialComponent implements OnInit {
  vendorNameControl = new FormControl('');
  materialAcceptDateControl = new FormControl(new Date());
  materialAcceptGRNControl = new FormControl('');
  materialAcceptInvoiceControl = new FormControl('');

  inventoryItemControl = new FormControl('');
  materialAcceptClassificationControl = new FormControl('');
  materialAcceptInventoryTypeControl = new FormControl('');
  materialAcceptQuantityControl = new FormControl();
  materialAcceptPriceyControl = new FormControl();

  inventory_url: string = "http://localhost:9443/api/v1/inventory-items/fetch-inventory-item-with-product-code/";

  vendorObj: Vendor;

  @ViewChild(MatTable) table: MatTable<any>;

  vendors = <any>[];
  acceptMaterialModel: AcceptMaterial;
  items: Item[] = [];
  inventoryItems = <any>[];

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

  constructor(private apiHandlerService: ApiHandlerService, private _snackBar: MatSnackBar,
    private inventoryService: InventoryService, private httpService: HttpService) {
  }

  ngOnInit(): void {
    this.vendorNameControl.valueChanges.subscribe(term => {
      if (typeof term === 'string' && term !== '') {
        this.httpService.search('http://localhost:9443/api/v1/vendors/fetch-vendors-with-name-like/', term).subscribe(data => {
          console.log(data);
          this.vendors = data as any[]
        }, (error) => {
          console.error(error);
        })
      }
    });

    this.inventoryItemControl.valueChanges.subscribe((term) => {
      if (typeof term === 'string' && term !== '') {
        this.httpService.search('http://localhost:9443/api/v1/inventory-items/fetch-inventory-items-list-like-id/', term).subscribe((res) => {
          console.log(res);
          this.inventoryItems = res;
        }, (error) => {
          console.error(error);
        })
      }
    });

    this.materialAcceptInventoryTypeControl.valueChanges.subscribe((term) => {
      console.log('inventory type changed ' + term);
      if (term === 'FOC') {
        this.materialAcceptPriceyControl.setValue(0);
        this.materialAcceptPriceyControl.disable();
      } else {
        this.materialAcceptPriceyControl.enable();
      }
    });

  }

  displayVendorName(vendor: Vendor): string {
    return vendor && vendor.name ? vendor.name : '';
  }

  displayInventoryItemCode(inventoryItem: InventoryItem): string {
    return inventoryItem && inventoryItem.inventoryItemCode ? inventoryItem.inventoryItemCode : '';
  }

  addMaterial(event: Event) {

    if (!this.inventoryItemControl.value.inventoryItemCode) {
      this.openSnackBar('Inventory item not selected', 'Please select inventory item');
      return;
    }

    if (!this.materialAcceptClassificationControl.value) {
      this.openSnackBar('Classification not selected', 'Please select classification');
      return;
    }

    if (this.materialAcceptQuantityControl.value < 0 || typeof this.materialAcceptQuantityControl.value === 'string'
      || this.materialAcceptQuantityControl.value === '') {
      this.openSnackBar('Quantity not entered', 'Please enter quantity');
      return;
    }

    if (this.materialAcceptPriceyControl.value < 0 || typeof this.materialAcceptPriceyControl.value === 'string'
      || this.materialAcceptPriceyControl.value === '') {
      this.openSnackBar('Price not entered', 'Please enter price');
      return;
    }

    if (!this.materialAcceptInventoryTypeControl.value) {
      this.openSnackBar('Inventory Type not selected', 'Please select inventory type');
      return;
    }

    let item = new Item();
    item.productCode = this.inventoryItemControl.value.inventoryItemCode;
    item.classification = this.materialAcceptClassificationControl.value;
    item.quantity = this.materialAcceptQuantityControl.value;
    item.pricePerUnit = this.materialAcceptPriceyControl.value;
    item.inventoryType = this.materialAcceptInventoryTypeControl.value;
    if (this.inventoryItemControl.value.descriptions && this.inventoryItemControl.value.descriptions.description) {
      item.description = this.inventoryItemControl.value.descriptions.description;
    }
    this.items.push(item);

    this.table.renderRows();

    this.resetSecondSection();
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000
    });
  }

  submitMaterial(event: Event) {
    if (!this.vendorNameControl.value.name) {
      this.openSnackBar('Vendor not selected', 'Please select valid vendor');
      return;
    }

    if (!this.materialAcceptInvoiceControl.value) {
      this.openSnackBar('Invoice not added', 'Please enter invoice number');
      return;
    }

    if (this.items.length === 0) {
      this.openSnackBar('No items are added', 'Please add some items');
      return;
    }

    let am = new AcceptMaterial();
    am.vendorCode = this.vendorNameControl.value.vendorId;
    am.recordDate = this.materialAcceptDateControl.value.toDateString();
    am.grn = this.materialAcceptGRNControl.value;
    am.invoice = this.materialAcceptInvoiceControl.value;

    am.incomingItemsList = this.items;

    console.log('am : ' + JSON.stringify(am));

    this.apiHandlerService.save('http://localhost:9443/api/v1/inventory/accept-materials', am).subscribe((data) => {
      console.log('response from save : ' + JSON.stringify(data));
      this.openSnackBar('Save', 'Success');
      this.resetForm(event);
    }, (error) => {
      console.error(error);
      this.openSnackBar('Save Failed', 'Please try again');
    });
  }

  resetForm(event: Event) {
    this.vendorNameControl.setValue('');
    this.materialAcceptDateControl.setValue(new Date());
    this.materialAcceptGRNControl.setValue('');
    this.materialAcceptInvoiceControl.setValue('');

    this.resetSecondSection();

    this.items = [];
    this.vendors = [];
    this.inventoryItems = [];
    this.table.renderRows();
  }

  resetSecondSection() {
    this.inventoryItemControl.setValue('');
    this.materialAcceptClassificationControl.setValue('');
    this.materialAcceptQuantityControl.setValue('');
    this.materialAcceptPriceyControl.setValue('');
    this.materialAcceptInventoryTypeControl.setValue('');
  }

  acceptMaterialFocusOut(event: Event) {
    console.log("focus out");
    if (typeof this.inventoryItemControl.value === 'string') {
      this.httpService.get('http://localhost:9443/api/v1/inventory-items/fetch-inventory-item-with-product-code/' + this.inventoryItemControl.value)
        .subscribe(res => {
          console.log(res.body);
          if (res.body) {
            this.inventoryItemControl.setValue(res.body, { emitEvent: false });
          }
        }, error => {
          console.error(error);
        })
    }
  }
}
