import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})

export class Pageable {
    offset: Number;
    sort: Sort;
    pageSize: Number;
    pageNumber: Number;
    paged: boolean;
    unpaged: boolean
}

export class Sort {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
}