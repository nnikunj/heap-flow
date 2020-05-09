import { Component, OnInit } from '@angular/core';
import { AcceptMaterial } from 'src/app/models/accept-material';

@Component({
  selector: 'app-accept-material',
  templateUrl: './accept-material.component.html',
  styleUrls: ['./accept-material.component.scss']
})
export class AcceptMaterialComponent implements OnInit {


  acceptMaterialModel :AcceptMaterial;
  
  constructor() { }

  ngOnInit(): void {
  }

}
