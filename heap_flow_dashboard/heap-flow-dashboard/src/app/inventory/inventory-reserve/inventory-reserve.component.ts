import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';
import { merge, fromEvent } from "rxjs";

import { InventoryReserveDatasource } from 'src/app/inventory/inventory-reserve/inventory-reserve.datasource'
import { InventoryReserveService } from 'src/app/inventory/inventory-reserve/inventory-reserve.service'

@Component({
  selector: 'app-inventory-reserve',
  templateUrl: './inventory-reserve.component.html',
  styleUrls: ['./inventory-reserve.component.scss']
})
export class InventoryReserveComponent implements OnInit, AfterViewInit {

  constructor(private _inventoryReserveService: InventoryReserveService) { }

  @ViewChild(MatPaginator) paginator: MatPaginator;

  dataSource: InventoryReserveDatasource;
  displayedColumns = ['itemCode', 'uom', 'descriptions', 'avialableQuantity', 'reservQuantity', 'category'];

  ngOnInit(): void {
    this.dataSource = new InventoryReserveDatasource(this._inventoryReserveService);
  }

  ngAfterViewInit(): void {
    this.dataSource.loadInventoryReserve(0, this.paginator.pageSize, this.paginator);

    merge(this.paginator.page)
      .pipe(
        tap(() => this.loadInventoryReserve())
      )
      .subscribe();
  }

  loadInventoryReserve() {
    this.dataSource.loadInventoryReserve(this.paginator.pageIndex, this.paginator.pageSize, this.paginator);
  }

}
