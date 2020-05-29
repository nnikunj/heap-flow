import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { InventoryItem, Descriptions } from 'src/app/models/inventory-item'

import { ApiHandlerService } from 'src/app/services/api-handler.service';

@Component({
  selector: 'app-add-inventory-item',
  templateUrl: './add-inventory-item.component.html',
  styleUrls: ['./add-inventory-item.component.scss']
})
export class AddInventoryItemComponent implements OnInit {

  // inventoryItemForm  = new FormGroup({
  //   inventoryItemCode : new FormControl(),
  //   description : new FormControl(),
  //   description2 : new FormControl(),
  //   description3 : new FormControl(),
  //   description4 : new FormControl(),
  //   description5 : new FormControl(),
  //   description6 : new FormControl(),
  //   baseUnitMeasure : new FormControl(),
  //   productGrpCode : new FormControl(),
  //   genProductPostingGrp : new FormControl(),
  //   itemCategoryCode : new FormControl(),
  //   gstGrpCode : new FormControl(),
  //   hsnSacCode : new FormControl()
  // });

  inventoryItemForm = this.fb.group({
    inventoryItemCode: ['', Validators.required],
    description: ['', Validators.required],
    description2: ['', Validators.required],
    description3: ['', Validators.required],
    description4: ['', Validators.required],
    description5: ['', Validators.required],
    description6: ['', Validators.required],
    baseUnitMeasure: ['', Validators.required],
    productGrpCode: ['', Validators.required],
    genProductPostingGrp: ['', Validators.required],
    itemCategoryCode: ['', Validators.required],
    gstGrpCode: ['', Validators.required],
    hsnSacCode: ['', Validators.required]
  });




  constructor(private fb: FormBuilder, private apiHandlerService: ApiHandlerService, private _snackBar: MatSnackBar, ) {

  }

  // setInventoryItem(inventoryItem : InventoryItem){
  //   this.inventoryItemCode = new FormControl(inventoryItem.inventoryItemCode);

  //   let descriptions = inventoryItem.descriptions;
  //   this.description = new FormControl(descriptions.description);
  //   this.description2 = new FormControl(descriptions.description2);
  //   this.description3 = new FormControl(descriptions.description3);
  //   this.description4 = new FormControl(descriptions.description4);
  //   this.description5 = new FormControl(descriptions.description5);
  //   this.description6 = new FormControl(descriptions.description6);

  //   this.baseUnitMeasure = new FormControl(inventoryItem.baseUnitMeasure);
  //   this.productGrpCode = new FormControl(inventoryItem.productGrpCode);
  //   this.genProductPostingGrp = new FormControl(inventoryItem.genProductPostingGrp);
  //   this.itemCategoryCode = new FormControl(inventoryItem.itemCategoryCode);
  //   this.gstGrpCode = new FormControl(inventoryItem.gstGrpCode);
  //   this.hsnSacCode = new FormControl(inventoryItem.hsnSacCode);
  // }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.inventoryItemForm.value);

    let item = new InventoryItem();
    item.inventoryItemCode = this.inventoryItemForm.get('inventoryItemCode').value;

    let descriptions = new Descriptions();
    descriptions.description = this.inventoryItemForm.get('description').value;
    descriptions.description2 = this.inventoryItemForm.get('description2').value;
    descriptions.description3 = this.inventoryItemForm.get('description3').value;
    descriptions.description4 = this.inventoryItemForm.get('description4').value;
    descriptions.description5 = this.inventoryItemForm.get('description5').value;
    descriptions.description6 = this.inventoryItemForm.get('description6').value;

    item.descriptions = descriptions;

    item.baseUnitMeasure = this.inventoryItemForm.get('baseUnitMeasure').value;
    item.productGrpCode = this.inventoryItemForm.get('productGrpCode').value;
    item.genProductPostingGrp = this.inventoryItemForm.get('genProductPostingGrp').value;
    item.itemCategoryCode = this.inventoryItemForm.get('itemCategoryCode').value;
    item.gstGrpCode = this.inventoryItemForm.get('gstGrpCode').value;
    item.hsnSacCode = this.inventoryItemForm.get('hsnSacCode').value;

    this.apiHandlerService.save('http://localhost:9443/api/v1/inventory-items/modify-item', item)
      .subscribe(data => {
        console.log(data);
        this.openSnackBar('Save', 'Success');
        this.inventoryItemForm.reset();
      }, error => {
        console.error("error while saving item.");
        console.error(error);
        this.openSnackBar('Save Failed', 'Please try again');
      });


  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000
    });
  }

}
