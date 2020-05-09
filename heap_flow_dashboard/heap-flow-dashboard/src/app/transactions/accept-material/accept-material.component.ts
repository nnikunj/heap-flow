import { Component, OnInit } from '@angular/core';
import { AcceptMaterial } from 'src/app/models/accept-material';

@Component({
  selector: 'app-accept-material',
  templateUrl: './accept-material.component.html',
  styleUrls: ['./accept-material.component.scss']
})
export class AcceptMaterialComponent implements OnInit {


  acceptMaterialModel :AcceptMaterial;
  todayDate = new Date().toString;

  constructor(AcceptMaterial) {
    this.acceptMaterialModel = new AcceptMaterial();
    // this.acceptMaterialModel.vendor = data.vendor;
    this.acceptMaterialModel.recordDate = "";
    this.acceptMaterialModel.grn = "";
    this.acceptMaterialModel.invoice = "";

    this.acceptMaterialModel.quantity = 0;
    this.acceptMaterialModel.price = 0.00;
    this.acceptMaterialModel.classification = "";
    this.acceptMaterialModel.acceptingMaterialCode = "";

  }

  ngOnInit(): void {

  }

  onClickSubmit(data) {
    console.log(data);
    this.acceptMaterialModel.vendor = data.vendor;
    this.acceptMaterialModel.recordDate = data.recordDate;
    this.acceptMaterialModel.grn = data.grn;
    this.acceptMaterialModel.invoice = data.invoice;

    this.acceptMaterialModel.quantity = data.quantity;
    this.acceptMaterialModel.price = data.price;
    this.acceptMaterialModel.classification = data.classification;
    this.acceptMaterialModel.acceptingMaterialCode = data.acceptingMaterialCode;

    console.log(this.acceptMaterialModel);
 }

}
