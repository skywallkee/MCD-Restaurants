import { ElementRef, ViewChild } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Reservation } from 'src/app/models/reservation';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

@Component({
  selector: 'app-lista-rezervari',
  templateUrl: './lista-rezervari.component.html',
  styleUrls: ['./lista-rezervari.component.scss']
})
export class ListaRezervariComponent implements OnInit {
  @ViewChild('time')
  timeInput: ElementRef;
  
  reservations: Reservation[];

  constructor(private restaurantService: RestaurantService) { }

  async ngOnInit(): Promise<void> {
    this.reservations = await this.restaurantService.getReservationsOfCurrentUser();
  }

  updateTime(reservation: Reservation) {
    this.restaurantService.updateReservation(reservation);
  }
}
