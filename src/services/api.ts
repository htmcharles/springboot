import axios from 'axios';
import { User, LoginResponse, RegisterResponse } from '../types/user';

const API_URL = 'http://localhost:8080/api/users';

export const register = async (userData: User): Promise<RegisterResponse> => {
    const response = await axios.post<RegisterResponse>(`${API_URL}/register`, userData);
    return response.data;
};

export const login = async (email: string, password: string): Promise<LoginResponse> => {
    const response = await axios.post<LoginResponse>(`${API_URL}/login`, {
        email,
        password
    });
    return response.data;
};
