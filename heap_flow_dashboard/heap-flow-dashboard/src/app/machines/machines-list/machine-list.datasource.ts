import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Observable, BehaviorSubject, of} from "rxjs";
import {catchError, finalize} from "rxjs/operators";
import { MatPaginator } from "@angular/material/paginator";

import { MachineResponse, Machine} from 'src/app/models/machine'
import { MachineService } from 'src/app/services/machine-service'

export class MachineDatasource implements DataSource<Machine>{

    constructor(private machineService : MachineService){}

    private machineSubject = new BehaviorSubject<Machine[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    // private totalRecordSubject = new BehaviorSubject<number>(0);

    public loading$ = this.loadingSubject.asObservable();
    // public totalRecord$ = this.totalRecordSubject.asObservable();

    loadMachines(codeLike : string, page : number, size : number, paginator: MatPaginator){
        this.loadingSubject.next(true);

        this.machineService.findMachines(codeLike, page, size)
        .pipe(
            catchError((res) => of(res)),
            finalize(() => this.loadingSubject.next(false))
        )
        .subscribe(res => {
            this.machineSubject.next(res.content);
            paginator.length = res.totalElements;
        })

    }

    connect(collectionViewer: CollectionViewer): Observable<Machine[] | readonly Machine[]> {
        return this.machineSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.machineSubject.complete();
        this.loadingSubject.complete();
        // this.totalRecordSubject.complete();
    }

}