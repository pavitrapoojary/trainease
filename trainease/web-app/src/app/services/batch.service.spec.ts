import { TestBed } from '@angular/core/testing';

import { BatchService } from './batch.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Batch } from '../models/batch.model';
import { environment } from 'src/environments/environment';

describe('BatchService', () => {
  let service: BatchService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BatchService]
    });

    service = TestBed.inject(BatchService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created Batch Service', () => {
    expect(service).toBeTruthy();
  });


  it('should get all batches', () => {
    const mockBatches: Batch[] = [];
    service.getAllBatches().subscribe((batchesResult) => {
      expect(batchesResult).toEqual(mockBatches);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/batches`);
    expect(req.request.method).toEqual('GET');
    req.flush(mockBatches);
  });


  it('should create a batch', () => {
    const mockBatch: Batch = {
      batchId: 'batchId',
      batchName: 'batchName',
      batchDescription: 'batchDescription',
      editing: false
    };

    service.createBatch(mockBatch).subscribe((batchCreated) => {
      expect(batchCreated).toEqual(mockBatch);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/batches`);
    expect(req.request.method).toEqual('POST');
    req.flush(mockBatch);
  });


  afterEach(() => {
    httpTestingController.verify();
  });


});
