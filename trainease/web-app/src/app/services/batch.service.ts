import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Batch } from '../models/batch.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BatchService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAllBatches(): Observable<Batch[]> {
    return this.http.get<Batch[]>(`${this.baseUrl}/batches`);
  }

  createBatch(batch: Batch): Observable<Batch[]> {
    return this.http.post<Batch[]>(`${this.baseUrl}/batches`, batch);
  }

  updateBatch(batch: Batch): Observable<Batch> {
    return this.http.put<Batch>(`${this.baseUrl}/batches`, batch);
  }

}
