import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Review } from 'src/app/models/review';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {
  reviewText: string;
  reviewList: Review[];
  starRating = 0;
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

  constructor(
    private cd: ChangeDetectorRef,
    private route: ActivatedRoute,
    private restaurantService: RestaurantService
  ) {
    this.route.params.subscribe(async params => {
      let average = 0;
      this.reviewAverage = Array<number>(5);
      this.route.params.subscribe(async params => {
        this.restaurant = await this.restaurantService.getId(params["id"]);
        this.reviewList = await this.restaurantService.getReviewsByRestaurant(this.restaurant.id);
        average = await this.restaurantService.getAverageReview(params["id"]);
        for (let i = 0; i < 5; i++) {
          if (i < average)
            this.reviewAverage[i] = 1;
          else
            this.reviewAverage[i] = 0;
        }
      });
    });
  }

  async ngOnInit(): Promise<void> {
  }

  processDateString(input) {
    const date = new Date(input);
    return date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
  }

  async submitReview() {
    await this.restaurantService.submitReview(this.reviewText, this.starRating, this.restaurant.id);
    this.reviewList = await this.restaurantService.getReviewsByRestaurant(this.restaurant.id);
    this.reviewText = "";
    this.cd.detectChanges();
  }

}
