import { Component, OnInit } from '@angular/core';
import { AgmCoreModule } from '@agm/core';
import {​​ google }​​ from "google-maps";
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';
import {ActivatedRoute} from '@angular/router';
import { Restaurant } from 'src/app/models/restaurant';

@Component({
  selector: 'app-restaurant-interfata',
  templateUrl: './restaurant-interfata.component.html',
  styleUrls: ['./restaurant-interfata.component.scss']
})
export class RestaurantInterfataComponent implements OnInit {
  lat: number = 51.678418;
  lng: number = 7.809007;
  restaurant: {
    id: number,
    adresa: "",
    latime: number,
    lungime: number,
    nameR: "",
    ora_deschidere: "",
    ora_inchidere: "",
    poza: ""
  };

  constructor(private route:ActivatedRoute, private restaurantService: RestaurantService){}

  async ngOnInit(){
    this.route.params.subscribe( async params => {
      this.restaurant = await this.restaurantService.getId(params["id"]);
      console.log(this.restaurant);
    }
  )
}

}
