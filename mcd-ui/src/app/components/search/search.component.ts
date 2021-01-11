import { Component, OnInit } from '@angular/core';
import { Restaurant } from 'src/app/models/restaurant';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  restaurants: Restaurant[];
  searchKeyword: string;

  constructor(private restaurantService: RestaurantService) { }

  async ngOnInit(): Promise<void> {
    this.restaurants = await this.restaurantService.getAll();
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('TOKEN');
    return token != null;
  }

  async search(): Promise<void> {
    this.restaurants = await this.restaurantService.filter(this.searchKeyword);
  }

  logout(): void {
    localStorage.removeItem('TOKEN');
  }
}
