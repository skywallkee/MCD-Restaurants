import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/reservation';
import { Table } from 'src/app/models/table';
import { Wall } from 'src/app/models/wall';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit {
  walls: Wall[];
  tables: Table[];
  reservations: Reservation[];

  constructor(private restaurantService: RestaurantService) { }

  async ngOnInit(): Promise<void> {
    this.walls = await this.restaurantService.getWalls();
    this.tables = await this.restaurantService.getTables();
    this.reservations = await this.restaurantService.getReservations();
  }

  isAvailable(table) {
    return this.restaurantService.isAvailable(this.reservations, table);
  }

  tableSelected(x) {
    console.log(x);
  }
}
