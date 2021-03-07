import { Component, OnInit } from '@angular/core';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';
import { ActivatedRoute } from '@angular/router';

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
  reviewAverage: number[];

  constructor(private route: ActivatedRoute, private restaurantService: RestaurantService) { }

  async ngOnInit() {
    let average = 0;
    this.reviewAverage = Array<number>(5);
    this.route.params.subscribe(async params => {
      this.restaurant = await this.restaurantService.getId(params["id"]);
      average = await this.restaurantService.getAverageReview(params["id"]);
      for (let i = 0; i < 5; i++) {
        if (i < average)
          this.reviewAverage[i] = 1;
        else
          this.reviewAverage[i] = 0;
      }
    });
  }

}
