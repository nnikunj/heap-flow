import { Injectable } from '@angular/core';  
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse,HttpParams } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { debounceTime } from 'rxjs/internal/operators/debounceTime';
import { Observable, throwError } from 'rxjs';

@Injectable()
export class HttpService {  

    constructor (private httpClient: HttpClient) { }  

    search(url: string, term: string) {
        let result = this.httpClient.get(url + btoa(term))
        .pipe(
            debounceTime(500),  // WAIT FOR 500 MILISECONDS AFTER EACH KEY STROKE.
            map(
                (data: any) => {
                    return (
                        data.length != 0 ? data as any[] : [[] as any]
                    );
                },
                (error: any) => {
                    return error;
                }
        ));

        return result;  
    }

    get(url: string): Observable<any> {
        return this.httpClient.get(url, { observe: 'response' }).
        pipe(map((res: HttpResponse<any>) => {return res}),
          catchError((error: HttpErrorResponse) => this.handleError(error)));
      };

      private handleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
          // A client-side or network error occurred. Handle it accordingly.
          console.error('An error occurred:', error.error.message);
        } else {
          // The backend returned an unsuccessful response code.
          // The response body may contain clues as to what went wrong,
          console.error(
            `Backend returned code ${error.status}, ` +
            `body was: ${error.error}`);
        }
        // return an observable with a user-facing error message
        return throwError(
          'Something bad happened; please try again later.');
      };

      getBody(url : string, httpParams : HttpParams){
        return this.httpClient.get(url, {
          params:httpParams,
          responseType: 'blob'
        });
      }

      getBodyFromPost(url : string, body : any){
        return this.httpClient.post(url,body , {
          responseType: 'blob'
        });
      }

    // get(url: string){
    //     let result = this.httpClient.get(url).subscribe(res => {
    //         return res;
    //     }, error => {
    //         return error;
    //     });
    //     return result;
    // }
}