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

  constructor(public acceptMaterialModel: AcceptMaterial) {
  }

  ngOnInit(): void {

  }

  onClickSubmit(data) {
    console.log(data);
    console.log(this.acceptMaterialModel);
 }

}
