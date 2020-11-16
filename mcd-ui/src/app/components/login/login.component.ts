import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/Login/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username: string;
  password: string;

  constructor(private loginService: LoginService) { }

  loginClick(){
    this.loginService.login(this.username, this.password);
  }

  ngOnInit(): void {
  }
}
