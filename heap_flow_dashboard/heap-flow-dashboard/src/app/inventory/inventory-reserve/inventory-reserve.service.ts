import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from "rxjs";
import { InventoryReserveResponse } from './inventory-reserve.model'

@Injectable({
  providedIn: 'root'
})
export class InventoryReserveService {

  constructor(private _httpClient: HttpClient) { }

  findReserveInventory(page = 0, size = 10): Observable<InventoryReserveResponse> {
    return this._httpClient.get('/api/v1/inventory-items/fetch-paged-reserve-inventory-items', {
      params: new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString())
    }).pipe(
      map(res => res as InventoryReserveResponse)
    )
  }
}
