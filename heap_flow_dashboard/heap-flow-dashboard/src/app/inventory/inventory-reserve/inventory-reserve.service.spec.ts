import { TestBed } from '@angular/core/testing';

import { InventoryReserveService } from './inventory-reserve.service';

describe('InventoryReserveService', () => {
  let service: InventoryReserveService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InventoryReserveService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
