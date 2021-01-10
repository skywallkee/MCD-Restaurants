import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WallBlockComponent } from './wall-block.component';

describe('WallBlockComponent', () => {
  let component: WallBlockComponent;
  let fixture: ComponentFixture<WallBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WallBlockComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WallBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
