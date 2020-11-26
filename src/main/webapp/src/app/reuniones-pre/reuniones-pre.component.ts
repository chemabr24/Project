import { Component, Input, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import { Router } from '@angular/router';
import {ReunionComponent} from '../reunion/reunion.component';

@Component({
  selector: 'app-reuniones-pre',
  templateUrl: './reuniones-pre.component.html',
  styleUrls: ['./reuniones-pre.component.css']
})
export class ReunionesPreComponent implements OnInit {

  @Input() public reunion;

  constructor(
    public dialog: MatDialog,
    private router:Router
  ) { }

  ngOnInit(): void {
    console.log(this.reunion);
  }

  verReunion() {
    this.reunion.action = "Modificar";
		const dialogRef = this.dialog.open(ReunionComponent, {
      width: '325px',
      data: this.reunion
    });
    dialogRef.afterClosed().subscribe(() => {
      this.router.navigateByUrl('/carga');
    });
  }
}
