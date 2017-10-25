import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SlideListComponent } from './slide-list.component';

describe('SlideListComponent', () => {
  let component: SlideListComponent;
  let fixture: ComponentFixture<SlideListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SlideListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SlideListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
