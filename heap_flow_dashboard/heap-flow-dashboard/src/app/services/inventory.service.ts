import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse,HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  constructor(private httpService: HttpClient) { }

  search(term: string) {
    let inventory = this.httpService.get('http://localhost:9443/api/v1/inventory-items/fetch-inventory-item-with-product-code/' + term)
    .pipe(
        map(
            (data: any) => {
                return (
                    data ? data as any : null
                );
            }
    ));

    return inventory;  
}  
}
