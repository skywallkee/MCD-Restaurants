import { Injectable } from '@angular/core';
import { RestaurantServiceApi } from 'src/app/api/restaurant/restaurantApi';
import { Reservation } from 'src/app/models/reservation';
import { Restaurant } from 'src/app/models/restaurant';
import { Review } from 'src/app/models/review';
import { Table } from 'src/app/models/table';
import { Wall } from 'src/app/models/wall';

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

  async getId(keyword: number): Promise<any> {
    const restaurants = await this.restaurantServiceApi.getAll();
    let restaurant;
    restaurants.forEach(function (item) {
      if (item.id == keyword)
        restaurant = item;
    })
    return restaurant;
  }

  async getAverageReview(keyword: number): Promise<any> {
    const reviews = await this.restaurantServiceApi.getAllReviews();
    let sum = 0;
    let number = 0;
    reviews.forEach(function (review) {
      if (review.id_R == keyword) {
        sum += review.nr_stele;
        number += 1;
      }
    })
    return Math.floor(sum / number);
  }

  async getWalls(): Promise<Wall[]> {
    const walls = await this.restaurantServiceApi.getWalls();
    return walls;
  }

  async getTables(): Promise<Table[]> {
    const tables = await this.restaurantServiceApi.getTables();
    return tables;
  }

  async getReservations(): Promise<Reservation[]> {
    const reservations = await this.restaurantServiceApi.getReservations();
    return reservations;
  }

  isAvailable(reservations: Reservation[], table: Table) {
    if (!reservations) {
      return false;
    }
    const tableReservations = reservations.filter(item => item.id_M === table.id);
    for (let item of tableReservations) {
      const reservationTime = new Date(item.data + " " + item.ora);
      const now = new Date();
      const nowEnd = new Date(now.getFullYear(), now.getMonth(), now.getDate(), now.getHours() + 2, now.getMinutes(), now.getSeconds());
      const reservationStart = new Date(reservationTime.getFullYear(), reservationTime.getMonth(),
        reservationTime.getDate(), reservationTime.getHours(), reservationTime.getMinutes(), reservationTime.getSeconds());
      const reservationEnd = new Date(reservationTime.getFullYear(), reservationTime.getMonth(),
        reservationTime.getDate(), reservationTime.getHours() + 2, reservationTime.getMinutes(), reservationTime.getSeconds());
      if (reservationStart <= nowEnd && now <= reservationEnd) {
        return false;
      }
    }
    return true;
  }

  async submitReview(comment: string, stars: number): Promise<void> {
    return await this.restaurantServiceApi.submitReview(comment, stars);
  }

  async getAllReviews(): Promise<Review[]> {
    return await this.restaurantServiceApi.getAllReviews();
  }

  async getStatisticsByDay(){
    return await this.restaurantServiceApi.getStatisticsByDay(1, 5);
  }
}
