import { Component, OnInit } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import {​​ google }​​ from "google-maps";

@Component({
  selector: 'app-restaurant-interfata',
  templateUrl: './restaurant-interfata.component.html',
  styleUrls: ['./restaurant-interfata.component.scss']
})
export class RestaurantInterfataComponent implements OnInit {
  lat: number = 51.678418;
  lng: number = 7.809007;

  constructor() { }

  ngOnInit(): void {
  }

}
