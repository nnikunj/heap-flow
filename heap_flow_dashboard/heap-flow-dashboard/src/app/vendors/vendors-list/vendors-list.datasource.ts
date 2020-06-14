import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { MatPaginator } from "@angular/material/paginator";

import { Vendor } from 'src/app/models/vendor'
import { VendorService } from 'src/app/services/vendor.service'

export class VendorsDatasource implements DataSource<Vendor>{

    constructor(private vendorService: VendorService) { }

    private vendorsSubject = new BehaviorSubject<Vendor[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    loadVendors(searchNameLike: string, page: number, size: number, paginator: MatPaginator) {
        this.loadingSubject.next(true);

        this.vendorService.findVendors(searchNameLike, page, size)
            .pipe(
                catchError((res) => of(res)),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(res => {
                this.vendorsSubject.next(res.content);
                paginator.length = res.totalElements;
            })
    }

    connect(collectionViewer: CollectionViewer): Observable<Vendor[] | readonly Vendor[]> {
        return this.vendorsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.vendorsSubject.complete();
        this.loadingSubject.complete();
    }

}