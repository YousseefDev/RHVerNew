import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-hr-dashboard',
  templateUrl: './hr-dashboard.component.html',
  styleUrls: ['./hr-dashboard.component.scss']
})
export class HrDashboardComponent implements OnInit {
currentUserRole: any;

  constructor() { }

  ngOnInit(): void {
  }

}
