// employee-dashboard.component.ts

import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Request } from '../request.model';
@Component({
  selector: 'app-employee-dashboard',
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {
  requestTypes: string[] = ['Auth', 'Leave', 'Loan', 'P_situation', 'Document', 'Job Transfer'];
  selectedRequestType: string = '';
  requestTitle: string = '';
  requestDescription: string = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {}
createRequest(): void {
  if (!this.selectedRequestType || !this.requestTitle || !this.requestDescription) {
    alert('Please select a request type and provide a title and description.');
    return;
  }

  const newRequest: Request = {
    type: this.selectedRequestType,
    title: this.requestTitle,
    description: this.requestDescription,
    status: 'Pending',
  };

  this.apiService.createRequest(newRequest).subscribe(
    (createdRequest: Request) => {
      console.log('Request created successfully:', createdRequest);
      // Clear input fields after creating the request
      this.selectedRequestType = '';
      this.requestTitle = '';
      this.requestDescription = '';
    },
    error => {
      console.error('Error creating request:', error);
      // Handle error appropriately (e.g., display error message)
    }
  );
}

loadMyRequests(): void {
  this.apiService.getMyRequests().subscribe(
    (requests: Request[]) => {
      console.log('My Requests:', requests);
      // Handle retrieved requests (e.g., display in UI)
    },
    error => {
      console.error('Error fetching requests:', error);
      // Handle error appropriately (e.g., display error message)
    }
  );
  }}
