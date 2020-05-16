import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
export class Vendor {
  constructor() { }
  public id: Number;
  version: Number;
  modified: Date;
  creation: Date;
  vendorId: string;
  public name: string;
  searchName: string;
  gstRegNo: string;
  panNumber: string;
  address: string;
  address2: string;
  city: string;
  stateCode: string;
  contactPerson: string;
  phone: string;
  email: string;

}
