import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';

export const appHttpInterceptor: HttpInterceptorFn = (request, next) => {
  console.log('yess');
  console.log(request.url);
  const authService: AuthService = Inject(AuthService);
  console.log(authService);
  if (!request.url.includes('/auth/login')) {
    const JWT = localStorage.getItem('JWT');
    let req = request.clone({
      headers: request.headers.set('Authorization', 'Bearer ' + JWT),
    });
    //console.log(authService.accessToken);
    return next(req).pipe(
      catchError((error) => {
        console.log(error);
        if (error.status == 401) {
          authService.logout();
        }

        return throwError(error.message);
      })
    );
  } else {
    return next(request);
  }
};
