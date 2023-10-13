import { TestBed } from '@angular/core/testing';

import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { User, UserRole } from '../models/user.model';
import { environment } from 'src/environments/environment';
import { Batch } from '../models/batch.model';

describe('UserService', () => {
  let service: UserService;
  let httpTestingController: HttpTestingController;
  let mockUsers: User[];
  let mockUser: User;
  let mockBatch: Batch;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    service = TestBed.inject(UserService);
    httpTestingController = TestBed.inject(HttpTestingController);
    mockBatch = {
      batchId: '',
      batchName: '',
      batchDescription: '',
      editing: false
    };

    mockUser = {
      emailId: '',
      name: '',
      role: UserRole.TRAINEE,
      batch: mockBatch,
      editing: false
    };

    mockUsers = [
      mockUser
    ];

  });


  it('should be created User Service', () => {
    expect(service).toBeTruthy();
  });

  it('should get all users', () => {
    service.getAllUsers().subscribe((users) => {
      expect(users).toEqual(mockUsers);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/users`);
    expect(req.request.method).toEqual('GET');
    req.flush(mockUsers);
  });


  it('should add a user', () => {
    service.addUser(mockUser).subscribe((userResponse) => {
      expect(userResponse).toEqual(mockUser);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/users`);
    expect(req.request.method).toEqual('POST');
    req.flush(mockUser);
  });


  it('should update a user', () => {
    service.updateUser(mockUser).subscribe((userResponse) => {
      expect(userResponse).toEqual(mockUser);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/users`);
    expect(req.request.method).toEqual('PUT');
    req.flush(mockUser);
  });

  it('should delete a user by email ID', () => {
    const emailId = 'user@example.com';

    service.deleteUserByEmailId(emailId).subscribe((response) => {
      expect(response).toEqual('Deleted');
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/users/${emailId}`);
    expect(req.request.method).toEqual('DELETE');
    req.flush('Deleted');
  });


  it('should upload an Excel file', () => {
    const mockFile = new File(['test'], 'test.xlsx',
      { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

    service.uploadExcelFile(mockFile).subscribe((userResponse) => {
      expect(userResponse).toEqual(mockUsers);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/users/saveExcel`);
    expect(req.request.method).toEqual('POST');
    req.flush(mockUsers);
  });


  afterEach(() => {
    httpTestingController.verify();
  });

});
