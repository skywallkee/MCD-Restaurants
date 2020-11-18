import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RestaurantInterfataComponent } from './restaurant-interfata.component';

describe('RestaurantInterfataComponent', () => {
  let component: RestaurantInterfataComponent;
  let fixture: ComponentFixture<RestaurantInterfataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RestaurantInterfataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RestaurantInterfataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
