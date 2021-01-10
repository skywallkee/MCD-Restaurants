import { Component, OnInit } from '@angular/core';
import { RestaurantService } from 'src/app/services/Restaurant/restaurant.service';

@Component({
  selector: 'app-reviews',
  templateUrl: './reviews.component.html',
  styleUrls: ['./reviews.component.scss']
})
export class ReviewsComponent implements OnInit {
  reviewText: string;

  constructor(private restaurantService: RestaurantService) { }

  ngOnInit(): void {
  }

  submitReview(){
    this.restaurantService.submitReview(this.reviewText);
  }

}
