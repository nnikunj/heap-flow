import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MachinesAddComponent } from './machines-add.component';

describe('MachinesAddComponent', () => {
  let component: MachinesAddComponent;
  let fixture: ComponentFixture<MachinesAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MachinesAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MachinesAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
