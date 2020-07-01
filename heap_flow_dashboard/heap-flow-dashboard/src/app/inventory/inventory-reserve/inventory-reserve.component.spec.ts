import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryReserveComponent } from './inventory-reserve.component';

describe('InventoryReserveComponent', () => {
  let component: InventoryReserveComponent;
  let fixture: ComponentFixture<InventoryReserveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryReserveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryReserveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
