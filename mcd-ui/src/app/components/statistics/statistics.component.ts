import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

var byDay = [
  {
    "name": "Germany",
    "value": 8940000
  },
  {
    "name": "USA",
    "value": 5000000
  },
  {
    "name": "France",
    "value": 7200000
  }
];

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {
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

  byDay: any[];
  byDayByHour: any[];

  view: any[] = [500, 300];

  // options
  showXAxis = true;
  showYAxis = true;


  constructor(
    private restaurantService: RestaurantService,
    private route: ActivatedRoute,
    private cd: ChangeDetectorRef
  ) {
    this.route.params.subscribe(async params => {
      let average = 0;
      this.reviewAverage = Array<number>(5);
      this.restaurant = await this.restaurantService.getId(params["id"]);
      let statistics = await this.restaurantService.getStatisticsByDay(this.restaurant.id, 1);
      let data = Object.keys(statistics).map(key => {
        return {
          name: key,
          value: statistics[key]
        };
      });
      Object.assign(this, { byDay: data });

      statistics = await this.restaurantService.getStatisticsByDayByHour(this.restaurant.id, new Date().getDay());
      data = Object.keys(statistics).map(key => {
        return {
          name: key,
          value: statistics[key]
        };
      });
      Object.assign(this, { byDayByHour: data });

      average = await this.restaurantService.getAverageReview(params["id"]);
      for (let i = 0; i < 5; i++) {
        if (i < average)
          this.reviewAverage[i] = 1;
        else
          this.reviewAverage[i] = 0;
      }
    });
  }

  ngOnInit(): void {
  }

  onSelect(event) {
    console.log(event);
  }

}
