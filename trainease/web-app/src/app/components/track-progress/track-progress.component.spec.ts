import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackProgressComponent } from './track-progress.component';

describe('TrackProgressComponent', () => {
  let component: TrackProgressComponent;
  let fixture: ComponentFixture<TrackProgressComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrackProgressComponent]
    });
    fixture = TestBed.createComponent(TrackProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
