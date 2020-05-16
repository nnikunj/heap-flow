import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryItemsGridComponent } from './inventory-items-grid.component';

describe('InventoryItemsGridComponent', () => {
  let component: InventoryItemsGridComponent;
  let fixture: ComponentFixture<InventoryItemsGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryItemsGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryItemsGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
