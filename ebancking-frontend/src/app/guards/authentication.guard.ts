import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authenticationGuard: CanActivateFn = (route, state) => {
  const authService: AuthService = inject(AuthService);
  const router: Router = inject(Router);
  console.log(authService.isAuthenticated);
  if (authService.isAuthenticated == true) {
    return true;
  } else {
    router.navigateByUrl('/login');
    return false;
  }
};
