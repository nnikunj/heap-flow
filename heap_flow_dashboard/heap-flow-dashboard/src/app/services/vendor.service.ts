import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';
import { Observable } from "rxjs";

import { VendorResponse } from './../models/vendor';

@Injectable({
    providedIn: 'root'
})
export class VendorService {

    constructor(private httpClient: HttpClient) { }

    search(term: string) {
        let vendors = this.httpClient.get('http://localhost:9443/api/v1/vendors/fetch-vendors-with-name-like/' + btoa(term))
            .pipe(
                debounceTime(500),  // WAIT FOR 500 MILISECONDS ATER EACH KEY STROKE.
                map(
                    (data: any) => {
                        return (
                            data.length != 0 ? data as any[] : [[] as any]
                        );
                    }
                ));

        return vendors;
    }

    findVendors(searchNameLike = "", page = 0, size = 10): Observable<VendorResponse> {
        return this.httpClient.get('http://localhost:9443/api/v1/vendors/fetch-vendors-page-wise', {
            params: new HttpParams()
                .set('searchNameLike', searchNameLike)
                .set('page', page.toString())
                .set('size', size.toString())
        }).pipe(
            map(res => res as VendorResponse)
        );
    }
}