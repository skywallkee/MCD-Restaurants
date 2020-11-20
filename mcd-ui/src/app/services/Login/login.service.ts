import { Injectable } from '@angular/core';
import { LoginServiceApi } from 'src/app/api/login/loginApi';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private loginServiceApi: LoginServiceApi) { }

  login(username: string, password: string): void {
    this.loginServiceApi.login(username, password);
  }

  register(username: string, password: string): void {
    this.loginServiceApi.register(username, password);
  }
}
