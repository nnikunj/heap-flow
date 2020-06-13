import { Component, OnInit, AfterViewInit, ViewChild, ElementRef } from '@angular/core';

import { MatPaginator } from "@angular/material/paginator";
//import { DialogBoxComponent } from './dialog-box/dialog-box.component';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { merge, fromEvent } from "rxjs";
import { debounceTime, distinctUntilChanged, startWith, tap, delay } from 'rxjs/operators';

import { MachineDatasource } from './machine-list.datasource'
import { MachineService } from 'src/app/services/machine-service'
import { MachinesAddComponent } from 'src/app/machines/machines-add/machines-add.component'

@Component({
  selector: 'app-machines-list',
  templateUrl: './machines-list.component.html',
  styleUrls: ['./machines-list.component.scss']
})
export class MachinesListComponent implements OnInit, AfterViewInit {

  constructor(private machineService: MachineService,
    private dialog: MatDialog) { }


  dataSource: MachineDatasource;

  displayedColumns = ['action', 'name', 'code', 'model', 'category', 'kWKva', 'make', 'serialNo'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('input') input: ElementRef;

  ngOnInit(): void {
    this.dataSource = new MachineDatasource(this.machineService);
    
  }

  ngAfterViewInit(): void {
    this.dataSource.loadMachines('', 0, 10, this.paginator);
    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;
          this.loadMachines();
        })
      )
      .subscribe();

    merge(this.paginator.page)
      .pipe(
        tap(() => this.loadMachines())
      )
      .subscribe();
  }

  loadMachines() {
    this.dataSource.loadMachines(this.input.nativeElement.value, this.paginator.pageIndex, this.paginator.pageSize, this.paginator);
  }

  openDialog(action: string, machine: any) {
    let obj = new Map();
    obj.set('action', action);
    obj.set("machine", machine);

    const dialogRef = this.dialog.open(MachinesAddComponent, { data: obj });

    dialogRef.afterClosed().subscribe(result => {
      console.log(result);
      this.loadMachines();
    });
  }



}
