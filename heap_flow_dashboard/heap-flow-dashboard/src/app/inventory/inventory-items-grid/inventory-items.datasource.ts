import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Observable, BehaviorSubject, of} from "rxjs";
import {catchError, finalize} from "rxjs/operators";
import { InventoryItemResponse, InventoryItem } from 'src/app/models/inventory-item';
import { InventoryService } from 'src/app/services/inventory.service'
import { MatPaginator } from "@angular/material/paginator";

export class InventoryItemDatasource implements DataSource<InventoryItem> {

    private inventoryItemSubject = new BehaviorSubject<InventoryItem[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    private totalRecordSubject = new BehaviorSubject<Number>(0);

    public loading$ = this.loadingSubject.asObservable();
    public totalRecord$ = this.totalRecordSubject.asObservable();

    constructor(private inventoryService : InventoryService){

    }

    loadInventoryItems(filter:string,
        sortDirection:string,
        pageNumber:number,
        pageSize:number, paginator: MatPaginator){
            this.loadingSubject.next(true);

            this.inventoryService.findInventory(filter, sortDirection, pageNumber, pageSize)
            .pipe(
                catchError((res) => of(res)),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(res => {
                this.totalRecordSubject.next(res.totalElements);
                this.inventoryItemSubject.next(res.content);
                paginator.length = res.totalElements;
            });
    }

    connect(collectionViewer: CollectionViewer): Observable<InventoryItem[] | readonly InventoryItem[]> {
        console.log("Connecting data source");
        return this.inventoryItemSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.inventoryItemSubject.complete();
        this.loadingSubject.complete();
        this.totalRecordSubject.complete();
    }
    
}