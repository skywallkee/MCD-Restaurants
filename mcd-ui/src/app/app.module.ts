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
import { FormsModule } from '@angular/forms';
import { RestaurantInterfataComponent } from './components/restaurant-interfata/restaurant-interfata.component';
import { SearchComponent } from './components/search/search.component';
import { ReviewsComponent } from './components/reviews/reviews.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { ReservationResultComponent } from './components/reservation-result/reservation-result.component';
import { StatisticsComponent } from './components/statistics/statistics.component';
import { WallBlockComponent } from './components/reservation/wall-block/wall-block.component';
import { TableBlockComponent } from './components/reservation/table-block/table-block.component';
import { AgmCoreModule } from '@agm/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { ListaRezervariComponent } from './components/lista-rezervari/lista-rezervari.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    RestaurantInterfataComponent,
    SearchComponent,
    ReviewsComponent,
    ReservationComponent,
    ReservationResultComponent,
    StatisticsComponent,
    WallBlockComponent,
    TableBlockComponent,
    ListaRezervariComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FlexLayoutModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatCardModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAxF2VRhdItkAUs-EOR9d3nebrNbLphs9Q'
    }),
    NgbModule,
    NgxChartsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
