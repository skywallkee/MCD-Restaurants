import { Injectable } from '@angular/core';
import { RestaurantServiceApi } from 'src/app/api/restaurant/restaurantApi';
import { Restaurant } from 'src/app/models/restaurant';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private restaurantServiceApi: RestaurantServiceApi) { }

  async getAll(): Promise<Restaurant[]> {
    return await this.restaurantServiceApi.getAll();
  }

  async filter(keyword: string): Promise<Restaurant[]> {
    const restaurants = await this.restaurantServiceApi.getAll();
    return restaurants.filter(item => item.nameR.startsWith(keyword));
  }
}
