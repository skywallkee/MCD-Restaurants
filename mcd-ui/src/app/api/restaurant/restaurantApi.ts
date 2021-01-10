import { Injectable } from '@angular/core';
import { Restaurant } from 'src/app/models/restaurant';
import { Table } from 'src/app/models/table';
import { Wall } from 'src/app/models/wall';
import ajax from 'superagent';
import config from '../../config/index';

@Injectable({
    providedIn: 'root'
})

export class RestaurantServiceApi {
    async getAll(): Promise<Restaurant[]> {
        const resp = await ajax
            .get(config.endpoint.restaurant.list)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json');
        return resp.body;
    }

    async getWalls(): Promise<Wall[]> {
        const resp = await ajax
            .get(config.endpoint.restaurant.walls)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json');
        return resp.body;
    }

    async getTables(): Promise<Table[]> {
        const resp = await ajax
            .get(config.endpoint.restaurant.tables)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json');
        return resp.body;
    }

    async getReservations(){
        const resp = await ajax
            .get(config.endpoint.restaurant.reservations)
            .timeout({ deadline: 30000 })  //30 seconds
            .set('Accept', 'application/json');
        return resp.body;
    }
}