import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MachineImportComponent } from './machine-import.component';

describe('MachineImportComponent', () => {
  let component: MachineImportComponent;
  let fixture: ComponentFixture<MachineImportComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MachineImportComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MachineImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
