import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';
import { Observable, throwError } from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class UploadService {

    constructor(private httpClient: HttpClient) { }

    upload(url: string, body: any): Observable<any> {
        return this.httpClient.post(url, body, {
            reportProgress: true,
            observe: 'events'
        }).pipe(
            catchError(this.errorMgmt)
        )
    }

    errorMgmt(error: HttpErrorResponse) {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
            // Get client-side error
            errorMessage = error.error.message;
        } else {
            // Get server-side error
            errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        console.log(errorMessage);
        return throwError(errorMessage);
    }

}