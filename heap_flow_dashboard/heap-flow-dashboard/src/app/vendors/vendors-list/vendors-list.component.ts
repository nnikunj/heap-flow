import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";
//import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { merge, fromEvent } from "rxjs";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';

import { VendorsDatasource } from './vendors-list.datasource'
import { VendorService } from 'src/app/services/vendor.service'
import { VendorsAddComponent } from 'src/app/vendors/vendors-add/vendors-add.component'


@Component({
  selector: 'app-vendors-list',
  templateUrl: './vendors-list.component.html',
  styleUrls: ['./vendors-list.component.scss']
})
export class VendorsListComponent implements OnInit, AfterViewInit {

  constructor(private dialog: MatDialog, private vendorService: VendorService) { }

  dataSource: VendorsDatasource;
  displayedColumns = ["action", "vendorId", "name", "searchName", "gstRegNo", "panNumber", "address", "address2", "city", "stateCode", "contactPerson", "phone", "email"];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  ngOnInit(): void {
    this.dataSource = new VendorsDatasource(this.vendorService);
  }

  ngAfterViewInit(): void {
    this.dataSource.loadVendors('', 0, 10, this.paginator);
    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadVendors();
        })
      )
      .subscribe();

    merge(this.paginator.page)
      .pipe(
        tap(() => this.loadVendors())
      )
      .subscribe();
  }

  loadVendors() {
    this.dataSource.loadVendors(this.input.nativeElement.value, this.paginator.pageIndex, this.paginator.pageSize, this.paginator);
  }

  openDialog(action: string, vendor: any) {
    let obj = new Map();
    obj.set('action', action);
    obj.set("vendor", vendor);

    const dialogRef = this.dialog.open(VendorsAddComponent, { data: obj });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      this.loadVendors();
    });
  }



}
