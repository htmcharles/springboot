export interface User {
    id?: number;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    address: string;
    role?: 'CUSTOMER' | 'ADMIN';
}

export interface LoginResponse {
    success: boolean;
    user: User;
    message: string;
}

export interface RegisterResponse {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
    address: string;
    role: 'CUSTOMER' | 'ADMIN';
}
