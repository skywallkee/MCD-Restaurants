import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/Login/login.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username: string;
  password1: string;
  password2: string;

  constructor(private loginService: LoginService) { }

  registerClick(){
    this.loginService.register(this.username, this.password1, this.password2);
  }

  ngOnInit(): void {
  }

}
