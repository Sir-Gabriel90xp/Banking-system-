export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  refreshToken: string;
  expiresIn: number;
}

export interface UserProfile {
  id: string;
  username: string;
  email: string;
  role: 'ROLE_ADMIN' | 'ROLE_EMPLOYEE' | 'ROLE_CUSTOMER';
  enabled: boolean;
}