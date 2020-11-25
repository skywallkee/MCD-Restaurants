import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { RestaurantInterfataComponent } from './components/restaurant-interfata/restaurant-interfata.component';
import { SearchComponent } from './components/search/search.component';
import { ReviewsComponent } from './components/reviews/reviews.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'restaurant', component: RestaurantInterfataComponent},
  {path: 'search', component: SearchComponent},
  {path: 'reviews', component: ReviewsComponent},
  {path: '', component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
