import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map } from 'rxjs';
import { AuthService } from '../services/auth.service';

export function roleGuard(allowedRoles: string[]): CanActivateFn {
  return () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    return authService.ensureAuthenticated().pipe(
      map((authenticated) => {
        if (!authenticated) return router.createUrlTree(['/login']);

        const role = authService.currentUser()?.role;
        return role && allowedRoles.includes(role)
          ? true
          : router.createUrlTree(['/dashboard']);
      })
    );
  };
}
