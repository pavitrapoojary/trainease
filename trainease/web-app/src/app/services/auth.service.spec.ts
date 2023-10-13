import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from 'src/environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let httpTestingController : HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientTestingModule],
      providers:[AuthService]
    });

    service = TestBed.inject(AuthService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });
 
  it('should be created Auth Service', () => {
    expect(service).toBeTruthy();
  });


  it('should authenticate user login', () => {
    const mockUsername = 'username';
    const mockPassword = 'password';
    const mockJwt = 'jwtToken';
    service.login(mockUsername,mockPassword).subscribe((authResponse) => {
      expect(authResponse).toEqual(mockJwt);
    });

    const req = httpTestingController.expectOne(`${environment.baseUrl}/authenticate`);
    expect(req.request.method).toEqual('POST');
    req.flush(mockJwt);
  });


  afterEach(() => {
    httpTestingController.verify();
  });

});
