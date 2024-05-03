import { CommonModule, NgFor } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Customer } from '../model/customer.model';
import { CustomerService } from '../services/customer.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-new-customer',
  standalone: true,
  imports: [CommonModule, NgFor, HttpClientModule, ReactiveFormsModule],

  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.css',
})
export class NewCustomerComponent implements OnInit {
  handleSaveCustomer() {
    let customer: Customer = this.newCustomerFormGroup.value;
    this.customerService.saveCustomer(customer).subscribe({
      next: (data) => {
        alert('Customer has been saved successfully!');
        //this.newCustomerFormGroup.reset();
        this.router.navigateByUrl('/customers');
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  newCustomerFormGroup!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.newCustomerFormGroup = this.fb.group({
      name: this.fb.control(null, [
        Validators.required,
        Validators.minLength(3),
      ]),
      email: this.fb.control(null, [Validators.email, Validators.required]),
    });
  }
}
