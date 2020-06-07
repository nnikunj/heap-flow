import { Component, Inject, Optional } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ApiHandlerService } from 'src/app/services/api-handler.service';

import { InventoryItem, Descriptions } from 'src/app/models/inventory-item'

@Component({
  selector: 'add-inventory-dialog',
  templateUrl: './add-inventory-dialog.component.html',
  styleUrls: ['./add-inventory-dialog.component.scss']
})
export class AddInventoryDialog {

  inventoryItemForm: FormGroup;
  action = 'Add';

  constructor(
    public dialogRef: MatDialogRef<AddInventoryDialog>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private apiHandlerService: ApiHandlerService,
    private _snackBar: MatSnackBar) {

    if (data && data.get('action') === 'Update') {
      this.action = data.get('action');
      let item: InventoryItem;
      item = data.get('item') as InventoryItem;
      let description = item.descriptions as Descriptions;
      this.inventoryItemForm = this.fb.group({
        inventoryItemCode: [{value:item.inventoryItemCode, disabled: true}],
        description: [description.description, Validators.required],
        description2: [description.description2],
        description3: [description.description3],
        description4: [description.description4],
        description5: [description.description5],
        description6: [description.description6],
        baseUnitMeasure: [item.baseUnitMeasure, Validators.required],
        productGrpCode: [item.productGrpCode],
        genProductPostingGrp: [item.genProductPostingGrp],
        itemCategoryCode: [item.itemCategoryCode],
        gstGrpCode: [item.gstGrpCode],
        hsnSacCode: [item.hsnSacCode],
        reserveQuantAlert: [item.reserveQuantAlert],
        reOrderQuant: [item.reOrderQuant],
        maxOrderQuant: [item.maxOrderQuant]
      });
    } else {
      this.inventoryItemForm = this.fb.group({
        inventoryItemCode: ['', Validators.required],
        description: ['', Validators.required],
        description2: [''],
        description3: [''],
        description4: [''],
        description5: [''],
        description6: [''],
        baseUnitMeasure: ['', Validators.required],
        productGrpCode: [''],
        genProductPostingGrp: [''],
        itemCategoryCode: [''],
        gstGrpCode: [''],
        hsnSacCode: [''],
        reserveQuantAlert: [''],
        reOrderQuant: [''],
        maxOrderQuant: ['']
      });
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
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
    item.reserveQuantAlert = this.inventoryItemForm.get('reserveQuantAlert').value;
    item.maxOrderQuant = this.inventoryItemForm.get('maxOrderQuant').value;
    item.reOrderQuant = this.inventoryItemForm.get('reOrderQuant').value;

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

      this.dialogRef.close();
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 5000
    });
  }


}