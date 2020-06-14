import { Injectable } from '@angular/core'
import { HttpClient, HttpParams } from '@angular/common/http'
import { map } from 'rxjs/operators';
import { Observable } from "rxjs";
import { IssueMaterialResponse } from 'src/app/models/issue-material-list'
import { AcceptMaterialResponse } from 'src/app/models/accept-material-list'

@Injectable({
    providedIn: 'root'
})
export class TransactionService {
    constructor(private httpClient: HttpClient) { }

    findIssueMaterials(idLike = "", page = 0, size = 10): Observable<IssueMaterialResponse> {
        return this.httpClient.get('http://localhost:9443/api/v1/inventory/fetch-issued-materials-page-wise', {
            params: new HttpParams()
                .set('idLike', idLike)
                .set('page', page.toString())
                .set('size', size.toString())
        }).pipe(
            map(res => res as IssueMaterialResponse)
        );
    }

    findAcceptMaterials(idLike = "", page = 0, size = 10): Observable<AcceptMaterialResponse> {
        return this.httpClient.get('http://localhost:9443/api/v1/inventory/fetch-accepted-materials-page-wise', {
            params: new HttpParams()
                .set('idLike', idLike)
                .set('page', page.toString())
                .set('size', size.toString())
        }).pipe(
            map(res => res as AcceptMaterialResponse)
        );
    }
}