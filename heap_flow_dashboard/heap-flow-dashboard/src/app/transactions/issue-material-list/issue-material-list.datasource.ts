import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { MatPaginator } from "@angular/material/paginator";

import { IssueMaterial } from 'src/app/models/issue-material-list'
import { TransactionService } from 'src/app/services/transactions.service'

export class IssueMaterialDatasource implements DataSource<IssueMaterial>{

    constructor(private transactionService: TransactionService) { }

    private issueMaterialSubject = new BehaviorSubject<IssueMaterial[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);

    public loading$ = this.loadingSubject.asObservable();

    loadIssueMaterials(idLike: string, page: number, size: number, paginator: MatPaginator) {
        this.loadingSubject.next(true);

        this.transactionService.findIssueMaterials(idLike, page, size)
            .pipe(
                catchError((res) => of(res)),
                finalize(() => this.loadingSubject.next(false))
            )
            .subscribe(res => {
                this.issueMaterialSubject.next(res.content);
                paginator.length = res.totalElements;
            })
    }

    connect(collectionViewer: CollectionViewer): Observable<IssueMaterial[] | readonly IssueMaterial[]> {
        return this.issueMaterialSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.issueMaterialSubject.complete();
        this.loadingSubject.complete();
    }

}