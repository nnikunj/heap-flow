import { Injectable } from '@angular/core'
import { HttpClient, HttpParams } from '@angular/common/http'
import { map } from 'rxjs/operators';
import { Observable } from "rxjs";
import { MachineResponse } from 'src/app/models/machine'

@Injectable({
    providedIn: 'root'
})
export class MachineService {

    constructor(private httpClient: HttpClient) { }

    findMachines(codeLike = "", page = 0, size = 10): Observable<MachineResponse> {
        return this.httpClient.get('http://localhost:9443/api/v1/machines/fetch-machines-page-wise', {
            params: new HttpParams()
                .set('codeLike', codeLike)
                .set('page', page.toString())
                .set('size', size.toString())
        }).pipe(
            map(res => res as MachineResponse)
        );
    }

}