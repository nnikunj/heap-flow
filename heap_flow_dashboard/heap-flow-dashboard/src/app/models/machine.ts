import { Injectable } from '@angular/core';
import { Pageable, Sort } from './pageable';

@Injectable({
  providedIn: 'root'
})

export class MachineResponse {
  constructor() { }
  content: Machine[]

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

export class Machine {
  constructor() { }

  public id: Number;
  version: Number;
  modified: Date;
  creation: Date;
  serialNo: string;
  public name: string;
  code: string;
  model: string;
  make: string;
  category: string;
  kWKva: string;

}
