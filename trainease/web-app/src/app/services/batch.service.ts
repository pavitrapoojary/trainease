import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Batch } from '../models/batch.model';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BatchService {
  baseUrl = environment.baseUrl;
  isProduction = environment.production;

  constructor(private http: HttpClient) { }

  getAllBatches(): Observable<Batch[]> {
    return this.http.get<Batch[]>(`${this.baseUrl}/batches`);
  }

  createBatch(batch: Batch): Observable<Batch> {
    return this.http.post<Batch>(`${this.baseUrl}/batches`, batch);
  }

  updateBatch(batch: Batch): Observable<Batch> {
    return this.http.put<Batch>(`${this.baseUrl}/batches`, batch);
  }

}
