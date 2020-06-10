import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";
//import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { merge, fromEvent } from "rxjs";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';

import { InventoryItemDatasource } from './inventory-items.datasource'
import { InventoryService } from 'src/app/services/inventory.service'
import { AddInventoryDialog } from 'src/app/inventory/add-inventory-item/add-inventory-dialog.component'
import { PrintItemQRCode } from 'src/app/inventory/print-item-qrcode/print-item-qrcode.component'

@Component({
  selector: 'app-inventory-items-grid',
  templateUrl: './inventory-items-grid.component.html',
  styleUrls: ['./inventory-items-grid.component.scss']
})
export class InventoryItemsGridComponent implements OnInit, AfterViewInit {

  dataSource: InventoryItemDatasource;

  displayedColumns = ['action', 'inventoryItemCode','description', 'description2', 'description3', 'description4', 'description5', 'description6', 'baseUnitMeasure',
    'productGrpCode', 'genProductPostingGrp', 'itemCategoryCode', 'gstGrpCode', 'hsnSacCode', 'creation', 'modified'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  constructor(private inventoryService: InventoryService,
     private dialog : MatDialog) { }


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

  logData(row: any){
    console.dir(row);
  }

  openDialog(action : any, item : any) {
    let obj = new Map();
    obj.set('action', action);
    obj.set('item', item);

    const dialogRef = this.dialog.open(AddInventoryDialog, {
      data:obj
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadInventoryItems();
      if(result.event == 'Add'){
        //this.addRowData(result.data);
      }else if(result.event == 'Update'){
        //this.updateRowData(result.data);
      }else if(result.event == 'Delete'){
        //this.deleteRowData(result.data);
      }
    });
  }

  print(item : any){
    const dialogRef = this.dialog.open(PrintItemQRCode, {
      data:item
    });

    dialogRef.afterClosed().subscribe(result => {
      
    });
   
  }

  // addRowData(row_obj){
  //   var d = new Date();
  //   this.dataSource.push({
  //     id:d.getTime(),
  //     name:row_obj.name
  //   });
  //   this.table.renderRows();
    
  // }
  // updateRowData(row_obj){
  //   this.dataSource = this.dataSource.filter((value,key)=>{
  //     if(value.id == row_obj.id){
  //       value.name = row_obj.name;
  //     }
  //     return true;
  //   });
  // }
  // deleteRowData(row_obj){
  //   this.dataSource = this.dataSource.filter((value,key)=>{
  //     return value.id != row_obj.id;
  //   });
  // }


}
