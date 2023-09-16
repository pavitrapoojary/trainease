import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateProgressComponent } from './update-progress.component';

describe('UpdateProgressComponent', () => {
  let component: UpdateProgressComponent;
  let fixture: ComponentFixture<UpdateProgressComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateProgressComponent]
    });
    fixture = TestBed.createComponent(UpdateProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
