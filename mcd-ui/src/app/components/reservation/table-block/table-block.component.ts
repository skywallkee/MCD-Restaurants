import { EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Table } from 'src/app/models/table';

const blue: string = '#2F41E3';

@Component({
  selector: 'table-block',
  templateUrl: './table-block.component.html',
  styleUrls: ['./table-block.component.scss']
})
export class TableBlockComponent implements OnInit, OnChanges {
  @Input() table: Table;
  @Input() isAvailable: boolean;
  @Output() onSelect: EventEmitter<number> = new EventEmitter<number>();
  width: number;
  height: number;
  top: number;
  left: number;
  bgColor: string;
  private initialBgColor: string;
  private isSelected: boolean;

  constructor() { }

  ngOnChanges(changes: SimpleChanges) {
    for (const propName in changes) {
      if (changes.hasOwnProperty(propName)) {
        switch (propName) {
          case 'isAvailable': {
            if (this.isAvailable === false) {
              this.bgColor = '#AF1515';
            } else {
              this.bgColor = '#15AF2E';
            }
          }
            break;
        }
      }
    }
  }

  ngOnInit(): void {
    this.isSelected = false;
    this.height = (this.table.Dy - this.table.Ay) * 400 / 100;
    this.width = (this.table.Bx - this.table.Ax) * 1000 / 100;
    this.top = (this.table.Ay) * 400 / 100;
    this.left = (this.table.Ax) * 1000 / 100;
  }

  toggle() {
    if (this.isAvailable === false) {
      return;
    }
    if (this.isSelected) {
      this.isSelected = false;
      this.bgColor = this.initialBgColor;
    } else {
      this.isSelected = true;
      this.initialBgColor = this.bgColor;
      this.bgColor = blue;
    }
    this.onSelect.emit(this.table.id);
  }
}
