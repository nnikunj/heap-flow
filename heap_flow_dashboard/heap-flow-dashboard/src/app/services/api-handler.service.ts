import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse,HttpParams } from '@angular/common/http';

import { catchError } from 'rxjs/operators';
import { map} from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiHandlerService {

  constructor(private httpClient: HttpClient) { }

  getText(url: string): Observable<any> {
     
    return this.httpClient.get(url, { observe: 'response', responseType: 'text'}).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };

  /**
   * 
   * @param url Executes GET api
   */
  get(url: string): Observable<any> {
     
    return this.httpClient.get(url, { observe: 'response' }).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };

  getWithParams(url: string, requestParams : HttpParams): Observable<any> {
     
    requestParams = requestParams.append('observe','response');
    return this.httpClient.get(url, {params : requestParams}).
      pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };

  /**
   * 
   * @param url Execites POST api
   * @param data 
   */
  save(url: string, data: any): Observable<any> {
     
    return this.httpClient.post(url, data, { observe: 'response' }).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };

 /**
   * Handles api error
   * @param error 
   */
  handleError(error: HttpErrorResponse) {
    
    if(error.error){
      error.error.status = error.status;
    }
    if (error.status == 401) {
       
      return throwError(error.error);
    } 
    return error.error ? throwError(error.error) : throwError(error) ;
  };

  /**
   * 
   * @param url Executes PUT api
   * @param data 
   */
  update(url: string, data: any): Observable<any> {
     
    return this.httpClient.put(url, data, { observe: 'response' }).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };

  /**
   * 
   * @param url Executes DELETE api
   */
  delete(url: string): Observable<any> {
    
    return this.httpClient.delete(url, { observe: 'response' }).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };

  /**
   * 
   * @param url Executes HEAD api
   */
  head(url: string): Observable<any> {
     
    return this.httpClient.head(url, { observe: 'response' }).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };


    /**
   * 
   * @param url Executes DELETE api with request body
   */
  deleteWithRequestBody(url: string, data: any): Observable<any> {
    
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      body: data,
      observe: 'response'
    };
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
      body: data
    };
    return this.httpClient.delete(url, httpOptions).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };


 
    /**
   * 
   * @param url Executes DELETE api with request body
   */
  deleteWithParams(url: string, requestParams : HttpParams): Observable<any> {
    
    requestParams = requestParams.append('observe','response');
    return this.httpClient.delete(url, {params : requestParams}).
    pipe(map((res: HttpResponse<any>) => this.handleResponse(res)),
      catchError((error: HttpErrorResponse) => this.handleError(error)));
  };

  /**
   * Handles api response
   * @param response 
   */
  handleResponse(response) {

    if (response == null || response.status === 204) {
      return {};
    } else {
      return response && response.body ? response.body : response;
    }
  };




}
