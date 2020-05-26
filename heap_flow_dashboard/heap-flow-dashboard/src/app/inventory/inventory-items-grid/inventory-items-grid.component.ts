import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";

import { merge, fromEvent } from "rxjs";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';

import { InventoryItemDatasource } from './inventory-items.datasource'
import { InventoryService } from 'src/app/services/inventory.service'

@Component({
  selector: 'app-inventory-items-grid',
  templateUrl: './inventory-items-grid.component.html',
  styleUrls: ['./inventory-items-grid.component.scss']
})
export class InventoryItemsGridComponent implements OnInit, AfterViewInit {

  dataSource: InventoryItemDatasource;

  displayedColumns = ['description', 'description2', 'description3', 'description4', 'description5', 'description6', 'baseUnitMeasure',
    'productGrpCode', 'genProductPostingGrp', 'itemCategoryCode', 'gstGrpCode', 'hsnSacCode', 'creation', 'modified'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  constructor(private inventoryService: InventoryService) { }


  ngOnInit(): void {
    this.dataSource = new InventoryItemDatasource(this.inventoryService);

  }

  ngAfterViewInit(): void {
    this.dataSource.loadInventoryItems('', 'asc', 0, 10, this.paginator);

    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadInventoryItems();
        })
      )
      .subscribe();

    merge(this.paginator.page)
      .pipe(
        tap(() => this.loadInventoryItems())
      )
      .subscribe();
  }

  loadInventoryItems() {
    this.dataSource.loadInventoryItems(
      this.input.nativeElement.value,
      'asc',
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.paginator);
  }



}
