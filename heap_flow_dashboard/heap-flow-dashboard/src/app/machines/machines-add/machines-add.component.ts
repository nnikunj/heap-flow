import { Component, OnInit, Inject, Optional } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { ApiHandlerService } from 'src/app/services/api-handler.service';

import { Machine } from 'src/app/models/machine'

@Component({
  // selector: 'app-machines-add',
  selector: 'machines-add',
  templateUrl: './machines-add.component.html',
  styleUrls: ['./machines-add.component.scss']
})
export class MachinesAddComponent {

  machineForm: FormGroup;
  action = 'Add';

  constructor(public dialog: MatDialogRef<MachinesAddComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private apiService: ApiHandlerService,
    private snackBar: MatSnackBar) {
    if (data.get('action') === 'Update') {
      this.action = 'Update';
      let machine = data.get('machine') as Machine;
      this.machineForm = this.fb.group({
        serialNo: [machine.serialNo],
        name: [{ value: machine.name, disabled: true }],
        code: [{ value: machine.code, disabled: true }],
        model: [machine.model],
        category: [machine.category],
        kWKva: [machine.kWKva],
        make: [machine.make]
      });
    } else if (data.get('action') === 'Add') {
      this.machineForm = this.fb.group({
        serialNo: [''],
        name: ['', Validators.required],
        code: ['', Validators.required],
        model: [''],
        category: [''],
        kWKva: [''],
        make: ['']
      });
    }
  }

  onNoClick(): void {
    this.dialog.close();
  }


  onSubmit() {
    console.log(this.machineForm.value);

    let machine = new Machine();
    machine.serialNo = this.machineForm.get('serialNo').value;
    machine.name = this.machineForm.get('name').value;
    machine.code = this.machineForm.get('code').value;
    machine.model = this.machineForm.get('model').value;
    machine.category = this.machineForm.get('category').value;
    machine.kWKva = this.machineForm.get('kWKva').value;
    machine.make = this.machineForm.get('make').value;

    this.apiService.save('/api/v1/machines/add-update', machine)
      .subscribe(data => {
        console.log(data);
        this.openSnackBar('Save', 'Success');
        this.dialog.close('try');
      }, error => {
        console.error("error while saving item.");
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
