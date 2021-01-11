import { Component, OnInit } from '@angular/core';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

var single = [
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

  single: any[];
  multi: any[];

  view: any[] = [700, 400];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Country';
  showYAxisLabel = true;
  yAxisLabel = 'Population';

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  x: number;

  constructor(private restaurantService: RestaurantService) { Object.assign(this, { single }); }

  async ngOnInit(): Promise<void> {
    this.x = await this.restaurantService.getStatisticsByDay();
    console.log(this.x);
  }

  onSelect(event) {
    console.log(event);
  }

}
