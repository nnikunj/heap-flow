import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { MatPaginator } from "@angular/material/paginator";

import { InventoryReserve } from './inventory-reserve.model'
import { InventoryReserveService } from './inventory-reserve.service'

export class InventoryReserveDatasource implements DataSource<InventoryReserve>{

    constructor(private _inventoryReserveService: InventoryReserveService) { }

    private inventoryReserveSubject = new BehaviorSubject<InventoryReserve[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    loadInventoryReserve(page: number, size: number, paginator: MatPaginator) {
        this.loadingSubject.next(true);

        this._inventoryReserveService.findReserveInventory(page, size)
            .pipe(
                catchError((res) => of(res)),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(res => {
                this.inventoryReserveSubject.next(res.content);
                paginator.length = res.totalElements;
            })
    }

    connect(collectionViewer: CollectionViewer): Observable<InventoryReserve[] | readonly InventoryReserve[]> {
        return this.inventoryReserveSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.inventoryReserveSubject.complete();
        this.loadingSubject.complete();
    }

}