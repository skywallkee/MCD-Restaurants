import { Injectable } from '@angular/core';
import { LoginServiceApi } from 'src/app/api/login/loginApi';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private loginServiceApi: LoginServiceApi) { }

  async login(username: string, password: string): Promise<void> {
    const token = await this.loginServiceApi.login(username, password);
    localStorage.setItem('TOKEN', token);
  }

  register(username: string, password1: string, password2: string): void {
    this.loginServiceApi.register(username, password1, password2);
  }
}
