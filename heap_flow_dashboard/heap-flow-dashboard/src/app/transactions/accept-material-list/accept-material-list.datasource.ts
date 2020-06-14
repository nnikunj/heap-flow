import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { MatPaginator } from "@angular/material/paginator";

import { AcceptMaterial } from 'src/app/models/accept-material-list'
import { TransactionService } from 'src/app/services/transactions.service'

export class AcceptMaterialDatasource implements DataSource<AcceptMaterial> {

    constructor(private transactionService: TransactionService) { }

    private acceptMaterialSubject = new BehaviorSubject<AcceptMaterial[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    loadAcceptMaterials(idLike: string, page: number, size: number, paginator: MatPaginator) {
        this.loadingSubject.next(true);

        this.transactionService.findAcceptMaterials(idLike, page, size)
            .pipe(
                catchError((res) => of(res)),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(res => {
                this.acceptMaterialSubject.next(res.content);
                paginator.length = res.totalElements;
            })
    }

    connect(collectionViewer: CollectionViewer): Observable<AcceptMaterial[] | readonly AcceptMaterial[]> {
        return this.acceptMaterialSubject.asObservable();
    }
    disconnect(collectionViewer: CollectionViewer): void {
        this.acceptMaterialSubject.complete();
        this.loadingSubject.complete();
    }

}