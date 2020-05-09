import { Component, OnInit } from '@angular/core';
import { AcceptMaterial } from 'src/app/models/accept-material';

@Component({
  selector: 'app-accept-material',
  templateUrl: './accept-material.component.html',
  styleUrls: ['./accept-material.component.scss']
})
export class AcceptMaterialComponent implements OnInit {
  //acceptMaterialModel :AcceptMaterial;
  todayDate = new Date().toString;
  addedRecords = [];

  constructor(public acceptMaterialModel: AcceptMaterial) {
  }

    ngOnInit(): void {

    }

    acceptRecord() {
      //This will call HTTP POST
    }

    onClickSubmit(data) {
      console.log(data);
      console.log(this.acceptMaterialModel);
      this.addRecordToTable(this.acceptMaterialModel);
      //this.resetForm();
    }

    addRecordToTable(formData) {
      this.addedRecords.push(formData);
      console.log("#####", this.addedRecords);
    }

    resetForm() {
      this.acceptMaterialModel.vendor.id = "";
      this.acceptMaterialModel.recordDate = "";
      this.acceptMaterialModel.grn = "";
      this.acceptMaterialModel.invoice = "";
      this.acceptMaterialModel.acceptingMaterialCode = "";
      this.acceptMaterialModel.classification = "";
      this.acceptMaterialModel.price = 0.00;
      this.acceptMaterialModel.quantity = 0;
    }
}
