import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
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

  acceptMaterialForm = this.fb.group({
    recordDate: [new Date(), Validators.required],
    vendorCode: ['', Validators.required],
    intentNumber: [''],
    grn: [''],
    invoice: ['', Validators.required],
    invoiceDate: [new Date()],
    poNumber: [''],
    poDate: [new Date()],
    loggedInUser: ['']
  });

  acceptMaterialItemForm = this.fb.group({
    pricePerUnit: ['', Validators.required],
    quantity: ['', Validators.required],
    inventoryType: ['', Validators.required],
    classification: ['', Validators.required],
    productCode: ['', Validators.required],
    baseUnitMeasure: [{ value: '', disabled: true }]
  });

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

  classificationDefault = 'PROJECT';
  inventoryTypeDefault = 'PURCHASED';

  displayedColumns: string[] = ['productCode', 'description', 'classification', 'inventoryType', 'quantity', 'baseUnitMeasure', 'pricePerUnit'];

  constructor(private apiHandlerService: ApiHandlerService, private _snackBar: MatSnackBar,
    private inventoryService: InventoryService, private httpService: HttpService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.acceptMaterialForm.get('vendorCode').valueChanges.subscribe(term => {
      if (typeof term === 'string' && term !== '') {
        this.httpService.search('http://localhost:9443/api/v1/vendors/fetch-vendors-with-name-like/', term).subscribe(data => {
          console.log(data);
          this.vendors = data as any[]
        }, (error) => {
          console.error(error);
        })
      }
    });

    this.acceptMaterialItemForm.get('productCode').valueChanges.subscribe((term) => {
      if (typeof term === 'string' && term !== '') {
        this.httpService.search('http://localhost:9443/api/v1/inventory-items/fetch-inventory-items-list-like-id/', term).subscribe((res) => {
          console.log(res);
          this.inventoryItems = res;
        }, (error) => {
          console.error(error);
        })
      }
    });

    this.acceptMaterialItemForm.get('inventoryType').valueChanges.subscribe((term) => {
      console.log('inventory type changed ' + term);
      if (term === 'FOC') {
        this.acceptMaterialItemForm.get('pricePerUnit').setValue(0);
        this.acceptMaterialItemForm.get('pricePerUnit').disable();
      } else {
        this.acceptMaterialItemForm.get('pricePerUnit').enable();
      }
    });

    this.acceptMaterialItemForm.get('classification').setValue(this.classificationDefault);
    this.acceptMaterialItemForm.get('inventoryType').setValue(this.inventoryTypeDefault);

  }

  displayVendorName(vendor: Vendor): string {
    return vendor && vendor.name ? vendor.name : '';
  }

  displayInventoryItemCode(inventoryItem: InventoryItem): string {
    return inventoryItem && inventoryItem.inventoryItemCode ? inventoryItem.inventoryItemCode : '';
  }

  public itemSelected = (event: any) => {
    if (this.acceptMaterialItemForm.get('productCode').value) {
      this.acceptMaterialItemForm.get('baseUnitMeasure').setValue(this.acceptMaterialItemForm.get('productCode').value.baseUnitMeasure);
    }
  }

  addMaterial() {
    let item = new Item();

    item.productCode = this.acceptMaterialItemForm.get('productCode').value.inventoryItemCode;
    item.classification = this.acceptMaterialItemForm.get('classification').value;
    item.quantity = this.acceptMaterialItemForm.get('quantity').value;
    item.pricePerUnit = this.acceptMaterialItemForm.get('pricePerUnit').value;
    item.inventoryType = this.acceptMaterialItemForm.get('inventoryType').value;
    if (this.acceptMaterialItemForm.get('productCode').value.descriptions && this.acceptMaterialItemForm.get('productCode').value.descriptions.description) {
      item.description = this.acceptMaterialItemForm.get('productCode').value.descriptions.description;
    }
    item.baseUnitMeasure = this.acceptMaterialItemForm.get('baseUnitMeasure').value;

    this.items.push(item);

    this.table.renderRows();

    this.resetAcceptMaterialItemForm();
  }

  resetAcceptMaterialItemForm() {
    this.acceptMaterialItemForm.reset();
    this.acceptMaterialItemForm.get('classification').setValue(this.classificationDefault);
    this.acceptMaterialItemForm.get('inventoryType').setValue(this.inventoryTypeDefault);
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000
    });
  }

  submitMaterial(event: Event) {
    if (!this.acceptMaterialForm.valid) {
      this.openSnackBar('Mandatory details not filled', 'Please enter Vendor and Invoice details.');
      return;
    }

    if (this.items.length === 0) {
      this.openSnackBar('No items are added', 'Please add some items');
      return;
    }

    let am = new AcceptMaterial();

    am.recordDate = this.acceptMaterialForm.get('recordDate').value.toDateString();
    am.vendorCode = this.acceptMaterialForm.get('vendorCode').value.vendorId;
    am.intentNumber = this.acceptMaterialForm.get('intentNumber').value;
    am.grn = this.acceptMaterialForm.get('grn').value;
    am.invoice = this.acceptMaterialForm.get('invoice').value;
    am.invoiceDate = this.acceptMaterialForm.get('invoiceDate').value.toDateString();
    am.poNumber = this.acceptMaterialForm.get('poNumber').value;
    am.poDate = this.acceptMaterialForm.get('poDate').value.toDateString();
    am.loggedInUser = this.acceptMaterialForm.get('loggedInUser').value;

    this.items.forEach(i => i.baseUnitMeasure = null);//base unit measure is just for display purpose.
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
    this.acceptMaterialForm.reset();
    this.resetAcceptMaterialItemForm();

    this.acceptMaterialForm.get('recordDate').setValue(new Date());
    this.acceptMaterialForm.get('invoiceDate').setValue(new Date());
    this.acceptMaterialForm.get('poDate').setValue(new Date());

    this.items = [];
    this.vendors = [];
    this.inventoryItems = [];
    this.table.renderRows();
  }

  acceptMaterialFocusOut(event: Event) {
    console.log("focus out");
    if (typeof this.acceptMaterialItemForm.get('productCode').value === 'string') {
      this.httpService.get('http://localhost:9443/api/v1/inventory-items/fetch-inventory-item-with-product-code/' + this.acceptMaterialItemForm.get('productCode').value)
        .subscribe(res => {
          console.log(res.body);
          if (res.body) {
            this.acceptMaterialItemForm.get('productCode').setValue(res.body, { emitEvent: false });
            this.acceptMaterialItemForm.get('baseUnitMeasure').setValue(res.body.baseUnitMeasure);
            console.log(this.acceptMaterialItemForm.get('productCode').value);
          }
        }, error => {
          console.error(error);
        })
    }
  }
}
