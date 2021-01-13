import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

@Component({
  selector: 'app-reservation-result',
  templateUrl: './reservation-result.component.html',
  styleUrls: ['./reservation-result.component.scss']
})
export class ReservationResultComponent implements OnInit {
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
  tables: number[] = [];
  phone: string;
  name: string;
  validPhone: boolean = false;

  constructor(private router: Router, private restaurantService: RestaurantService) {
    const state = this.router.getCurrentNavigation().extras.state;
    this.restaurant = state.restaurant;
    this.tables = state.tables;
  }

  ngOnInit(): void {
  }

  phoneChanged(event): void {
    this.phone = event;
    if (Number.isNaN(Number(this.phone))) {
      this.validPhone = false;
    } else {
      this.validPhone = true;
    }
  }

  confirm() {
    this.restaurantService.submitReservation(this.tables, this.phone, this.name);
    this.router.navigate(['search'], {});
  }

  cancel() {
    this.router.navigate(['search'], {});
  }
}
