import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubNavigationMenuComponent } from './sub-navigation-menu.component';

describe('SubNavigationMenuComponent', () => {
  let component: SubNavigationMenuComponent;
  let fixture: ComponentFixture<SubNavigationMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubNavigationMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubNavigationMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
