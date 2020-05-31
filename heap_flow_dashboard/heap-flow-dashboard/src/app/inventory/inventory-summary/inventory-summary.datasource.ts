import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Observable, BehaviorSubject, of} from "rxjs";
import {catchError, finalize} from "rxjs/operators";
import { InventorySummary } from 'src/app/models/inventory-summary';
import { InventoryService } from 'src/app/services/inventory.service'
import { MatPaginator } from "@angular/material/paginator";

export class InventorySummaryDatasource implements DataSource<InventorySummary> {

    private inventorySummarySubject = new BehaviorSubject<InventorySummary[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private totalRecordSubject = new BehaviorSubject<Number>(0);

    public loading$ = this.loadingSubject.asObservable();
    public totalRecord$ = this.totalRecordSubject.asObservable();

    constructor(private inventoryService : InventoryService){

    }

    loadInventorySummaryItems(filter:string,
        sortDirection:string,
        pageNumber:number,
        pageSize:number, paginator: MatPaginator){
            this.loadingSubject.next(true);

            this.inventoryService.findInventorySummary(filter, sortDirection, pageNumber, pageSize)
            .pipe(
                catchError((res) => of(res)),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(res => {
                this.totalRecordSubject.next(res.totalElements);
                this.inventorySummarySubject.next(res.content);
                paginator.length = res.totalElements;
            });
    }

    connect(collectionViewer: CollectionViewer): Observable<InventorySummary[] | readonly InventorySummary[]> {
        console.log("Connecting data source");
        return this.inventorySummarySubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.inventorySummarySubject.complete();
        this.loadingSubject.complete();
        this.totalRecordSubject.complete();
    }
    
}