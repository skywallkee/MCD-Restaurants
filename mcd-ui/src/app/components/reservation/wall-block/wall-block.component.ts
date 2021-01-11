import { Component, Input, OnInit } from '@angular/core';
import { Wall } from 'src/app/models/wall';

@Component({
  selector: 'wall-block',
  templateUrl: './wall-block.component.html',
  styleUrls: ['./wall-block.component.scss']
})
export class WallBlockComponent implements OnInit {
  width: number;
  height: number;
  @Input() wall: Wall;

  constructor() { }

  ngOnInit(): void {
    this.height = this.wall.Dy - this.wall.Ay;
    this.width = this.wall.Bx - this.wall.Ax;
  }

}
