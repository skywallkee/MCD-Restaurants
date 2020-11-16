import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  restaurants=[{
    title: "test",
    description: "description1"
  }, {
    title: "test2",
    description: "description2"
  }];

  constructor() { }

  ngOnInit(): void {
  }

}
