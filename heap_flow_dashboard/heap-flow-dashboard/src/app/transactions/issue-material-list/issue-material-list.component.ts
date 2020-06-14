import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, Inject, NgModule } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";
//import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { merge, fromEvent } from "rxjs";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';

import { ApiHandlerService } from 'src/app/services/api-handler.service';

import { IssueMaterial } from 'src/app/models/issue-material-list'
import { IssueMaterialDatasource } from './issue-material-list.datasource';
import { TransactionService } from 'src/app/services/transactions.service';

@Component({
  selector: 'issue-material-list',
  templateUrl: './issue-material-list.component.html',
  styleUrls: ['./issue-material-list.component.scss']
})
export class IssueMaterialListComponent implements OnInit, AfterViewInit {

  constructor(private dialog: MatDialog, private transactionService: TransactionService, private apiService: ApiHandlerService) { }

  dataSource: IssueMaterialDatasource;
  displayedColumns = ["action", "entryCreatedOnDate", "issueDate", "inventoryType", "outgoingQuantity", "uom", "outgoingMaterial", "outgoingMaterialDescription", "classificationCategory", "consumingMachine", "issuedToEngineer", "issuedForDept", "approvedBy", "issuedBy", "issueSlipNumber", "outgoingMaterialPrice"];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  ngOnInit(): void {
    this.dataSource = new IssueMaterialDatasource(this.transactionService);
  }
  ngAfterViewInit(): void {
    this.dataSource.loadIssueMaterials('', 0, 10, this.paginator);
    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadIssueMaterials();
        })
      )
      .subscribe();

    merge(this.paginator.page)
      .pipe(
        tap(() => this.loadIssueMaterials())
      )
      .subscribe();
  }

  loadIssueMaterials() {
    this.dataSource.loadIssueMaterials(this.input.nativeElement.value, this.paginator.pageIndex, this.paginator.pageSize, this.paginator);
  }

  openDialog(action: string, issueMaterial: IssueMaterial) {
    console.log('accept mat : ' + action);
    const dialogRef = this.dialog.open(IssueMaterialDeleteDialog, {
      width: '350px',
      data: issueMaterial
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      if (result) {
        this.apiService.delete('http://localhost:9443/api/v1/inventory/cancel-issued-mat/' + issueMaterial.dbId)
          .subscribe(data => {
            console.log(data);
            this.loadIssueMaterials();
          });
      }
      
    });
  }

}

@Component({
  selector: 'issue-material-delete',
  templateUrl: './issue-material-delete.dialog.html',
})
export class IssueMaterialDeleteDialog {



  constructor(
    public dialogRef: MatDialogRef<IssueMaterialDeleteDialog>,
    @Inject(MAT_DIALOG_DATA) public data: IssueMaterial) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

}