import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IssueMaterialComponent } from './issue-material.component';

describe('IssueMaterialComponent', () => {
  let component: IssueMaterialComponent;
  let fixture: ComponentFixture<IssueMaterialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IssueMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
