import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {FlexLayoutModule} from '@angular/flex-layout';
import { HomeComponent } from './components/home/home.component';
import { RestaurantComponent } from './components/home/restaurant/restaurant.component';
import { FormsModule } from '@angular/forms';
import { RestaurantInterfataComponent } from './components/restaurant-interfata/restaurant-interfata.component';
import { SearchComponent } from './components/search/search.component';
import { ReviewsComponent } from './components/reviews/reviews.component';
import { ReservationComponent } from './components/reservation/reservation.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    RestaurantComponent,
    RestaurantInterfataComponent,
    SearchComponent,
    ReviewsComponent,
    ReservationComponent
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FlexLayoutModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
