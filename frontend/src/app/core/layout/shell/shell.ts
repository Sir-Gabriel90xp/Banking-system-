// src/app/core/layout/shell/shell.ts
import { Component, computed } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './shell.html',
  styleUrl: './shell.scss'
})
export class Shell {
  constructor(public authService: AuthService) {}

  isAdminOrEmployee = computed(() => {
    const role = this.authService.currentUser()?.role;
    return role === 'ROLE_ADMIN' || role === 'ROLE_EMPLOYEE';
  });

  isAdmin = computed(() => this.authService.currentUser()?.role === 'ROLE_ADMIN');

  logout(): void {
    this.authService.logout();
  }

  initials(name: string | undefined): string {
    if (!name) return '?';
    return name.charAt(0).toUpperCase();
  }
}