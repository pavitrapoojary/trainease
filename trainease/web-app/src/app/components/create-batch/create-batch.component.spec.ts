import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBatchComponent } from './create-batch.component';

describe('CreateBatchComponent', () => {
  let component: CreateBatchComponent;
  let fixture: ComponentFixture<CreateBatchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateBatchComponent]
    });
    fixture = TestBed.createComponent(CreateBatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
