import { Component, Input, OnInit } from '@angular/core';
@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss']
})
export class RestaurantComponent implements OnInit {
  @Input() restaurant={
    title: "",
    description: "",
    stars: 0,
    logo: ""
  };

  constructor() { }

  ngOnInit(): void {
  }

}
