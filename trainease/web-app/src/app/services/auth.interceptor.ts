import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpHeaders, } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { environment } from 'src/environments/environment';


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    baseUrl = environment.baseUrl;
    isProduction = environment.production;

    constructor(private router: Router, private cookie: CookieService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        if (req.url === `${this.baseUrl}/authenticate`) {
            return next.handle(req);
        }
        let token = this.getTokenFromCookie();
        const authReq = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`,
            },
        });

        return next.handle(authReq).pipe(tap((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
            }
        },
            (error) => {
                if (error.status === 401) {
                    this.router.navigate(['']);
                }
            }
        ));
    }

    getTokenFromCookie() {
        return this.cookie.get("Bearer");
    }

}

