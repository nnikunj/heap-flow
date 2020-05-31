import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";

import { merge, fromEvent } from "rxjs";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';

import { InventorySummaryDatasource } from './inventory-summary.datasource'
import { InventoryService } from 'src/app/services/inventory.service'

@Component({
  selector: 'app-inventory-summary',
  templateUrl: './inventory-summary.component.html',
  styleUrls: ['./inventory-summary.component.scss']
})
export class InventorySummaryComponent implements OnInit, AfterViewInit {

  dataSource: InventorySummaryDatasource;

  displayedColumns = ['inventoryItemCode', 'itemCategoryCode', 'description', 'description2', 'type', 'quantity', 'baseUnitMeasure',
    'averageUnitPrice', 'value', 'creation', 'modified'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  constructor(private inventoryService: InventoryService) { }

  ngOnInit(): void {
    this.dataSource = new InventorySummaryDatasource(this.inventoryService);
  }

  ngAfterViewInit(): void {
    this.dataSource.loadInventorySummaryItems('', 'asc', 0, 10, this.paginator);

    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadInventorySummaryItems();
        })
      )
      .subscribe();

    merge(this.paginator.page)
      .pipe(
        tap(() => this.loadInventorySummaryItems())
      )
      .subscribe();
  }

  loadInventorySummaryItems() {
    this.dataSource.loadInventorySummaryItems(
      this.input.nativeElement.value,
      'asc',
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.paginator);
  }

  logData(row: any) {
    console.dir(row);
  }



}
