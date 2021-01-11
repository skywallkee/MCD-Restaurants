import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RestaurantInterfataComponent } from './components/restaurant-interfata/restaurant-interfata.component';
import { SearchComponent } from './components/search/search.component';
import { ReviewsComponent } from './components/reviews/reviews.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { ReservationResultComponent } from './components/reservation-result/reservation-result.component';
import {StatisticsComponent} from './components/statistics/statistics.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'restaurant/:id', component: RestaurantInterfataComponent},
  {path: 'search', component: SearchComponent},
  {path: 'reviews', component: ReviewsComponent},
  {path: 'reservation', component: ReservationComponent},
  {path: 'reservation-result', component:ReservationResultComponent},
  {path: 'statistics', component:StatisticsComponent},
  {path: '', component: SearchComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
