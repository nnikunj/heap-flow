import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, Inject } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";
//import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { merge, fromEvent } from "rxjs";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';

import { ApiHandlerService } from 'src/app/services/api-handler.service';

import { AcceptMaterial } from 'src/app/models/accept-material-list'
import { AcceptMaterialDatasource } from './accept-material-list.datasource';
import { TransactionService } from 'src/app/services/transactions.service';

@Component({
  selector: 'accept-material-list',
  templateUrl: './accept-material-list.component.html',
  styleUrls: ['./accept-material-list.component.scss']
})
export class AcceptMaterialListComponent implements OnInit, AfterViewInit {

  constructor(private dialog: MatDialog, private transactionService: TransactionService, private apiService: ApiHandlerService) { }

  dataSource: AcceptMaterialDatasource;
  displayedColumns = ["action", "recievedDate", "inventoryType", "incomingQuantity", "pricePerUnit", "incomingMaterial", "classificationCategory", "invoiceNumber", "invoiceDate", "poNumber", "poDate", "intdentNumber", "materialAcceptedBy", "grnNumber", "department", "vendor", "incomingMaterialDescription", "uom", "entryCreatedOnDate"];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  ngOnInit(): void {
    this.dataSource = new AcceptMaterialDatasource(this.transactionService);
  }
  ngAfterViewInit(): void {
    this.dataSource.loadAcceptMaterials('', 0, 10, this.paginator);
    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadAcceptMaterials();
        })
      )
      .subscribe();

    merge(this.paginator.page)
      .pipe(
        tap(() => this.loadAcceptMaterials())
      )
      .subscribe();
  }

  loadAcceptMaterials() {
    this.dataSource.loadAcceptMaterials(this.input.nativeElement.value, this.paginator.pageIndex, this.paginator.pageSize, this.paginator);
  }

  openDialog(action: string, acceptMaterial: AcceptMaterial) {
    console.log('accept mat : ' + action);
    const dialogRef = this.dialog.open(AcceptMaterialDeleteDialog, {
      width: '350px',
      data: acceptMaterial
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result) {
        this.apiService.delete('/api/v1/inventory/cancel-accepted-mat/' + acceptMaterial.dbId)
          .subscribe(data => {
            console.log(data);
            this.loadAcceptMaterials();
          })
      }
      
    });
  }

}

@Component({
  selector: 'accept-material-delete',
  templateUrl: './accept-material-delete.dialog.html',
})
export class AcceptMaterialDeleteDialog {

  constructor(
    public dialogRef: MatDialogRef<AcceptMaterialDeleteDialog>,
    @Inject(MAT_DIALOG_DATA) public data: AcceptMaterial) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}