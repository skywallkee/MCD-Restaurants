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
  top: number;
  left: number;
  @Input() wall: Wall;

  constructor() { }

  ngOnInit(): void {
    this.height = (this.wall.Dy - this.wall.Ay) * 400 / 100;
    this.width = (this.wall.Bx - this.wall.Ax) * 1000 / 100;
    this.top = this.wall.Ay * 400 / 100;
    this.left = this.wall.Ax * 1000 / 100;
  }
}
