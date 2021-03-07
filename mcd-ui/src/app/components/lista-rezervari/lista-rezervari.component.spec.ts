import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaRezervariComponent } from './lista-rezervari.component';

describe('ListaRezervariComponent', () => {
  let component: ListaRezervariComponent;
  let fixture: ComponentFixture<ListaRezervariComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListaRezervariComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaRezervariComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
