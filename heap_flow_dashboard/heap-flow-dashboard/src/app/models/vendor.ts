import { Injectable } from '@angular/core';
import { Pageable, Sort } from './pageable';

@Injectable({
  providedIn: 'root'
})

export class VendorResponse{
  content : Vendor[];

  pageable: Pageable;
  totalElements: Number;
  totalPages: Number;
  last: boolean;
  number: Number;
  size: Number;
  sort: Sort;
  numberOfElements: Number;
  first: boolean;
  empty: boolean;
}

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
