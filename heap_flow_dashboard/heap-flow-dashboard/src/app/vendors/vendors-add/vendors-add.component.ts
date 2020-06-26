import { Component, OnInit, Inject, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ApiHandlerService } from 'src/app/services/api-handler.service';

import { Vendor } from 'src/app/models/vendor'

@Component({
  selector: 'app-vendors-add',
  templateUrl: './vendors-add.component.html',
  styleUrls: ['./vendors-add.component.scss']
})
export class VendorsAddComponent implements OnInit {

  vendorForm: FormGroup;
  action = 'Add';

  constructor(public dialog: MatDialogRef<VendorsAddComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private apiService: ApiHandlerService,
    private snackBar: MatSnackBar) {
    if (data.get('action') === 'Update') {
      this.action = 'Update';
      let vendor = data.get('vendor') as Vendor;
      this.vendorForm = this.fb.group({
        vendorId: [vendor.vendorId],
        name: [{ value: vendor.name, disabled: true }],
        searchName: [vendor.searchName],
        gstRegNo: [vendor.gstRegNo],
        address: [vendor.address],
        address2: [vendor.address2],
        city: [vendor.city],
        contactPerson: [vendor.contactPerson],
        email: [vendor.email],
        panNumber: [vendor.panNumber],
        phone: [vendor.phone],
        stateCode: [vendor.stateCode]
      });
    } else if (data.get('action') === 'Add') {
      this.vendorForm = this.fb.group({
        vendorId: [''],
        name: ['', Validators.required],
        searchName: ['', Validators.required],
        gstRegNo: [''],
        address: [''],
        address2: [''],
        city: [''],
        contactPerson: [''],
        email: [''],
        panNumber: [''],
        phone: [''],
        stateCode: ['']
      });
    }
  }

  ngOnInit(): void {
  }

  onNoClick(): void {
    this.dialog.close();
  }

  onSubmit() {
    console.log(this.vendorForm.value);

    let vendor = new Vendor();
    vendor.vendorId = this.vendorForm.get('vendorId').value;
    vendor.name = this.vendorForm.get('name').value;
    vendor.searchName = this.vendorForm.get('searchName').value;
    vendor.gstRegNo = this.vendorForm.get('gstRegNo').value;
    vendor.address = this.vendorForm.get('address').value;
    vendor.address2 = this.vendorForm.get('address2').value;
    vendor.city = this.vendorForm.get('city').value;
    vendor.contactPerson = this.vendorForm.get('contactPerson').value;
    vendor.email = this.vendorForm.get('email').value;
    vendor.panNumber = this.vendorForm.get('panNumber').value;
    vendor.phone = this.vendorForm.get('phone').value;
    vendor.stateCode = this.vendorForm.get('stateCode').value;


    this.apiService.save('/api/v1/vendors/add-update', vendor)
      .subscribe(data => {
        console.log(data);
        this.openSnackBar('Save', 'Success');
        this.dialog.close('try');
      }, error => {
        console.error("error while saving vendor.");
        console.error(error);
        this.openSnackBar('Save Failed', 'Please try again');
      });

  }

  openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 5000
    });
  }

}
