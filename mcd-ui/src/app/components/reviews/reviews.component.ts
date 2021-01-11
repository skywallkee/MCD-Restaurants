import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
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

  constructor(
    private cd: ChangeDetectorRef,
    private restaurantService: RestaurantService
    ) { }

  async ngOnInit(): Promise<void> {
    this.reviewList = await this.restaurantService.getAllReviews();
  }

  processDateString(input) {
    const date = new Date(input);
    return date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
  }

  async submitReview() {
    await this.restaurantService.submitReview(this.reviewText, this.starRating);
    this.reviewList = await this.restaurantService.getAllReviews();
    this.reviewText = "";
    this.cd.detectChanges();
  }

}
