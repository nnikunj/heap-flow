import { Injectable } from '@angular/core';  
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse,HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';

import { Vendor } from './../models/vendor';

@Injectable()  
export class VendorService {  

    constructor (private httpService: HttpClient) { }  

    search(term: string) {
        let vendors = this.httpService.get('http://localhost:9443/api/v1/vendors/fetch-vendors-with-name-like/' + term)
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
}