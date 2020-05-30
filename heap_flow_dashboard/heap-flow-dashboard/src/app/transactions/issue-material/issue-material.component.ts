import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { MatTable } from '@angular/material/table';
import { MatSnackBar } from '@angular/material/snack-bar';

import { HttpService } from 'src/app/services/http.service'
import { ApiHandlerService } from 'src/app/services/api-handler.service';

import { Machine } from 'src/app/models/machine'
import { InventoryItem } from 'src/app/models/inventory-item';


import { IssueMaterial, OutgoingItem } from 'src/app/models/issue-material'

class SelectInterface {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-issue-material',
  templateUrl: './issue-material.component.html',
  styleUrls: ['./issue-material.component.scss'],
  providers: [HttpService]
})
export class IssueMaterialComponent implements OnInit {
  
  issueMaterialForm = this.fb.group({
    recordDate: [new Date(), Validators.required],
    machineCode: ['', Validators.required],
    issuedViaEmp: ['', Validators.required],
    issuedForDept: [''],
    approvedBy: [''],
    loggedInUser:['']
  });

  issueMaterialItemForm = this.fb.group({
    quantity: ['', Validators.required],
    inventoryType:['', Validators.required],
    classification:['', Validators.required],
    productCode:['', Validators.required]
  });

  // machineCodeControl = new FormControl('');
  // issueMaterialDateControl = new FormControl(new Date());
  // issueMaterialEMPControl = new FormControl('');

  // issueMaterialCodeControl = new FormControl('');
  // issueMaterialClassificationControl = new FormControl('');
  // issueMaterialInventoryTypeControl = new FormControl('');
  // issueMaterialQuantityControl = new FormControl('');

  machines = <any>[];
  inventoryItems = <any>[];

  //classifications : SelectInterface[] = [];
  inventoryTypes: SelectInterface[] = [];

  outgoingItemList: OutgoingItem[] = [];
  issueMaterial: IssueMaterial;

  machine_search_url: string = 'http://localhost:9443/api/v1/machines/fetch-machines-with-code-like/';
  item_search_url: string = 'http://localhost:9443/api/v1/inventory-items/fetch-inventory-items-list-like-id/';

  inventoryTypeMap = new Map();

  classifications: SelectInterface[] = [
    { value: 'PROJECT', viewValue: 'PROJECT' },
    { value: 'R&D', viewValue: 'R&D' }
  ];

  classificationDefault = 'PROJECT';

  issueMaterialColumns = ['productCode', 'classification', 'inventoryType', 'quantity'];

  @ViewChild(MatTable) table: MatTable<any>;


  constructor(private httpService: HttpService, private _snackBar: MatSnackBar, private apiHandlerService: ApiHandlerService,
    private fb : FormBuilder) { }

  ngOnInit(): void {
    this.issueMaterialForm.get('machineCode').valueChanges.subscribe((term) => {
      if (typeof term === 'string' && term !== '') {
        this.httpService.search(this.machine_search_url, term).subscribe((data) => {
          console.log(data);
          this.machines = data as any[];
        }, (error) => {
          console.error(error);
        });
      }
    });

    this.issueMaterialItemForm.get('productCode').valueChanges.subscribe((term) => {
      if (typeof term === 'string' && term !== '') {
        this.httpService.search(this.item_search_url, term).subscribe((data) => {
          console.log(data);
          this.inventoryItems = data as any[];
        }, (error) => {
          console.error(error);
        });
      }
    });

    this.inventoryTypeMap.set("FOC", "FREE OF COST");
    this.inventoryTypeMap.set("PURCHASED", "PURCHASED");
    this.inventoryTypeMap.set("IMPORTED", "IMPORTED");
    this.inventoryTypeMap.set("REPAIRED_OR_REJECTED", "REPAIRED OR REJECTED");
    this.inventoryTypeMap.set("SERVICE_OR_AMC", "SERVICE OR AMC");
    this.inventoryTypeMap.set("CMC", "CMC");

    this.issueMaterialItemForm.get('classification').setValue(this.classificationDefault);

  }

  displayMachineCode(machine: Machine) {
    return machine && machine.code ? machine.code : '';
  }

  displayIssueMaterialCode(inventoryItem: InventoryItem) {
    return inventoryItem && inventoryItem.inventoryItemCode ? inventoryItem.inventoryItemCode : '';
  }

  public itemSelected = (event: any) => {
    if (this.issueMaterialItemForm.get('productCode').value && this.issueMaterialItemForm.get('productCode').value.stokcs) {
      this.updateInventoryType(this.issueMaterialItemForm.get('productCode').value.stokcs);
    }
  }

  updateInventoryType(stocks) {
    this.inventoryTypes = [];
    console.log(stocks);
    if (stocks) {
      stocks.forEach(s => {
        let i = new SelectInterface();
        i.value = s.inventoryTypeName;
        i.viewValue = this.inventoryTypeMap.get(s.inventoryTypeName);

        this.inventoryTypes.push(i);
      });
    }
  }

  addMaterial(event: Event) {

    // if (typeof this.issueMaterialCodeControl.value !== 'object') {
    //   this.openSnackBar('Material not selected', 'Please select a valid material');
    //   return;
    // }

    // if (!this.issueMaterialClassificationControl.value) {
    //   this.openSnackBar('Classification Not seleted', 'Please select classification');
    //   return;
    // }

    // if (!this.issueMaterialInventoryTypeControl.value) {
    //   this.openSnackBar('Inventory Type Not seleted', 'Please select inventory type');
    //   return;
    // }

    // if (this.issueMaterialQuantityControl.value < 0 || typeof this.issueMaterialQuantityControl.value === 'string'
    //   || this.issueMaterialQuantityControl.value === '') {
    //   this.openSnackBar('Quantity not entered', 'Please enter quantity');
    //   return;
    // }

    if (this.issueMaterialItemForm.get('quantity').value > 0) {
      let quantityValue = this.issueMaterialItemForm.get('quantity').value;
      for (let index = 0; index < this.issueMaterialItemForm.get('productCode').value.stokcs.length; index++) {
        let s = this.issueMaterialItemForm.get('productCode').value.stokcs[index];
        if (s.inventoryTypeName === this.issueMaterialItemForm.get('inventoryType').value) {
          if (quantityValue > s.quantity) {
            this.openSnackBar('Quantity entered is more than stock', 'Stock value ' + s.quantity );
            return;
          }
        }
      }
    }

    let outgoingItem = new OutgoingItem();

    // outgoingItem.productCode = this.issueMaterialCodeControl.value.inventoryItemCode;
    // outgoingItem.classification = this.issueMaterialClassificationControl.value;
    // outgoingItem.inventoryType = this.issueMaterialInventoryTypeControl.value;
    // outgoingItem.quantity = this.issueMaterialQuantityControl.value;

    outgoingItem.productCode = this.issueMaterialItemForm.get('productCode').value.inventoryItemCode;
    outgoingItem.classification = this.issueMaterialItemForm.get('classification').value;
    outgoingItem.inventoryType = this.issueMaterialItemForm.get('inventoryType').value;
    outgoingItem.quantity = this.issueMaterialItemForm.get('quantity').value;

    this.outgoingItemList.push(outgoingItem);

    this.table.renderRows();

    this.resetIssueMaterialItemForm();
  }

  resetIssueMaterialItemForm(){
    this.issueMaterialItemForm.reset();
    this.issueMaterialItemForm.get('classification').setValue(this.classificationDefault);
  }

  submitIssueMaterial(event: Event) {
    // if (typeof this.machineCodeControl.value !== 'object') {
    //   this.openSnackBar('Machine not selected', 'Please select a valid machine');
    //   return;
    // }

    // if (!this.issueMaterialEMPControl.value) {
    //   this.openSnackBar('EMP code not entered', 'Please enter EMP code');
    //   return;
    // }

    if(!this.issueMaterialForm.valid){
      this.openSnackBar('Please enter mandatory field.', 'Please enter Machine code and Employee.');
      return;
    }

    if (this.outgoingItemList.length === 0) {
      this.openSnackBar('Material not added', 'Please add some material');
      return;
    }

    let issueMaterial = new IssueMaterial();

    // issueMaterial.machineCode = this.machineCodeControl.value.code;
    // issueMaterial.recordDate = this.issueMaterialDateControl.value.toDateString();
    // issueMaterial.issuedViaEmp = this.issueMaterialEMPControl.value;
    issueMaterial.machineCode = this.issueMaterialForm.get('machineCode').value.code;
    issueMaterial.recordDate = this.issueMaterialForm.get('recordDate').value.toDateString();
    issueMaterial.issuedViaEmp = this.issueMaterialForm.get('issuedViaEmp').value;
    issueMaterial.issuedForDept = this.issueMaterialForm.get('issuedForDept').value;
    issueMaterial.approvedBy = this.issueMaterialForm.get('approvedBy').value;
    issueMaterial.loggedInUser = this.issueMaterialForm.get('loggedInUser').value;

    issueMaterial.outgoingItemsList = this.outgoingItemList;

    this.apiHandlerService.save('http://localhost:9443/api/v1/inventory/issue-materials', issueMaterial).subscribe((res) => {
      this.openSnackBar('Save', 'successful');
      this.resetForm(new Event('reset'));
    }, (error) => {
      console.error(error);
      this.openSnackBar('Save failed', 'Please try again');
    })


  }

  resetForm(event: Event) {
    this.issueMaterialForm.reset()
    this.resetIssueMaterialItemForm();

    this.issueMaterialForm.get('recordDate').setValue(new Date());

    this.inventoryTypes = [];
    this.inventoryItems = [];
    this.outgoingItemList = [];

    this.table.renderRows();

  }

  issueMaterialFocusOut(event: Event) {
    console.log("focus out");
    if (typeof this.issueMaterialItemForm.get('productCode').value === 'string') {
      this.httpService.get('http://localhost:9443/api/v1/inventory-items/fetch-inventory-item-with-product-code/' + this.issueMaterialItemForm.get('productCode').value)
        .subscribe(res => {
          console.log(res.body);
          if (res.body) {
            this.issueMaterialItemForm.get('productCode').setValue(res.body, { emitEvent: false });
            this.updateInventoryType(res.body.stokcs);
          }
        }, error => {
          console.error(error);
        })
    }

  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000,
    });
  }

}
