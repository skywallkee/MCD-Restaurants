import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  etaje: String[] = [];
  etajSelectat: String;
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
  selectedTables: number[] = [];
  selectedDate;
  selectedTime;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private restaurantService: RestaurantService,
    private cd: ChangeDetectorRef
  ) { }

  async ngOnInit(): Promise<void> {
    let average = 0;
    this.reviewAverage = Array<number>(5);
    this.reservations = await this.restaurantService.getReservations();
    this.route.params.subscribe(async params => {
      this.restaurant = await this.restaurantService.getId(params["id"]);
      this.tables = await this.restaurantService.getTablesByRestaurant(this.restaurant.id);
      this.walls = await this.restaurantService.getWallsByRestaurant(this.restaurant.id);
      average = await this.restaurantService.getAverageReview(params["id"]);
      for (let i = 0; i < 5; i++) {
        if (i < average)
          this.reviewAverage[i] = 1;
        else
          this.reviewAverage[i] = 0;
      }
      this.tables.forEach(table => {
        if (!this.etaje.includes(table.etaj)) {
          this.etaje.push(table.etaj);
        }
      });
      this.etajSelectat = this.etaje[0];
      this.cd.detectChanges();
    });
  }

  isAvailable(table) {
    return this.restaurantService.isAvailable(this.reservations, table);
  }

  tableSelected(x) {
    if (!this.selectedTables.includes(x)) {
      this.selectedTables.push(x);
    } else {
      this.selectedTables = this.selectedTables.filter(table => table !== x);
    }
    this.cd.detectChanges();
  }

  submitReservation() {
    this.router.navigate(['reservation-result'], {
      state: {
        tables: this.selectedTables,
        restaurant: this.restaurant,
        time: this.selectedTime,
        date: this.selectedDate
      }
    });
  }
}
