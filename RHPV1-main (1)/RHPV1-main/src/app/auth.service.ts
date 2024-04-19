// src/app/auth.service.ts

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly USER_KEY = 'currentUser';

  constructor() { }

  setCurrentUser(token: string): void {
    localStorage.setItem(this.USER_KEY, token);
  }

  getCurrentUser(): string | null {
    return localStorage.getItem(this.USER_KEY);
  }

  getCurrentUserRole(): string | null {
    const user = this.getCurrentUser();
    if (user) {
      try {
        const tokenPayload = JSON.parse(atob(user.split('.')[1])); // Decode token payload
        if (tokenPayload && tokenPayload.scope && Array.isArray(tokenPayload.scope) && tokenPayload.scope.length > 0) {
          return tokenPayload.scope[0].substring(5); // Extract role from 'ROLE_X' format
        } else {
          console.error('Invalid token payload:', tokenPayload);
        }
      } catch (error) {
        console.error('Error parsing token payload:', error);
      }
    }
    return null;
  }
  

  isAdmin(): boolean {
    return this.getCurrentUserRole() === 'ADMIN';
  }

  isHR(): boolean {
    return this.getCurrentUserRole() === 'HUMAN_RESOURCE';
  }

  isLoggedIn(): boolean {
    return !!this.getCurrentUser();
  }

  logout(): void {
    localStorage.removeItem(this.USER_KEY);
  }
}
