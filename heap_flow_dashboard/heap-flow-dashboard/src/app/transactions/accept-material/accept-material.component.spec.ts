import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcceptMaterialComponent } from './accept-material.component';

describe('AcceptMaterialComponent', () => {
  let component: AcceptMaterialComponent;
  let fixture: ComponentFixture<AcceptMaterialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcceptMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcceptMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
