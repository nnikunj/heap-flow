import { Injectable } from '@angular/core';
@Injectable({
  providedIn: 'root'
})
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

}
