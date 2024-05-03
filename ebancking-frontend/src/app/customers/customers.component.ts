import { CommonModule, NgFor } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../services/customer.service';
import { Observable, catchError, map, throwError } from 'rxjs';
import { Customer } from '../model/customer.model';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, NgFor, HttpClientModule, ReactiveFormsModule],

  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css',
})
export class CustomersComponent implements OnInit {
  handleDeleteCustomer(c: Customer) {
    let conf = confirm('Are you sure?');
    if (!conf) return;
    this.customerService.deleteCustomer(c.id).subscribe({
      next: (resp) => {
        this.customers$ = this.customers$.pipe(
          map((data) => {
            let index = data.indexOf(c);
            data.slice(index, 1);
            return data;
          })
        );
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
  handleSearchCustomers() {
    let kw = this.searchFormGroup?.value.keyword;
    this.customers$ = this.customerService.searchCustomers(kw).pipe(
      catchError((error) => {
        this.errorMessage = error.message;
        return throwError(error);
      })
    );
  }

  customers$!: Observable<Array<Customer>>;
  errorMessage!: string;
  searchFormGroup!: FormGroup;
  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder
  ) {}
  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.customers$ = this.customerService.getCustomers().pipe(
      catchError((error) => {
        this.errorMessage = error.message;
        return throwError(error);
      })
    );
  }
}
