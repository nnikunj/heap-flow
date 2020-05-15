import { Injectable } from '@angular/core';  
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse,HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';

@Injectable()  
export class HttpService {  

    constructor (private httpClient: HttpClient) { }  

    search(url: string, term: string) {
        let result = this.httpClient.get(url + term)
        .pipe(
            debounceTime(500),  // WAIT FOR 500 MILISECONDS AFTER EACH KEY STROKE.
            map(
                (data: any) => {
                    return (
                        data.length != 0 ? data as any[] : [[] as any]
                    );
                }
        ));

        return result;  
    }  
}