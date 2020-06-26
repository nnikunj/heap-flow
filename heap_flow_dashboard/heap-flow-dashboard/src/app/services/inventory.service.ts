import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from "rxjs";
import { InventoryItemResponse, InventoryItem } from 'src/app/models/inventory-item';
import { InventorySummaryResponse } from 'src/app/models/inventory-summary'

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  constructor(private httpService: HttpClient) { }

  search(term: string) {
    let inventory = this.httpService.get('/api/v1/inventory-items/fetch-inventory-item-with-product-code/' + btoa(term))
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

  findInventory(filter = '', sortOrder = 'asc',
    pageNumber = 0, pageSize = 3): Observable<InventoryItemResponse> {

    return this.httpService.get('/api/v1/inventory-items/fetch-paged-inventory-items', {
      params: new HttpParams()
        .set('idLike', filter)
        .set('page', pageNumber.toString())
        .set('size', pageSize.toString())
    }).pipe(
      map(res => res as InventoryItemResponse)
    );

  }

  findInventorySummary(filter = '', sortOrder = 'asc',
    pageNumber = 0, pageSize = 3): Observable<InventorySummaryResponse> {

    return this.httpService.get('/api/v1/inventory/fetch-inventory-summary-page-wise', {
      params: new HttpParams()
        .set('idLike', filter)
        .set('page', pageNumber.toString())
        .set('size', pageSize.toString())
    }).pipe(
      map(res => res as InventorySummaryResponse)
    );

  }


}
